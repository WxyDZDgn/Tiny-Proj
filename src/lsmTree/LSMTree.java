package lsmTree;

import java.util.List;

public class LSMTree<ROW_KEY extends Comparable<ROW_KEY>, VALUE> {
	private final Memory<ROW_KEY, VALUE> memory;
	private final Disk<ROW_KEY, VALUE> disk;

	public LSMTree(int defaultMaxLevel, int... maxLevels) {
		this.memory = new Memory<>();
		this.disk = new Disk<>(defaultMaxLevel, maxLevels);
	}

	public void clear() {
		this.memory.clear();
		this.disk.clear();
	}

	public void put(Node<ROW_KEY, VALUE> item) {
		this.memory.put(item.getRowKey(), item);
	}

	public void puts(List<Node<ROW_KEY, VALUE>> items) {
		for(Node<ROW_KEY, VALUE> item : items) {
			this.memory.put(item.getRowKey(), item);
		}
	}

	public Node<ROW_KEY, VALUE> get(ROW_KEY rowKey) {
		Node<ROW_KEY, VALUE> res;
		res = this.memory.get(rowKey);
		if(res == null) res = this.disk.get(rowKey);
		return res;
	}

	@Override
	public String toString() {
		return "LSMTree{" +
			   "memory=" + memory +
			   ", disk=" + disk +
			   '}';
	}

	public void submit() {
		this.disk.put(this.memory.submit());
		this.memory.clear();
	}
}
