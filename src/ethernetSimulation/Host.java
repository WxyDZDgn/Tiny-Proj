package ethernetSimulation;

public class Host extends Node {
	protected Node port;

	public Host(String id, Node port) {
		super(id);
		this.port = port;
	}

	@Override
	public String toString() {
		return "Node '%s': Host, connected to Node '%s'.".formatted(super.id, port == null ? "null" : port.id);
	}

	@Override
	public void sendMessage(Message message, Node preNode) {
		System.out.printf("Node '%s': host, send message [ %s ].\n", super.id, message);
		port.receiveMessage(message, this);
	}

	@Override
	public void receiveMessage(Message message, Node previous) {
		if(message.getDestination().equals(super.id)) {
			System.out.printf("Node '%s': host, received message [ %s ].\n", super.id, message);
		} else {
			System.out.printf("Node '%s': host, refused message [ %s ].\n", super.id, message);
		}
	}

	@Override
	public boolean isAvailable(int port) {
		return this.port == null;
	}

	public void connectTo(Node node, int port) {
		if(this.port != null) return;
		this.port = node;
	}
}
