/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.sort.QuickSort;

public class QuickSortTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/qsort/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/qsort/input_large.dat";

	@Override
	protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				QuickSort.sortSmallNative(SMALL_INPUT);
			else
				QuickSort.sortLargeNative(LARGE_INPUT);
		} else {
			if (getSize() == BenchSize.SMALL)
				QuickSort.sortSmall(SMALL_INPUT);
			else
				QuickSort.sortLarge(LARGE_INPUT);
		}
		return null;
	}
}
