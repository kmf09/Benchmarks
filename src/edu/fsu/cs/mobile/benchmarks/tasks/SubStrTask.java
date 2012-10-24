/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.search.Strings;

public class SubStrTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/strings/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/strings/input_large.dat";

	@Override
	protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				Strings.longestSubstringNative(SMALL_INPUT, 0);
			else
				Strings.longestSubstringNative(LARGE_INPUT, 1);
		} else {
			if (getSize() == BenchSize.SMALL)
				Strings.longestSubstring(SMALL_INPUT);
			else
				Strings.longestSubstring(LARGE_INPUT);
		}
		return null;
	}

}
