/*
 * Author: Ira Ray Jenkins
 * Original implementation: http://algs4.cs.princeton.edu/55compression/Huffman.java.html
 */
package edu.fsu.cs.mobile.benchmarks.crypto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

import android.util.Log;

public class HuffmanCode {
	public static class Node implements Comparable<Node> {
		private char ch;
		private int freq;
		private Node left, right;

		Node(char ch, int freq, Node left, Node right) {
			this.ch = ch;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		// compare, based on frequency
		public int compareTo(Node that) {
			return this.freq - that.freq;
		}

		// is the node a leaf node?
		private boolean isLeaf() {
			return (left == null && right == null);
		}
	}

	private static final int R = 256;
	private static final int SMALL_SIZE = 500;
	private static final int LARGE_SIZE = 10000;

	static {
		System.loadLibrary("native_bench");
	}

	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";

	private static Node root;

	private static void buildCode(String[] st, Node node, String string) {
		if (!node.isLeaf()) {
			buildCode(st, node.left, string + "0");
			buildCode(st, node.right, string + "1");
		} else {
			st[node.ch] = string;
		}
	}

	private static Node buildTree(int[] frequency) {
		Node left, right;
		PriorityQueue<Node> pQueue = new PriorityQueue<Node>();

		// populate queue
		for (char i = 0; i < R; i++)
			if (frequency[i] > 0)
				pQueue.offer(new Node(i, frequency[i], null, null));

		// merge two smallest trees
		while (pQueue.size() > 1) {
			left = pQueue.poll();
			right = pQueue.poll();
			pQueue.offer(new Node('\0', left.freq + right.freq, left, right));
		}

		return pQueue.poll();
	}

	private static String compress(String input) {
		int[] frequency = new int[R];
		String output = "";

		for (int i = 0; i < input.length(); i++)
			frequency[input.charAt(i)]++;

		root = buildTree(frequency);

		String[] st = new String[R];
		buildCode(st, root, "");

		for (int i = 0; i < input.length(); i++) {
			output += st[input.charAt(i)];
		}

		return output;
	}

	private static String decompress(String input) {
		Node temp;
		String out = "";

		assert (root != null);

		for (int i = 0; i < input.length();) {
			temp = root;
			while (!temp.isLeaf()) {
				if (input.charAt(i++) == '1')
					temp = temp.right;
				else
					temp = temp.left;
			}
			out += temp.ch;
		}

		return out;
	}

	public static void run(String fileName) {
		BufferedReader input = null;
		String line = null;
		String str = "";
		String output;

		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));

			while ((line = input.readLine()) != null)
				str += line;

		} catch (Exception e) {
			Log.e(PKG, "Error reading input file.");
			e.printStackTrace();
		}

		output = compress(str);
		Log.i(PKG, "Compress: " + output);
		Log.i(PKG, "Decompress: " + decompress(output));
	}

	public static native void runNative(String fileName, int size);
}
