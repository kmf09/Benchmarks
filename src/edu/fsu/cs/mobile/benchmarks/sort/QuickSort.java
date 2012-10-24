/*
 * Author: Ira Ray Jenkins
 * Original algorithm: http://algs4.cs.princeton.edu/23quicksort/Quick.java.html
 * Original Vertex3D: mibench - qsort_large.c
 */
package edu.fsu.cs.mobile.benchmarks.sort;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class QuickSort {
	public static class Vertex3D implements Comparable<Vertex3D> {
		int x, y, z;
		double distance;

		public Vertex3D(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)
					+ Math.pow(z, 2));
		}

		public Vertex3D(String input) {
			this(Integer.parseInt(input.split("[ \t]+")[0]), Integer
					.parseInt(input.split("[ \t]+")[1]), Integer.parseInt(input
					.split("[ \t]+")[2]));
		}

		public int compareTo(Vertex3D another) {
			return this.distance < another.distance ? -1
					: this.distance > another.distance ? 1 : 0;
		}

		@Override
		public String toString() {
			return String.valueOf(x) + " " + String.valueOf(y) + " "
					+ String.valueOf(z);
		}
	}

	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "QuickSort";
	private static final int SMALL_SIZE = 10000;

	private static final int LARGE_SIZE = 60000;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static int partition(Comparable[] a, int lo, int hi) {
		int i = lo;
		int j = hi + 1;
		Comparable v = a[lo];
		for (;;) {
			while (a[++i].compareTo(v) < 0)
				if (i == hi)
					break;

			while (v.compareTo(a[--j]) < 0)
				if (j == lo)
					break;

			if (i >= j)
				break;

			swap(a, i, j);
		}

		swap(a, lo, j);

		return j;
	}

	// this is the sorting algorithm
	@SuppressWarnings("rawtypes")
	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo)
			return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}

	@SuppressWarnings("rawtypes")
	public static void sortLarge(String fileName) {
		Comparable[] array = new Comparable[LARGE_SIZE];
		int i = 0;
		BufferedReader input = null;
		String line = null;

		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));

			while ((line = input.readLine()) != null && i < LARGE_SIZE)
				array[i++] = new Vertex3D(line);
		} catch (Exception e) {
			Log.e(PKG, TAG + ": Error reading input file.");
			e.printStackTrace();
			return;
		}

		sort(array, 0, i - 1);

		for (int j = 0; j < i; j++)
			Log.i(PKG, TAG + ": " + String.valueOf(array[j]));
	}

	public native static void sortLargeNative(String fileName);

	@SuppressWarnings("rawtypes")
	public static void sortSmall(String fileName) {
		Comparable[] array = new Comparable[SMALL_SIZE];
		int i = 0;
		BufferedReader input = null;
		String line = null;

		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));

			while ((line = input.readLine()) != null && i < SMALL_SIZE)
				array[i++] = line;
		} catch (Exception e) {
			Log.e(PKG, TAG + ": Error reading input file.");
			e.printStackTrace();
			return;
		}

		sort(array, 0, SMALL_SIZE - 1);

		for (i = 0; i < SMALL_SIZE; i++)
			Log.i(PKG, TAG + ": " + String.valueOf(array[i]));
	}

	public native static void sortSmallNative(String fileName);

	private static void swap(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
}
