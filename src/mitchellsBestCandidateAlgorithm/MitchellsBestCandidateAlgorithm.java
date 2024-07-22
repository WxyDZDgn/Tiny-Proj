package mitchellsBestCandidateAlgorithm;

import java.util.List;
import java.util.Random;

public class MitchellsBestCandidateAlgorithm {
	private static final long DEFAULT_SEED = 9653;
	private static final long DEFAULT_SIZE = Integer.MAX_VALUE;
	private static final int DEFAULT_MAXIMUM_PHASE = 5000;
	private static final int DEFAULT_MAXIMUM_POINTS_PER_PHASE = 5000;
	private long seed;
	private long size;
	private int maximumPhase;
	private int maximumPointsPerPhase;

	public MitchellsBestCandidateAlgorithm() {
		this.seed = DEFAULT_SEED;
		this.size = DEFAULT_SIZE;
		this.maximumPhase = DEFAULT_MAXIMUM_PHASE;
		this.maximumPointsPerPhase = DEFAULT_MAXIMUM_POINTS_PER_PHASE;
	}

	public MitchellsBestCandidateAlgorithm(long seed, long size, int maximumPhase, int maximumPointsPerPhase) {
		this.seed = seed;
		this.size = size;
		this.maximumPhase = maximumPhase;
		this.maximumPointsPerPhase = maximumPointsPerPhase;
	}

	public MitchellsBestCandidateAlgorithm setSeed(long seed) {
		this.seed = seed;
		return this;
	}

	public MitchellsBestCandidateAlgorithm setSize(long size) {
		this.size = size;
		return this;
	}

	public MitchellsBestCandidateAlgorithm setMaximumPhase(int maximumPhase) {
		this.maximumPhase = maximumPhase;
		return this;
	}

	public MitchellsBestCandidateAlgorithm setMaximumPointsPerPhase(int maximumPointsPerPhase) {
		this.maximumPointsPerPhase = maximumPointsPerPhase;
		return this;
	}

	public List<Point> nextPoints() {
		final Random random = new Random(this.seed);

		Point size = new Point(this.size, this.size);
		Point initPoint = new Point(random.nextLong(this.size), random.nextLong(this.size));
		QuadTree tree = new QuadTree(initPoint, size);

		for(int i = 0; i < this.maximumPhase; ++i) {
			int m = this.maximumPointsPerPhase;
			Point[] points = new Point[m];
			for(int j = 0; j < m; ++j) {
				points[j] = new Point(random.nextLong(this.size), random.nextLong(this.size));
			}
			tree.addPoints(points);
		}
		return tree.getPoints();
	}
}
