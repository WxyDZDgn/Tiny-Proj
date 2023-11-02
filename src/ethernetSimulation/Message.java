package ethernetSimulation;

public class Message {
	private final String origin;
	private final String destination;

	private final String text;

	public Message(String origin, String text, String destination) {
		this.origin = origin;
		this.text = text;
		this.destination = destination;
	}

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return "'%s' from host '%s' to host '%s'".formatted(text, origin, destination);
	}
}
