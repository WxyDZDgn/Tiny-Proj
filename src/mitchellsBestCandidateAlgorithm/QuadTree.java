package mitchellsBestCandidateAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
	private static final Integer DEFAULT_MAXIMUM_POINTS = 10;
	private final Node core;
	private final Integer maximumPoints;

	public QuadTree(Point initPoint, Point size) {
		this(initPoint, size, DEFAULT_MAXIMUM_POINTS);
	}

	public QuadTree(Point initPoint, Point size, Integer maximumPoints) {
		this.maximumPoints = maximumPoints;
		core = new Node(Point.ORIGIN, size, maximumPoints);
		core.add(initPoint);
	}

	public Point addPoints(Point[] points) {
		long max = -1;
		Point resultPoint = null;
		for(Point point : points) {
			long res = core.getMinimumDistanceSquare(point);
			if(res >= 0 && (max < 0 || max <= res)) {
				max = res;
				resultPoint = point;
			}
		}
		if(resultPoint != null) {
			core.add(resultPoint);
		}
		return resultPoint;
	}

	@Override
	public String toString() {
		return "QuadTree(maximumPoints=%d, %s)".formatted(maximumPoints, core);
	}

	public List<Point> getPoints() {
		return new ArrayList<>(core.getPoints());
	}
}
