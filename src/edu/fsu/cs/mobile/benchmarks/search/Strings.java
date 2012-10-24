/*
 * Author: Ira Ray Jenkins
 * Algorithms taken from: http://en.wikipedia.org/wiki/Longest_common_subsequence
 *                        http://en.wikipedia.org/wiki/Longest_common_substring_problem
 */
package edu.fsu.cs.mobile.benchmarks.search;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class Strings {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final int SMALL_SIZE = 500;
	private static final int LARGE_SIZE = 10000;

	// private static void backtrack(char[][] c, String a, String b, int i, int
	// j) {
	// if (i == 0 || j == 0)
	// return;
	//
	// if (a.charAt(i - 1) == b.charAt(j - 1)) {
	// backtrack(a, b, i - 1, j - 1);
	// longestString += a.charAt(i - 1);
	// } else if (c[i][j - 1] > c[i - 1][j])
	// backtrack(a, b, i, j - 1);
	// else
	// backtrack(a, b, i - 1, j);
	// }

	static {
		System.loadLibrary("native_bench");
	}

	private static String backtrack(int[][] c, String a, String b, int i, int j) {
		StringBuffer longest = new StringBuffer();

		while (i > 0 && j > 0) {
			if (a.charAt(i - 1) == b.charAt(j - 1)) {
				j--;
				longest.append(a.charAt(--i));
			} else if (c[i][j - 1] > c[i - 1][j])
				j--;
			else
				i--;
		}

		return longest.reverse().toString();
	}

	public static void longestSubsequence(String fileName) {
		BufferedReader input = null;
		String a, b;

		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));

			if ((a = input.readLine()) != null
					&& (b = input.readLine()) != null) {
				longestSubsequence(a, b);
			} else
				throw new Exception();
		} catch (Exception e) {
			Log.e(PKG, "Error reading input file.");
			e.printStackTrace();
		}
	}

	private static void longestSubsequence(String a, String b) {
		// check for null or empty strings
		if (a == null || b == null || a.equals("") || b.equals("")) {
			Log.i(PKG, "Empty input.");
			return;
		}
		// create matrix
		int[][] c = new int[a.length() + 1][b.length() + 1];

		// find lcs, store length in c[a.length()][b.length()]
		for (int i = 1; i <= a.length(); i++)
			for (int j = 1; j <= b.length(); j++)
				if (a.charAt(i - 1) == b.charAt(j - 1))
					c[i][j] = c[i - 1][j - 1] + 1;
				else
					c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);

		Log.i(PKG, backtrack(c, a, b, a.length(), b.length()));
	}

	public static native void longestSubsequenceNative(String fileName, int size);

	public static void longestSubstring(String fileName) {
		BufferedReader input = null;
		String a, b;

		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));

			if ((a = input.readLine()) != null
					&& (b = input.readLine()) != null) {
				longestSubstring(a, b);
			} else
				throw new Exception();
		} catch (Exception e) {
			Log.e(PKG, "Error reading input file.");
			e.printStackTrace();
		}
	}

	private static void longestSubstring(String a, String b) {
		if (a == null || b == null || a.equals("") || b.equals("")) {
			Log.i(PKG, "Empty input.");
			return;
		}

		int[][] c = new int[a.length() + 1][b.length() + 1];
		int z = 0;
		String lcs = "";

		for (int i = 1; i <= a.length(); i++)
			for (int j = 1; j <= b.length(); j++)
				if (a.charAt(i - 1) == b.charAt(j - 1)) {
					if (i == 1 || j == 1)
						c[i][j] = 1;
					else
						c[i][j] = c[i - 1][j - 1] + 1;

					if (c[i][j] > z) {
						z = c[i][j];
						lcs = "";
					}

					if (c[i][j] == z) {
						lcs = a.substring(i - z + 1, i);
					}
				} else
					c[i][j] = 0;

		Log.i(PKG, "Longest common substring: " + lcs);
	}

	public static native void longestSubstringNative(String fileName, int size);
}
