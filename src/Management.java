import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.exit;

public class Management {
	/*
		This is a solution for the Management problem.
		Author: Dogaru Mihai-Sorin
	 */

	private class Node {
		String name;
		ArrayList<Node> subordinates;

		public Node(String name) {
			this.name = name;
			this.subordinates = new ArrayList<>();
		}

		public void print() {
			System.out.print("(" + this.name);
			if (this.subordinates.size() > 0) {
				System.out.print(", ");
			}

			Iterator<Node> iter = subordinates.iterator();
			while (iter.hasNext()) {
				Node employee = iter.next();
				employee.print();
				// After every subordinate except last, place a comma
				if (iter.hasNext())
					System.out.print(", ");
			}

			System.out.print(")");
		}
	}

	// Creates the logic tree out of the text tree.
	private Node generateGraph(String[] text) throws  Exception {
		Stack<Node> myS = new Stack<>();
		Node root = null;
		int len = text.length;
		// The text is split by open and close brackets. The separators are also included in text array.
		for (int i = 0; i < len; i++) {
			// If open bracket, create new node and add it as subordinate to the last on stack.
			if (text[i].compareTo("(") == 0 && i + 1 < len) {
				Node employee = new Node(text[i + 1]);
				if (employee.name.contains("(")) {
					throw new Exception();
				}
				i = i + 1;
				if (i == 1) {
					root = employee;
				} else {
					myS.peek().subordinates.add(employee);
				}
				myS.add(employee);
			} else if (text[i].compareTo(")") == 0) {
				// If close bracket, then last node on stack has no more kids, pop it.
				myS.pop();
			}
		}

		return root;
	}

	// Receives a graph and removes the Unavailable brances from it.
	private void pruneUnavailable(Node root) {

		if (root.name.contains("Unavailable")) {
			root.name = "EmptyGraph";
			root.subordinates = new ArrayList<>();
			return;
		}

		Stack<Node> myS = new Stack<>();
		myS.add(root);

		while (!myS.isEmpty()) {
			Node current = myS.pop();

			Iterator<Node> iter = current.subordinates.iterator();
			while (iter.hasNext()) {
				Node employee = iter.next();
				if (employee.name.contains("Unavailable")) {
					iter.remove();
				} else {
					myS.add(employee);
				}
			}
		}

	}
	public void solve(String[] text) {

		Node root = null;
		if (text.length == 2) {
			System.out.println("()");
			return;
		}

		if (text.length < 2) {
			System.out.println("Wrong format.");
			return;
		}

		try {
			root = this.generateGraph(text);
		} catch (EmptyStackException e) {
			System.out.println("You added too many closing brackets.");
			exit(1);
		} catch (Exception e) {
			System.out.println("Too many open brackets, wrong input.");
			exit(1);
		}
		this.pruneUnavailable(root);
		root.print();
	}

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputText = "";

		Management problem = new Management();

		try {
			inputText = reader.readLine();
		} catch (IOException e) {
			System.out.println("Something went wrong with IO.");
			exit(1);
		}

		// Remove spaces and commas, split by brackets and also keep the brackets in array.
		// It's like some tokenizer and also holding the tokens.
		String[] input = inputText.replaceAll(" |,", "").split("((?<=\\))|(?=\\))|(?<=\\()|(?=\\())");
		problem.solve(input);

	}
}
