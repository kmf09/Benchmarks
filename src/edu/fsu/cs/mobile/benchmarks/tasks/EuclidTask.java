/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import edu.fsu.cs.mobile.benchmarks.math.Functions;

public class EuclidTask extends BenchmarkTask {

	@Override
	protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative())
			Functions.extendedEuclidNative(65, 40);
		else
			Functions.extendedEuclid(65, 40);

		return null;
	}
}
