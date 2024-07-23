package mitchellsBestCandidateAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private static final Integer LEAF_STATUS = 0;
	private static final Integer NODE_STATUS = 1;
	private static final Integer MAXIMUM_CHILDREN = 4;
	private static final Integer AREA_0 = 0;
	private static final Integer AREA_1 = 1;
	private static final Integer AREA_2 = 2;
	private static final Integer AREA_3 = 3;
	private static final String LEAF_NAME = "Leaf";
	private static final String NODE_NAME = "Node";
	private final Integer maximumPoints;
	private final Point DIAG1, DIAG2;
	private final List<Point> points;
	private final Node[] nodes;
	private Integer currentStatus;

	public Node(Point diag1, Point diag2, Integer maximumPoints) {
		this.DIAG2 = diag2;
		this.DIAG1 = diag1;
		this.currentStatus = LEAF_STATUS;
		this.maximumPoints = maximumPoints;
		this.points = new ArrayList<>();
		this.nodes = new Node[MAXIMUM_CHILDREN];
	}

	public long getMinimumDistanceSquare(Point point) {
		long min = -1;
		if(this.currentStatus.equals(LEAF_STATUS)) {
			for(Point p : this.points) {
				long res = Point.getDistanceSqare(p, point);
				if(res >= 0 && (min < 0 || min >= res)) min = res;
			}
		} else {
			int area = getArea(point);
			for(int i = 0; i <= 3; ++i) {
				if(i == 2 && !isInside(point)) continue;
				Node n = nodes[(area + i) % 4];
				long res = n.getMinimumDistanceSquare(point);
				if(res >= 0 && (min < 0 || min >= res)) min = res;
			}
		}
		return min;
	}

	public void add(Point point) {
		points.add(point);
		if(this.currentStatus.equals(LEAF_STATUS) && points.size() > maximumPoints) {
			Point mid = Point.getMiddlePoint(this.DIAG1, this.DIAG2);
			long u = Math.max(this.DIAG1.getY(), this.DIAG2.getY()), d = Math.min(this.DIAG1.getY(), this.DIAG2.getY());
			long r = Math.max(this.DIAG1.getX(), this.DIAG2.getX()), l = Math.min(this.DIAG1.getX(), this.DIAG2.getX());
			Point ul = new Point(l, u), ur = new Point(r, u);
			Point dl = new Point(l, d), dr = new Point(r, d);
			this.nodes[this.getArea(ul)] = new Node(ul, mid, maximumPoints);
			this.nodes[this.getArea(ur)] = new Node(ur, mid, maximumPoints);
			this.nodes[this.getArea(dl)] = new Node(dl, mid, maximumPoints);
			this.nodes[this.getArea(dr)] = new Node(dr, mid, maximumPoints);
			this.currentStatus = NODE_STATUS;
		}
		if(this.currentStatus.equals(NODE_STATUS)) {
			for(Point p : this.points) {
				this.nodes[this.getArea(p)].add(p);
			}
			this.points.clear();
		}
	}

	public Integer getArea(Point point) {
		Point mid = Point.getMiddlePoint(this.DIAG1, this.DIAG2);
		long x = point.getX() - mid.getX(), y = point.getY() - mid.getY();
		if(x >= 0 && y >= 0) return AREA_0;
		if(x < 0 && y >= 0) return AREA_1;
		if(x < 0 && y < 0) return AREA_2;
		return AREA_3;
	}

	private boolean isInside(Point point) {
		long u = Math.max(this.DIAG1.getY(), this.DIAG2.getY()), d = Math.min(this.DIAG1.getY(), this.DIAG2.getY());
		long r = Math.max(this.DIAG1.getX(), this.DIAG2.getX()), l = Math.min(this.DIAG1.getX(), this.DIAG2.getX());
		return d <= point.getY() && point.getY() <= u &&
			   l <= point.getX() && point.getX() <= r;
	}

	public List<Point> getPoints() {
		if(currentStatus.equals(LEAF_STATUS)) return List.copyOf(this.points);
		List<Point> ls = new ArrayList<>();
		for(Node n : nodes) {
			ls.addAll(n.getPoints());
		}
		return ls;
	}

	@Override
	public String toString() {
		String type = LEAF_NAME, value = this.points.toString();
		if(this.currentStatus.equals(NODE_STATUS)) {
			type = NODE_NAME;
			value = List.of(this.nodes).toString();
		}
		return "%s(Area=(%s ~ %s), %s)".formatted(type, DIAG1, DIAG2, value);
	}
}