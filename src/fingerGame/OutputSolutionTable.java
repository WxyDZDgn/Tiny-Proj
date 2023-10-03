package fingerGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OutputSolutionTable {
	private static final int N = 10;
	private static int[][][][] res;
	private static boolean[][][][] vis;

	private static String getTable() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<tr><td rowspan='2' colspan='2'>S</td>");
		for(int i = 0; i < N; ++i) {
			stringBuilder.append("<td id='%dc' colspan='%d'>%d</td>\n".formatted(i, N, i));
		}
		stringBuilder.append("</tr><tr>");
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < N; ++j) {
				stringBuilder.append(
						"<td class='%dc%d' onmousemove='hoverNode(this, false)' onmouseleave='hoverNode(this, true)'>%d</td>\n".formatted(
								i, j, j));
			}
		}
		stringBuilder.append("</tr>");

		for(int i = 0; i < N; ++i) {
			stringBuilder.append("<tr>");
			stringBuilder.append("<td id='%dr' rowspan='%d'>%d</td>\n".formatted(i, N, i));
			for(int j = 0; j < N; ++j) {
				if(j > 0) stringBuilder.append("<tr>");
				stringBuilder.append(
						"<td class='%dr%d' onmousemove='hoverNode(this, false)' onmouseleave='hoverNode(this, true)'>%d</td>\n".formatted(
								i, j, j));
				for(int k = 0; k < N; ++k) {
					for(int l = 0; l < N; ++l) {
						String s;
						int t = fun(i, j, k, l);
						if(t < 0) s = "D";
						else if(t > 0) s = "V";
						else s = " ";
						stringBuilder.append(
								"<td class='%dr%d %dc%d' id='%dr%d %dc%d' onmousemove='hoverNode(this, false)' onmouseleave='hoverNode(this, true)' onclick='clickNode(this)'>%s</td>\n".formatted(
										i, j, k, l, i, j, k, l, s));
					}
				}
				if(j < N - 1) stringBuilder.append("</tr>");
			}


			stringBuilder.append("</tr>");
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		res = new int[N][N][N][N];
		vis = new boolean[N][N][N][N];
		try(PrintWriter output = new PrintWriter("output.html")) {
			output.println("""
					<!DOCTYPE html>
					<head>
						<title>获胜情况</title>
						<style>
							table {
								width: 100%%;
							}
							td {
								background-color: white;
								text-align: center;
								border: 1px solid black;
							}
						</style>
						<script type="text/javascript">
							var clickedRow = false;
							var clickedRowNode;
							var clickedCol = false;
							var clickedColNode;
							var clickedNode;
							
							function clickNode(node) {
								var classes = node.className.split(' ');
								if(classes.length >= 2) {
									if(clickedRow && clickedCol) {
										clickedRow = clickedCol = false;
										hoverNode(clickedNode, true);
										hoverNode(node, false);
									} else if(!clickedRow && !clickedCol) {
										hoverNode(node, false);
										clickedNode = node;
										clickedRow = clickedCol = true;
									} else {
										if(clickedRow) {
											clickedNode = document.getElementById(clickedRow.className + ' ' + node.className.split(' ')[1]);
											clickedRow = false;
										} else {
											clickedNode = document.getElementById(node.className.split(' ')[0] + ' ' + clickedCol.className);
											clickedCol = false;
										}
										hoverNode(clickedNode, true);
										hoverNode(clickedNode, false);
										clickedRow = clickedCol = true;
									}
								} else {
									var s = classes[0];
									var isRow = (s[parseInt(s.length / 2)] == 'r');
									//???????????????????????????????????????
								}
							}
							function hoverNode(node, leave) {
								if(clickedRow && clickedCol) return;
								var classes = node.className.split(' ');
								var color = leave ? "white" : "#ffbf00";
								
								if(classes.length <= 1) {
									var s = classes[0];
									var ls = document.getElementsByClassName(s);
									for(var each of ls) {
										each.style.background = color;
									}
									return;
									//???????????????????????????????????????
								}
								
								var rowLs = !clickedRow ? document.getElementsByClassName(classes[0]) : document.getElementsByClassName(clickedRowNode.className.split(' ')[0]);
								var colLs = !clickedCol ? document.getElementsByClassName(classes[1]) : document.getElementsByClassName(clickedColNode.className.split(' ')[1]);
								
								for(var each of rowLs) {
									each.style.background = color;
								}
								document.getElementById(classes[0].substring(0, parseInt(classes[0].length / 2) + 1)).style.background = color;
								
								for(var each of colLs) {
									each.style.background = color;
								}
								document.getElementById(classes[1].substring(0, parseInt(classes[1].length / 2) + 1)).style.background = color;
								
							}
						</script>
					</head>
					<body>
						<table style="border: 1px solid black; border-collapse: collapse; font-family: 'Courier New', monospace;">
							<caption>初始状态已知时, 先手（左边）获胜情况 - V: 胜利 - D: 失败</caption>
							%s
						</table>
					</body>
					""".formatted(getTable()));
		} catch(FileNotFoundException e) {
			throw new RuntimeException(e);
		}
//		Manager manager = new Manager(res);
//		manager.run();
//		fingerGame.Player[] players = new fingerGame.Player[]{new fingerGame.Player(), new fingerGame.Player()};
//		int currentPlayer = 0;
//		List<fingerGame.Choice> finalChoice = new ArrayList<>();
//		List<fingerGame.Choice> win = new ArrayList<>();
//		List<fingerGame.Choice> lose = new ArrayList<>();
//
//		while(true) {
//			win.clear();
//			lose.clear();
//			for(int i = 0; i < 2; ++ i) {
//				for(int j = 0; j < 2; ++ j) {
//					if(!players[currentPlayer].isAvailable(i, players[1 - currentPlayer], j)) continue;
//					if(players[currentPlayer].isWin(i, players[1 - currentPlayer], j) > 0) win.add(new fingerGame.Choice(players[currentPlayer], i, players[1 - currentPlayer], j));
//					else lose.add(new fingerGame.Choice(players[currentPlayer], i, players[1 - currentPlayer], j));
//				}
//			}
//			fingerGame.Choice choice = (win.isEmpty()) ? (lose.get((int)(Math.random() * lose.size()))) : (win.get((int)(Math.random() * win.size())));
//			finalChoice.add(choice);
////			System.out.println(choice);
//			players[currentPlayer].step(choice.currentHand, choice.nextPlayer, choice.nextHand);
//			if(players[currentPlayer].isWin()) break;
//			currentPlayer = 1 - currentPlayer;
//		}
//		for(fingerGame.Choice each : finalChoice) {
//			System.out.println(each);
//		}
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