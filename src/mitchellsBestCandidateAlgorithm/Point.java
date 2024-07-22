package mitchellsBestCandidateAlgorithm;

public class Point {
	public static final Point ORIGIN = new Point(0, 0);
	private final long X, Y;

	public Point(long x, long y) {
		this.X = x;
		this.Y = y;
	}

	public Point(Point p) {
		this.X = p.X;
		this.Y = p.Y;
	}

	public static Point getMiddlePoint(Point a, Point b) {
		long x = a.X + b.X, y = a.Y + b.Y;
		return new Point(x / 2, y / 2);
	}

	public static long getDistanceSqare(Point a, Point b) {
		long x = a.X - b.X, y = a.Y - b.Y;
		return x * x + y * y;
	}

	public long getX() {
		return X;
	}

	public long getY() {
		return Y;
	}

	@Override
	public String toString() {
		return "Point(%d, %d)".formatted(this.X, this.Y);
	}
}
