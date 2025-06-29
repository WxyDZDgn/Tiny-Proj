package segmentTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegmentTreeTest {
	private static final Random rand = new Random();
	private static final int TOTAL_TEST_COUNT = 1000;
	private static final int TOTAL_LIST_SIZE = 1000;
	private static final int TOTAL_OPER_COUNT = 1000;

	private static final int MIN_ELEM_VALUE = -1000;
	private static final int MAX_ELEM_VALUE = 1000;

	public static void main(String[] args) {
		for(int i = 0; i < TOTAL_TEST_COUNT; i++) {
			test();
		}
		System.out.println("Test Passed");
	}

	public static void test() {
		List<Integer> ls = new ArrayList<>();

		rand.ints(TOTAL_LIST_SIZE, MIN_ELEM_VALUE, MAX_ELEM_VALUE).forEach(ls::add);
		SegmentTree st = new SegmentTree(ls);
		List<Integer> cp = new ArrayList<>(List.copyOf(ls));
		for(int i = 0; i < TOTAL_OPER_COUNT; i++) {
			int b1 = rand.nextInt(TOTAL_LIST_SIZE), b2 = rand.nextInt(TOTAL_LIST_SIZE);
			int l = Math.min(b1, b2), r = Math.max(b1, b2);
			if(rand.nextBoolean()) {
				int ans = 0;
				for(int m = l; m <= r; m++) {
					ans += cp.get(m);
				}
				int out = st.query(l, r);
				assert ans == out;
			} else {
				int val = rand.nextInt(MIN_ELEM_VALUE, MAX_ELEM_VALUE);
				for(int m = l; m <= r; m++) {
					cp.set(m, cp.get(m) + val);
				}
				st.updateDelta(l, r, val);
			}
		}
	}
}
