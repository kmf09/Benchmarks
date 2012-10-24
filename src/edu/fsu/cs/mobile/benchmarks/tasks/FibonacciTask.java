/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.math.Functions;

public class FibonacciTask extends BenchmarkTask {

	@Override
	protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				Functions.nthFibonacciNative(20);
			else
				Functions.nthFibonacciNative(40);
		} else {
			if (getSize() == BenchSize.SMALL)
				Functions.nthFibonacci(20);
			else
				Functions.nthFibonacci(40);
		}

		return null;
	}
}
