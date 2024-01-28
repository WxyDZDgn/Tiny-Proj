package genBinTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BinaryTree {
	private final Random random;
	private final List<Integer> ls;
	private final Node root;

	public BinaryTree(int length) {
		this.random = new Random();
		this.ls = new ArrayList<>();
		for(int i = 0; i < length; ++i) ls.add(i);
		Collections.shuffle(ls);
		this.root = randomConstruction(0, length - 1);
	}

	private Node randomConstruction(int left, int right) {
		if(left > right) return null;
		int mid = random.nextInt(left, right + 1);
		return new Node(ls.get(mid), randomConstruction(left, mid - 1), randomConstruction(mid + 1, right));
	}

	public void preOrder(List<Integer> res) {
		List<Node> ls = new ArrayList<>();
		if(this.root != null) this.root.preOrder(ls);
		ls.forEach(value -> res.add(value.getValue()));
	}

	public void midOrder(List<Integer> res) {
		List<Node> ls = new ArrayList<>();
		if(this.root != null) this.root.midOrder(ls);
		ls.forEach(value -> res.add(value.getValue()));

	}

	public void posOrder(List<Integer> res) {
		List<Node> ls = new ArrayList<>();
		if(this.root != null) this.root.posOrder(ls);
		ls.forEach(value -> res.add(value.getValue()));

	}
}
