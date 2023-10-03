package fingerGame;

public class Player {
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	private int[] hands;

	public Player() {
		hands = new int[]{1, 1};
	}

	public Integer isWin(int sHand, Player player, int oHand) {
		if(!isAvailable(sHand, player, oHand)) return null;
		return -OutputSimulationSteps.fun(
				player.hands[0], player.hands[1], (this.hands[sHand] + player.hands[oHand]) % 10,
				this.hands[1 - sHand]
		);
	}

	public boolean isAvailable(int sHand, Player player, int oHand) {
		return this.hands[sHand] != 0 && player.hands[oHand] != 0;
	}

	public Boolean isWin() {
		return hands[0] == 0 && hands[1] == 0;
	}

	public Choice step(int sHand, Player player, int oHand) {
		if(!isAvailable(sHand, player, oHand)) return null;
//		System.out.printf("%s: %d %d -> ", this, hands[LEFT], hands[RIGHT]);
		this.hands[sHand] = (this.hands[sHand] + player.hands[oHand]) % 10;
//		System.out.printf("%d %d\n", hands[LEFT], hands[RIGHT]);
		return new Choice(this, sHand, player, oHand);
	}

}
