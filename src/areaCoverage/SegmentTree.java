package areaCoverage;

import java.util.List;

public class SegmentTree {
	Node[] nodes;

	public SegmentTree(List<Integer> ls) {
		int n = ls.size() - 1;
		int N = (int) Math.floor(Math.log10(n) / Math.log10(2));
		int delta = n - N;
		nodes = new Node[1 << (N + 1) + delta];
//		nodes = new Node[1 << (int) ((Math.ceil(Math.log10(ls.size() - 1) / Math.log10(2))) + 1)];
		build(1, ls.size() - 1, 1, ls);
	}

	private void build(int l, int r, int p, List<Integer> ls) {
		if(l >= r) {
			int lf = ls.get(l - 1), rt = ls.get(l);
			nodes[p] = new Node(lf, rt, 0, 0, true);
			return;
		}
		int mid = (l + r) / 2;
		build(l, mid, 2 * p, ls);
		build(mid + 1, r, 2 * p + 1, ls);
		int lf = getLeftNode(p).left;
		int rt = getRightNode(p).right;
		nodes[p] = new Node(lf, rt, 0, 0, false);
	}

	private void update(int l, int r, int k, int p) {
		Node cur = nodes[p];
		if(cur.right <= l || r <= cur.left) {
			return;
		}
		if(l <= cur.left && cur.right <= r) {
			cur.cover += k;
			pushUp(p);
			return;
		}
		update(l, r, k, getLeftIdx(p));
		update(l, r, k, getRightIdx(p));
		pushUp(p);
	}

	public void update(int l, int r, int k) {
		update(l, r, k, 1);
	}

	public int query() {
		return nodes[1].len;
	}

	private void pushUp(int p) {
		Node cur = nodes[p];
		if(cur.cover > 0) cur.len = cur.right - cur.left;
		else if(cur.isLeaf) cur.len = 0;
		else cur.len = getLeftNode(p).len + getRightNode(p).len;
	}

	private Node getLeftNode(int p) {
		return nodes[2 * p];
	}

	private Node getRightNode(int p) {
		return nodes[2 * p + 1];
	}

	private int getLeftIdx(int p) {
		return 2 * p;
	}

	private int getRightIdx(int p) {
		return 2 * p + 1;
	}

}
