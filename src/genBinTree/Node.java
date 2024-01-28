package genBinTree;

import java.util.List;

public class Node {
	private final int value;
	private final Node left;
	private final Node right;

	public Node(int value, Node left, Node right) {
		this.value = value;
		this.left = left;
		this.right = right;
	}

	public int getValue() {
		return value;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

	public void preOrder(List<Node> ls) {
		ls.add(this);
		if(this.left != null) this.getLeft().preOrder(ls);
		if(this.right != null) this.getRight().preOrder(ls);
	}

	public void midOrder(List<Node> ls) {
		if(this.left != null) this.getLeft().midOrder(ls);
		ls.add(this);
		if(this.right != null) this.getRight().midOrder(ls);
	}

	public void posOrder(List<Node> ls) {
		if(this.left != null) this.getLeft().posOrder(ls);
		if(this.right != null) this.getRight().posOrder(ls);
		ls.add(this);
	}
}
