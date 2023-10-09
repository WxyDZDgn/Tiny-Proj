package binTable;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private static final int MAX_Bit = 3;

	public static void main(String[] args) {
		final int MAX_VALUE = (1 << MAX_Bit) - 1;

		int binLength = 0;
		{
			int x = MAX_VALUE;
			do {
				x >>= 1;
				++binLength;
			} while(x != 0);
		}

		List<List<Integer>> ls = new ArrayList<>(binLength);
		for(int i = 0; i < binLength; i++) {
			ls.add(new ArrayList<>());
		}

		for(int each = 1; each <= MAX_VALUE; ++each) {
			int x = each;
			for(int i = 0; i < binLength; ++i) {
				if((x & 1) == 1) ls.get(i).add(each);
				x >>= 1;
			}
		}

		for(int i = ls.size() - 1; i >= 0; --i) {
			System.out.println(ls.get(i));
		}

	}
}
