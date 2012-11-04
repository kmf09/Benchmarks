package edu.fsu.cs.mobile.benchmarks.search;
// http://www.java-forums.org/java-lang/7614-binary-tree-implementation-java.html
public class BinaryTree {
	Node root;

	static class Node {
		Node left;
		Node right;
		int value;

		public Node(int v) { 
			value = v;
		}
	}
	
	public BinaryTree() {
		root = null; 
	}
	
	public void insertMe(int value) {
		insert(root, value);
	}

	public void insert(Node node, int value) {
		if (node == null) {
			if (root == null)
				root = new Node(value);
			System.out.println("   Inserted " + value + " as the root");
		}
		else if (value < node.value) {
			if (node.left != null)
				insert(node.left, value);
			else {
				System.out.println("  Inserted " + value + " to left of "
						+ node.value);
				node.left = new Node(value);
			}
		} 
		else if (value > node.value) {
			if (node.right != null) {
				insert(node.right, value);
			} 
			else {
				System.out.println("  Inserted " + value + " to right of "
						+ node.value);
				node.right = new Node(value);
			}
		}
	}

	public static void printInOrder(Node node) {
		if (node != null) {
			printInOrder(node.left);
			System.out.println("  Traversed " + node.value);
			printInOrder(node.right);
		}
	}

	public static void main(String[] args) {
		
	}
}
