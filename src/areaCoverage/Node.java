package areaCoverage;

class Node {
	int left;
	int right;
	int cover;
	int len;
	boolean isLeaf;

	Node(int l, int r, int cover, int len, boolean isLeaf) {
		this.left = l;
		this.right = r;
		this.cover = cover;
		this.len = len;
		this.isLeaf = isLeaf;
	}
}
