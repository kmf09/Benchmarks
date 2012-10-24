/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.math;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class Matrix {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "MatrixMultiply";

	static {
		System.loadLibrary("native_bench");
	}

	public static void multiply(String fileName, int size) {
		BufferedReader input = null;
		double[][] a = new double[size][size];
		double[][] b = new double[size][size];
		double[][] c;
		String line;
		String[] row;

		try {

			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));

			for (int i = 0; i < size; i++) {
				line = input.readLine();
				row = line.split(" ");
				for (int j = 0; j < size; j++)
					a[i][j] = Double.parseDouble(row[j]);
			}

			for (int i = 0; i < size; i++) {
				line = input.readLine();
				row = line.split(" ");
				for (int j = 0; j < size; j++)
					b[i][j] = Double.parseDouble(row[j]);
			}
		} catch (Exception e) {
			Log.e(PKG, TAG + ": Error reading input file.");
			e.printStackTrace();
		}

		c = multiply_ijk(a, b);

		for (int i = 0; i < c.length; i++)
			Log.i(PKG, String.valueOf(c[i][i]));
	}

	private static double[][] multiply_ijk(double[][] a, double[][] b) {
		int n = a.length;
		double[][] c = new double[n][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < n; k++)
					c[i][j] += a[i][k] * b[k][j];
		return c;
	}

	public static native void multiplyNative(String fileName, int size);
}
