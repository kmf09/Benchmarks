/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import android.util.Log;
import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.crypto.HuffmanCode;

public class HuffmanTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/strings/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/strings/input_large.dat";

	@Override
	protected Void doInBackground(Object... params) {
		super.doInBackground(params);
		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				Log.i("edu.fsu.cs.mobile.benchmarks.native", "Not implemented.");
			// HuffmanCode.runNative(SMALL_INPUT, 0);
			else
				Log.i("edu.fsu.cs.mobile.benchmarks.native", "Not implemented.");
			// HuffmanCode.runNative(LARGE_INPUT, 1);
		} else {
			if (getSize() == BenchSize.SMALL)
				HuffmanCode.run(SMALL_INPUT);
			else
				HuffmanCode.run(LARGE_INPUT);
		}

		return null;
	}
}
