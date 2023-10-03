package fingerGame;

import java.util.ArrayList;
import java.util.List;

public class OutputSimulationSteps {
	private static final int N = 10;
	private static int[][][][] res;
	private static boolean[][][][] vis;

	public static void main(String[] args) {
		res = new int[N][N][N][N];
		vis = new boolean[N][N][N][N];
		Player[] players = new Player[]{new Player(), new Player()};
		int currentPlayer = 0;
		List<Choice> finalChoice = new ArrayList<>();
		List<Choice> win = new ArrayList<>();
		List<Choice> lose = new ArrayList<>();

		while(true) {
			win.clear();
			lose.clear();
			for(int i = 0; i < 2; ++i) {
				for(int j = 0; j < 2; ++j) {
					if(!players[currentPlayer].isAvailable(i, players[1 - currentPlayer], j)) continue;
					if(players[currentPlayer].isWin(i, players[1 - currentPlayer], j) > 0)
						win.add(new Choice(players[currentPlayer], i, players[1 - currentPlayer], j));
					else lose.add(new Choice(players[currentPlayer], i, players[1 - currentPlayer], j));
				}
			}
			Choice choice = win.isEmpty() ? lose.get(0) : win.get(0);
			finalChoice.add(choice);
			players[currentPlayer].step(choice.currentHand, choice.nextPlayer, choice.nextHand);
			if(players[currentPlayer].isWin()) break;
			currentPlayer = 1 - currentPlayer;
		}
		for(Choice each : finalChoice) {
			System.out.println(each);
		}
		System.out.printf("%s: Victory!\n", players[currentPlayer]);

	}

	// -1: 当前玩家失败
	// 0: 循环
	// 1: 当前玩家获胜
	public static int fun(int sLeft, int sRight, int oLeft, int oRight) {
		int sMin = Math.min(sLeft, sRight), sMax = Math.max(sLeft, sRight);
		int oMin = Math.min(oLeft, oRight), oMax = Math.max(oLeft, oRight);
		if(vis[sMin][sMax][oMin][oMax]) return 0;
		if(sMax == 0 && oMax == 0) return 0;
		if(res[sMin][sMax][oMin][oMax] != 0) return res[sMin][sMax][oMin][oMax];

		vis[sMin][sMax][oMin][oMax] = true;

		if(oMax == 0) res[sMin][sMax][oMin][oMax] = -1;
		else if(sMax == 0) res[sMin][sMax][oMin][oMax] = 1;
		else {
			int temp = -1;
			temp = getTemp(sMin, sMax, oMin, oMax, temp);
			temp = getTemp(sMax, sMin, oMin, oMax, temp);
			res[sMin][sMax][oMin][oMax] = temp;
		}

		vis[sMin][sMax][oMin][oMax] = false;
		return res[sMin][sMax][oMin][oMax];
	}

	private static int getTemp(int sMin, int sMax, int oMin, int oMax, int temp) {
		int next;
		if(sMin != 0 && oMin != 0 &&
		   (next = fun(oMin, oMax, Math.min((sMin + oMin) % N, sMax), Math.max((sMin + oMin) % N, sMax))) != 0) {
			temp = Math.max(temp, -next);
		}
		if(sMin != 0 && oMax != 0 &&
		   (next = fun(oMin, oMax, Math.min((sMin + oMax) % N, sMax), Math.max((sMin + oMax) % N, sMax))) != 0) {
			temp = Math.max(temp, -next);
		}
		return temp;
	}
}