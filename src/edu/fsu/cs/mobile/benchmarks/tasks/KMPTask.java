/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.search.KMP;

public class KMPTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/strings/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/strings/input_large.dat";

	@Override
	protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				KMP.searchNative(SMALL_INPUT, 0);
			else
				KMP.searchNative(LARGE_INPUT, 1);
		} else {
			if (getSize() == BenchSize.SMALL)
				KMP.search(SMALL_INPUT, BenchSize.SMALL);
			else
				KMP.search(LARGE_INPUT, BenchSize.LARGE);
		}

		return null;
	}

}
