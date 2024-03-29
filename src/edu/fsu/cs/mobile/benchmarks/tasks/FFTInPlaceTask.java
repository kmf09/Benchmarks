package edu.fsu.cs.mobile.benchmarks.tasks;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.math.FFTInPlaceBench;

public class FFTInPlaceTask extends BenchmarkTask {
	
	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				FFTInPlaceBench.sortSmallNative();
			else
				FFTInPlaceBench.sortLargeNative();
		} else {
			if (getSize() == BenchSize.SMALL)
				FFTInPlaceBench.sortSmall();
			else
				FFTInPlaceBench.sortLarge();
		}
		return null;
	}
}