package segmentTree;

public class Node {
	private final int left;
	private final int right;
	private int value;
	private Node leftNode;
	private Node rightNode;
	private int lazy;

	public Node(int left, int right) {
		this.left = left;
		this.right = right;
		leftNode = null;
		rightNode = null;
		value = 0;
		lazy = 0;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void updateDelta(int l, int r, int delta) {
		if(right < l || r < left) {
			return;
		}
		if(l <= left && right <= r) {
			lazy += delta;
			value += (right - left + 1) * delta;
			return;
		}
		leftNode.updateDelta(l, r, delta);
		rightNode.updateDelta(l, r, delta);
		value = leftNode.value + rightNode.value;
	}

	public int query(int l, int r) {
		if(right < l || r < left) {
			return 0;
		}
		if(l <= left && right <= r) {
			return value;
		}
		if(lazy != 0) {
			leftNode.updateDelta(l, r, lazy);
			rightNode.updateDelta(l, r, lazy);
			lazy = 0;
		}
		int cur = 0;
		cur += leftNode.query(l, r);
		cur += rightNode.query(l, r);
		return cur;

	}

	@Override
	public String toString() {
		return String.format(
				"Node [left=%s, right=%s, value=%s, leftNode=%s, rightNode=%s]", left, right, value, getLeftNode(),
				getRightNode()
		);
	}

	public Node getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}

}
