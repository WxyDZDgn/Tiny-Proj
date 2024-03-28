package lsmTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Disk<ROW_KEY extends Comparable<ROW_KEY>, VALUE> {
	int maxLevel;
	int[] maxOfEachLevel;
	List<List<Node<ROW_KEY, VALUE>>> trees;

	public Disk(int defaultMaxLevel, int... maxLevels) {
		if(defaultMaxLevel < 1) {
			throw new RuntimeException("length should at least be 1.");
		}
		for(int level : maxLevels) {
			if(level < 1) throw new RuntimeException("length should at least be 1.");
		}
		this.maxLevel = maxLevels.length + 1;
		this.maxOfEachLevel = new int[this.maxLevel];
		this.maxOfEachLevel[0] = defaultMaxLevel;
		System.arraycopy(maxLevels, 0, this.maxOfEachLevel, 1, this.maxLevel - 1);
		this.trees = new ArrayList<>();
		for(int i = 0; i < this.maxLevel; ++i) {
			List<Node<ROW_KEY, VALUE>> tmp = new ArrayList<>();
			this.trees.add(tmp);
		}
	}

	public void put(List<Node<ROW_KEY, VALUE>> ls) {
		this.put(0, ls, 0);
	}

	public void clear() {
		this.trees.forEach(List::clear);
	}

	@Override
	public String toString() {
		return "Disk{" +
			   "maxLevel=" + maxLevel +
			   ", maxOfEachLevel=" + Arrays.toString(maxOfEachLevel) +
			   ", trees=" + trees +
			   '}';
	}

	private void put(int level, List<Node<ROW_KEY, VALUE>> ls, int index) {
		if(index >= ls.size()) return;
		if(level >= this.maxLevel) throw new RuntimeException("Disk out of memory: " + this);
		List<Node<ROW_KEY, VALUE>> tmp = new ArrayList<>();
		int ori = 0;
		while(ori < this.trees.get(level).size() && index < ls.size()) {
			int op = ls.get(index).compareTo(this.trees.get(level).get(ori));
			if(op == 0) {
				tmp.add(ls.get(index));
				++ori;
				++index;
			} else if(op < 0) {
				tmp.add(ls.get(index));
				++index;
			} else {
				tmp.add(this.trees.get(level).get(ori));
				++ori;
			}
		}
		while(ori < this.trees.get(level).size()) {
			tmp.add(this.trees.get(level).get(ori));
			++ori;
		}
		while(index < ls.size()) {
			tmp.add(ls.get(index));
			++index;
		}
		int tmpIndex = 0;
		List<Node<ROW_KEY, VALUE>> merge = new ArrayList<>();
		for(; tmpIndex < Math.min(this.maxOfEachLevel[level], tmp.size()); ++tmpIndex) {
			merge.add(tmp.get(tmpIndex));
		}
		this.trees.set(level, merge);
		put(level + 1, tmp, tmpIndex);

	}

	public Node<ROW_KEY, VALUE> get(ROW_KEY rowKey) {
		for(List<Node<ROW_KEY, VALUE>> tree : this.trees) {
			for(Node<ROW_KEY, VALUE> each : tree) {
				if(each.getRowKey().equals(rowKey)) {
					return each;
				}
			}
		}
		return null;
	}
}
