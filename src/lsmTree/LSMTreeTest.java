package lsmTree;

import java.util.ArrayList;
import java.util.List;

public class LSMTreeTest {
	private static LSMTree<Integer, Integer> lsmTree;

	public static void main(String[] args) {
		init();
		deUpsert();
		select();
		clear();
	}

	public static void init() {
		System.out.println("--------INIT--------");
		lsmTree = new LSMTree<>(2, 4, 20);
		List<Node<Integer, Integer>> ls = new ArrayList<>();
		ls.add(new Node<>(5, 1));
		ls.add(new Node<>(2, 2));
		ls.add(new Node<>(3, 3));
		ls.add(new Node<>(4, 4));
		ls.add(new Node<>(9, 5));
		ls.add(new Node<>(1, 6));
		lsmTree.puts(ls);
		System.out.println(lsmTree);
		lsmTree.submit();
		System.out.println(lsmTree);
	}

	public static void deUpsert() {
		System.out.println("--------DE_UPSERT--------");
		System.out.println("insert 8, 6, 10");
		lsmTree.put(new Node<>(8, 7));
		lsmTree.put(new Node<>(6, 8));
		lsmTree.put(new Node<>(10, 9));
		System.out.println(lsmTree);
		System.out.println("update 1 from 6 to 10 in disk");
		System.out.println("update 6 from 8 to 11 in mem");
		lsmTree.put(new Node<>(1, 10));
		lsmTree.put(new Node<>(6, 11));
		System.out.println(lsmTree);
		System.out.println("delete 2 in disk");
		System.out.println("delete 10 in mem");
		lsmTree.put(new Node<>(2, null, true));
		lsmTree.put(new Node<>(10, null, true));
		System.out.println(lsmTree);
		System.out.println("submit");
		lsmTree.submit();
		System.out.println(lsmTree);
	}

	public static void select() {
		System.out.println("--------SELECT--------");
		System.out.println("insert 20");
		System.out.println("update 6 from 11 to 20");
		lsmTree.put(new Node<>(20, 19));
		lsmTree.put(new Node<>(6, 20));
		System.out.println("select 6, 20, 1, 2, 50");
		System.out.println(lsmTree.get(6));
		System.out.println(lsmTree.get(20));
		System.out.println(lsmTree.get(1));
		System.out.println(lsmTree.get(2));
		System.out.println(lsmTree.get(50));
	}

	public static void clear() {
		System.out.println("--------CLEAR--------");
		lsmTree.clear();
		System.out.println(lsmTree);
	}
}