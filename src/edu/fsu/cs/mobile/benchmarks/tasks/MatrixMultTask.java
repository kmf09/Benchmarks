/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.math.Matrix;

public class MatrixMultTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/mmult/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/mmult/input_large.dat";
	private static final int SMALL_SIZE = 25;
	private static final int LARGE_SIZE = 200;

	@Override
	protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				Matrix.multiplyNative(SMALL_INPUT, SMALL_SIZE);
			else
				Matrix.multiplyNative(LARGE_INPUT, LARGE_SIZE);
		} else {
			if (getSize() == BenchSize.SMALL)
				Matrix.multiply(SMALL_INPUT, SMALL_SIZE);
			else
				Matrix.multiply(LARGE_INPUT, LARGE_SIZE);
		}

		return null;
	}
}
