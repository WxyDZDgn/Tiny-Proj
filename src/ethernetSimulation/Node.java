package ethernetSimulation;

public abstract class Node {
	protected String id;

	public Node(String id) {
		this.id = id;
	}

	public abstract void sendMessage(Message message, Node preNode);

	public abstract void receiveMessage(Message message, Node previous);

	public abstract boolean isAvailable(int port);

	public abstract void connectTo(Node node, int port);
}
