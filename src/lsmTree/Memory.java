package lsmTree;

import java.util.*;

public class Memory<ROW_KEY extends Comparable<ROW_KEY>, VALUE> {
	private final TreeMap<ROW_KEY, Node<ROW_KEY, VALUE>> tree;

	public Memory() {
		this.tree = new TreeMap<>();
	}

	public List<Node<ROW_KEY, VALUE>> submit() {
		List<Node<ROW_KEY, VALUE>> res = new ArrayList<>();
		this.tree.forEach((k, v) -> res.add(v));
		return res;
	}

	public void clear() {
		this.tree.clear();
	}

	public void put(ROW_KEY rowKey, Node<ROW_KEY, VALUE> node) {
		tree.put(rowKey, node);
	}

	public Node<ROW_KEY, VALUE> get(ROW_KEY rowKey) {
		return this.tree.getOrDefault(rowKey, null);
	}

	@Override
	public String toString() {
		return "Memory{" +
			   "tree=" + tree.values() +
			   '}';
	}
}
