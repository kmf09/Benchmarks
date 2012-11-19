package edu.fsu.cs.mobile.benchmarks.tasks;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.math.FFTNaiveBench;

public class FFTInPlaceTask extends BenchmarkTask {
	
	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				FFTNaiveBench.sortSmallNative();
			else
				FFTNaiveBench.sortLargeNative();
		} else {
			if (getSize() == BenchSize.SMALL)
				FFTNaiveBench.sortSmall();
			else
				FFTNaiveBench.sortLarge();
		}
		return null;
	}
}