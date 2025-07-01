package areaCoverage;

import java.util.*;

public class AreaCoverageTest {
	private static final String[] TESTS = new String[]{"""
2
-2 2 -1 1
-1 1 -2 2
""", """
3
-2 2 -1 1
-1 1 -2 2
-3 3 -3 3
""", """
5
-9 -4 0 3
-6 2 -5 1
-5 5 -3 4
-3 -1 -4 -2
1 3 -6 -4
"""};
	private static final int[] EXPECTED_RESULTS = new int[]{12, 36, 104};

	public static void main(String[] args) {
//		assert TESTS.length == EXPECTED_RESULTS.length;
		for(int i = 0; i < TESTS.length; i++) {
			test(TESTS[i], EXPECTED_RESULTS[i]);
		}
		System.out.println("Test Passed");
	}

	private static void test(String test, int expectedResult) {
		int actualResult = solve(test);
		assert actualResult == expectedResult;
	}

	private static int solve(String test) {
		Scanner input = new Scanner(test);
		int n = input.nextInt();
		List<Tuple> tuples = new ArrayList<>();
		HashSet<Integer> ys = new HashSet<>();
		for(int i = 0; i < n; i++) {
			int x1 = input.nextInt(), x2 = input.nextInt(), y1 = input.nextInt(), y2 = input.nextInt();
			tuples.add(new Tuple(x1, y1, y2, 1));
			tuples.add(new Tuple(x2, y1, y2, -1));
			ys.add(y1);
			ys.add(y2);
		}
		List<Integer> sorted = new ArrayList<>(ys.stream().toList());
		sorted.sort(null);
		tuples.sort(Comparator.comparingInt(Tuple::x));

		int area = 0;
		SegmentTree segmentTree = new SegmentTree(sorted);
		for(int i = 0; i < tuples.size(); i++) {
			Tuple cur = tuples.get(i);
			if(i > 0) {
				Tuple pre = tuples.get(i - 1);
				int deltaX = cur.x() - pre.x();
				area += segmentTree.query() * deltaX;
			}
			segmentTree.update(cur.y1(), cur.y2(), cur.k());
		}

		input.close();

		return area;
	}
}
