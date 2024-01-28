package genBinTree;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		final int NUMBER_OF_NODES = 10;

		List<Integer> pre = new ArrayList<>();
		List<Integer> mid = new ArrayList<>();
		List<Integer> pos = new ArrayList<>();
		BinaryTree binaryTree = new BinaryTree(NUMBER_OF_NODES);
		binaryTree.preOrder(pre);
		binaryTree.midOrder(mid);
		binaryTree.posOrder(pos);
		System.out.println(pre);
		System.out.println(mid);
		System.out.println(pos);
	}
}
