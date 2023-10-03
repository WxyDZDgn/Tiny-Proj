package fingerGame;

public class Choice {
	public Player currentPlayer;
	public int currentHand;
	public Player nextPlayer;
	public int nextHand;

	public Choice(Player currentPlayer, int currentHand, Player nextPlayer, int nextHand) {
		this.currentPlayer = currentPlayer;
		this.currentHand = currentHand;
		this.nextPlayer = nextPlayer;
		this.nextHand = nextHand;
	}

	@Override
	public String toString() {
		String cHand = currentHand == Player.LEFT ? "LEFT" : "RIGHT";
		String nHand = nextHand == Player.LEFT ? "LEFT" : "RIGHT";
		return "%s: %s -> %s: %s".formatted(currentPlayer, cHand, nextPlayer, nHand);
	}
}
