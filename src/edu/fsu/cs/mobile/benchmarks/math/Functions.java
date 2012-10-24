/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.math;

import android.util.Log;

public class Functions {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "Functions";

	static {
		System.loadLibrary("native_bench");
	}

	private static long[] euclid(long p, long q) {
		if (q == 0)
			return new long[] { p, 1, 0 };

		long[] vals = euclid(q, p % q);
		long d = vals[0];
		long a = vals[2];
		long b = vals[1] - (p / q) * vals[2];
		return new long[] { d, a, b };
	}

	public static void extendedEuclid(long p, long q) {
		long[] result = euclid(p, q);
		Log.i(PKG,
				TAG + ": " + String.valueOf(result[0]) + " "
						+ String.valueOf(result[1]) + " "
						+ String.valueOf(result[2]));
	}

	public static native void extendedEuclidNative(long p, long q);

	private static long fibonacci(long n) {
		if (n < 2)
			return n;
		else
			return (fibonacci(n - 1) + fibonacci(n - 2));

	}

	public static void nthFibonacci(long n) {
		Log.i(PKG, TAG + ": " + String.valueOf(fibonacci(n)));
	}

	public static native void nthFibonacciNative(long n);
}
