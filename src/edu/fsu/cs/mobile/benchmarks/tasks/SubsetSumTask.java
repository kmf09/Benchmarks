/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import android.util.Log;
import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.math.SubsetSum;

public class SubsetSumTask extends BenchmarkTask {
	private static final String TAG = "SubsetSumTask";

	private static final int[] input = { 94, 56, 62, 41, 20, 93, 100, 96, 85,
			65, 1, 44, 35, 84, 81, 9, 64, 24, 67, 8, 31, 60, 78, 57, 98, 16,
			34, 5, 73, 59 };

	@Override
	protected Void doInBackground(Object... params) {
		super.doInBackground(params);
		boolean result;

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				result = SubsetSum.sumNative(input, input.length, 432);
			else
				result = SubsetSum.sumNative(input, input.length, -1);
		} else {
			if (getSize() == BenchSize.SMALL)
				result = SubsetSum.sum(input, input.length, 432);
			else
				result = SubsetSum.sum(input, input.length, -1);
		}

		Log.i(PKG, TAG + (result ? ": YES" : ": NO"));

		return null;
	}

}
