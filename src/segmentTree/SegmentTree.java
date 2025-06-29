package segmentTree;

import java.util.List;

public class SegmentTree {
	private final Node root;

	public SegmentTree(List<Integer> values) {
		if(values.isEmpty()) {
			root = null;
			return;
		}
		int n = values.size() - 1;
		root = new Node(0, values.size() - 1);
		if(n <= 0) {
			root.setValue(values.getFirst());
			return;
		}
		int val = 0;
		val += build(0, n / 2, root, true, values);
		val += build(n / 2 + 1, n, root, false, values);
		root.setValue(val);
	}

	private int build(int l, int r, Node node, boolean isLeft, List<Integer> values) {
		Node tmp = new Node(l, r);
		if(isLeft) {
			node.setLeftNode(tmp);
		} else {
			node.setRightNode(tmp);
		}
		if(l >= r) {
			tmp.setValue(values.get(l));
			return values.get(l);
		}
		int mid = (l + r) / 2;
		int val = 0;
		val += build(l, mid, tmp, true, values);
		val += build(mid + 1, r, tmp, false, values);
		tmp.setValue(val);
		return val;
	}

	public void updateDelta(int l, int r, int delta) {
		if(root != null) root.updateDelta(l, r, delta);
	}

	public Integer query(int l, int r) {
		if(root == null) return null;
		return root.query(l, r);
	}

	@Override
	public String toString() {
		return String.format("SegmentTree [root=%s]", root);
	}
}
