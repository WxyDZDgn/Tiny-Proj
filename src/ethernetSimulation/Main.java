package ethernetSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static HashMap<String, Node> hash;
	private static List<Node> ls;
	private static Scanner scanner;
	private static int numberOfHosts = 0;
	private static int numberOfPorts = 0;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		hash = new HashMap<>();
		ls = new ArrayList<>();
		int op;
		do {
			showMainMenu();
			op = scanner.nextInt();
			switch(op) {
				case 1:
					createGraph();
					break;
				case 2:
					simulation();
					break;
				case 3:
					clearGraph();
					break;
				case 4:
					showGraph();
					break;
			}
		} while(op != 0);
		scanner.close();
	}

	private static void showMainMenu() {
		System.out.println("""
				  			LAN SIMULATION
				[1] Create Nodes
				[2] Simulate LAN
				[3] Clear all
				[4] Show whole graph
				[0] Exit
				"""
		);
		System.out.print("Please enter the number to make a choice: ");
	}

	public static void createGraph() {
		int op;
		do {
			showGraph();
			showCreateGraphMenu();
			op = scanner.nextInt();
			switch(op) {
				case 1:
					createHost();
					break;
				case 2:
					createSwitch();
					break;
				case 3:
					clearGraph();
					break;
			}
		} while(op != 0);
	}

	public static void simulation() {
		if(numberOfHosts <= 1) {
			System.err.println("At least 2 hosts, try again.");
			return;
		}
		int op;
		do {
			showGraph();
			showSimulationMenu();
			op = scanner.nextInt();
			switch(op) {
				case 1:
					sendFrame();
					break;
				case 2:
					clearSwitchingTables();
					break;
			}
		} while(op != 0);
	}

	public static void clearGraph() {
		hash.clear();
		ls.clear();
	}

	public static void showGraph() {
		int s = ls.size();
		String[] strings = new String[s];
		for(int i = 0; i < s; ++i) {
			strings[i] = ls.get(i).toString();
		}
		System.out.printf("""
				-----------------------------------------------------------
								NODE INFORMATION
				Total Nodes: %d
				Information:
				%s
				-----------------------------------------------------------
				""", ls.size(), String.join("\n", String.join("\n", strings)));
	}

	private static void showCreateGraphMenu() {
		System.out.println("""
								CREATE NODES
				[1] Create a new host
				[2] Create a new switch
				[3] Clear whole graph
				[0] Return
				"""
		);
		System.out.print("Please enter the number to make a choice: ");

	}

	private static void createHost() {
		if(numberOfPorts <= 0 && !ls.isEmpty()) {
			System.err.println("No extra ports, try again.");
			return;
		}
		System.out.print("Please enter the name of a new host: ");
		String hostName = scanner.next();
		if(hash.getOrDefault(hostName, null) != null) {
			System.err.println("This host name has existed, try again.");
			return;
		}
		if(ls.isEmpty()) {
			Node node = new Host(hostName, null);
			ls.add(node);
			hash.putIfAbsent(hostName, node);
			++numberOfPorts;
		} else {
			System.out.print("Please enter the existed node's name to make connection: ");
			String target = scanner.next();
			Node targetNode = hash.getOrDefault(target, null);
			if(targetNode == null) {
				System.err.println("This node name does not exist, try again.");
				return;
			}
			if(targetNode instanceof Host && targetNode.isAvailable(0)) {
				Node cur = new Host(hostName, targetNode);
				targetNode.connectTo(cur, 0);
				ls.add(cur);
				hash.put(hostName, cur);
			} else if(targetNode instanceof Switch) {
				System.out.print("Please enter the target port of the switch: ");
				int port = scanner.nextInt();
				if(!targetNode.isAvailable(port)) {
					System.err.println("That port has been taken, try again.");
					return;
				}
				Node cur = new Host(hostName, targetNode);
				targetNode.connectTo(cur, port);
				ls.add(cur);
				hash.put(hostName, cur);
			} else {
				System.err.println("Unreachable type of node, try again.");
				return;
			}
			--numberOfPorts;
		}
		System.out.printf("Creating host '%s' successfully.\n", hostName);
		++numberOfHosts;
	}

	private static void createSwitch() {
		if(numberOfPorts <= 0 && !ls.isEmpty()) {
			System.err.println("No extra ports, try again.");
			return;
		}
		System.out.print("Please enter the name of a new switch: ");
		String hostName = scanner.next();
		if(hash.getOrDefault(hostName, null) != null) {
			System.err.println("This host name has existed, try again.");
			return;
		}
		if(ls.isEmpty()) {
			Node node = new Switch(hostName, null, null);
			ls.add(node);
			hash.putIfAbsent(hostName, node);
			numberOfPorts += 4;
		} else {
			System.out.print("Please enter the existed node's name to make connection: ");
			String target = scanner.next();
			Node targetNode = hash.getOrDefault(target, null);
			if(targetNode == null) {
				System.err.println("This node name does not exist, try again.");
				return;
			}
			if(targetNode instanceof Host && targetNode.isAvailable(0)) {
				System.out.print("Please enter the current port of the switch: ");
				int curPort = scanner.nextInt();
				Node cur = new Switch(hostName, targetNode, curPort);
				targetNode.connectTo(cur, curPort);
				ls.add(cur);
				hash.put(hostName, cur);
			} else if(targetNode instanceof Switch) {
				System.out.print("Please enter the current port of the switch: ");
				int curPort = scanner.nextInt();
				System.out.print("Please enter the target port of the switch: ");
				int tarPort = scanner.nextInt();
				if(!targetNode.isAvailable(tarPort)) {
					System.err.println("This port has been taken, try again.");
					return;
				}
				Node cur = new Switch(hostName, targetNode, curPort);
				targetNode.connectTo(cur, tarPort);
				ls.add(cur);
				hash.put(hostName, cur);
			} else {
				System.err.println("Unreachable type of node, try again.");
				return;
			}
			numberOfPorts += 2;
		}
		System.out.printf("Creating switch '%s' successfully.\n", hostName);

	}

	private static void showSimulationMenu() {
		System.out.println("""
								SIMULATE LAN
				[1] Send frame
				[2] Clear switch table
				[0] Return
				"""
		);
		System.out.print("Please enter the number to make a choice: ");

	}

	private static void sendFrame() {
		System.out.println("Please enter the original node's name: ");
		String sender = scanner.next();
		Node senderNode = hash.getOrDefault(sender, null);
		if(senderNode == null) {
			System.err.println("This node does not exist, try again.");
			return;
		} else if(!(senderNode instanceof Host)) {
			System.err.println("This node is not HOST, try again.");
			return;
		}
		System.out.println("Please enter the target node's name: ");
		String receiver = scanner.next();
		Node receiverNode = hash.getOrDefault(receiver, null);
		if(receiverNode == null) {
			System.err.println("That node does not exist, try again.");
			return;
		} else if(!(receiverNode instanceof Host)) {
			System.err.println("That node is not HOST, try again.");
			return;
		}
		System.out.println("Please enter the message to be sent (without spaces): ");
		String text = scanner.next();
		Message message = new Message(sender, text, receiver);
		senderNode.sendMessage(message, null);
	}

	private static void clearSwitchingTables() {
		for(Node each : ls) {
			if(each instanceof Switch) {
				((Switch) each).clearSwitchingTable();
			}
		}
	}
}
