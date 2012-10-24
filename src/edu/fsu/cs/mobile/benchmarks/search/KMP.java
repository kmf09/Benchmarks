/*
 * Author: Ira Ray Jenkins
 * Original algorithm: http://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm
 */
package edu.fsu.cs.mobile.benchmarks.search;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.util.Log;
import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;

public class KMP {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final int SMALL_SIZE = 500;
	private static final int LARGE_SIZE = 10000;

	static {
		System.loadLibrary("native_bench");
	}

	private static int[] failureFunction(char[] pattern) {
		int f[] = new int[pattern.length];
		int i = 2;
		int j = 0;

		f[0] = -1;
		f[1] = 0;

		while (i < pattern.length) {
			if (pattern[i - 1] == pattern[j])
				f[i++] = ++j;
			else if (j > 0)
				j = f[j];
			else
				f[i++] = 0;
		}

		return f;
	}

	private static void search(char[] text, char[] pattern) {
		int i, j;
		int[] f = failureFunction(pattern);

		i = j = 0;

		while (i + j < text.length) {
			if (text[i + j] == pattern[i]) {
				if (i == pattern.length - 1) {
					Log.i(PKG, "Match found at " + String.valueOf(j));
					return;
				}
				i++;
			} else {
				j = j + i - f[i];
				if (f[i] > -1)
					i = f[i];
				else
					i = 0;
			}
		}
		Log.i(PKG, "No match found.");
	}

	public static void search(String fileName, BenchSize size) {
		BufferedReader input = null;
		String a, b;

		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));

			if ((a = input.readLine()) != null
					&& (b = input.readLine()) != null) {
				if (size == BenchSize.SMALL)
					search(a.toCharArray(), "GTAGC".toCharArray());
				else
					search(a.toCharArray(), b.toCharArray());
			} else
				throw new Exception();
		} catch (Exception e) {
			Log.e(PKG, "Error reading input file.");
			e.printStackTrace();
		}
	}

	public static native void searchNative(String fileName, int size);
}
