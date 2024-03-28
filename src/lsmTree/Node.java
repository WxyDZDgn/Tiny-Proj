package lsmTree;

public class Node<ROW_KEY extends Comparable<ROW_KEY>, VALUE> implements Comparable<Node<ROW_KEY, VALUE>> {
	private ROW_KEY rowKey;
	private VALUE value;
	private boolean deleted;

	public Node(ROW_KEY rowKey, VALUE value) {
		this(rowKey, value, false);
	}

	public Node(ROW_KEY rowKey, VALUE value, boolean deleted) {
		this.rowKey = rowKey;
		this.value = value;
		this.deleted = deleted;
	}

	public ROW_KEY getRowKey() {
		return rowKey;
	}

	public void setRowKey(ROW_KEY rowKey) {
		this.rowKey = rowKey;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public VALUE getValue() {
		return value;
	}

	public void setValue(VALUE value) {
		this.value = value;
	}

	@Override
	public int compareTo(Node<ROW_KEY, VALUE> o) {
		return this.rowKey.compareTo(o.rowKey);
	}

	@Override
	public String toString() {
		if(this.deleted) {
			return "Node{" +
				   "rowKey=" + rowKey +
				   ", deleted" +
				   '}';
		} else {
			return "Node{" +
				   "rowKey=" + rowKey +
				   ", value=" + value +
				   '}';
		}
	}
}
