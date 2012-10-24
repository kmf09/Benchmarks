/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.math;

public class SubsetSum {
	static {
		System.loadLibrary("native_bench");
	}

	public static boolean sum(int[] set, int size, int sum) {
		if (sum == 0)
			return true;

		if (size == 0)
			return false;

		return sum(set, size - 1, sum)
				|| sum(set, size - 1, sum - set[size - 1]);
	}

	public static native boolean sumNative(int[] array, int size, int sum);
}
