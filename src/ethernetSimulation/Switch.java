package ethernetSimulation;

import java.util.HashMap;

public class Switch extends Node {
	protected Node[] nodes;

	protected HashMap<String, Integer> switchingTable;
	protected HashMap<Node, Integer> nodeHash;

	public Switch(String id, Node node, Integer port) {
		super(id);
		this.nodes = new Node[4];
		this.switchingTable = new HashMap<>();
		this.nodeHash = new HashMap<>();
		if(port == null) return;
		this.nodes[port] = node;
		this.nodeHash.putIfAbsent(node, port);
	}

	@Override
	public String toString() {
		int length = nodes.length;
		String[] ids = new String[length];
		for(int i = 0; i < length; ++i) {
			ids[i] = nodes[i] == null ? "null" : nodes[i].id;
		}
		return "Node '%s': Switch, whose ports are connected to [ %s ] separately.".formatted(
				super.id, String.join(", ", ids));
	}

	public void clearSwitchingTable() {
		switchingTable.clear();
	}

	@Override
	public void sendMessage(Message message, Node preNode) {
		int port = switchingTable.getOrDefault(message.getDestination(), -1);
		if(port < 0) {

			System.out.printf(
					"Node '%s': Switch, cannot find '%s', broadcasting.\n", super.id, message.getDestination());
			for(Node each : nodes) {
				if(each != null && !preNode.equals(each))
					each.receiveMessage(message, this);
			}
		} else {
			System.out.printf("Node '%s': Switch, '%s' is on port %d. sending.\n", super.id, message.getOrigin(), port);
			nodes[port].receiveMessage(message, this);
		}
	}

	@Override
	public void receiveMessage(Message message, Node previous) {
		int port = nodeHash.getOrDefault(previous, -1);
		if(port < 0) return;
		if(switchingTable.getOrDefault(message.getOrigin(), -1) >= 0)
			System.out.printf(
					"Node '%s': Switch, READ port of sender host '%s' is already written.%n", super.id,
					message.getOrigin()
			);
		else
			System.out.printf(
					"Node '%s': Switch, WRITE port of sender host '%s' is %d.%n", super.id, message.getOrigin(), port);
		switchingTable.putIfAbsent(message.getOrigin(), port);
		sendMessage(message, previous);
	}

	@Override
	public boolean isAvailable(int port) {
		return nodes[port] == null;
	}

	public void connectTo(Node node, int port) {
		if(nodes[port] != null) return;
		nodes[port] = node;
		nodeHash.put(node, port);
	}


}
