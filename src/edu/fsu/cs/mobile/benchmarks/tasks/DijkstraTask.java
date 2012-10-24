/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.graph.Dijkstra;

public class DijkstraTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/graph/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/graph/input_large.dat";

	@Override
	protected Void doInBackground(Object... params) {
		super.doInBackground(params);
		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				Dijkstra.searchNative(SMALL_INPUT);
			else
				Dijkstra.searchNative(LARGE_INPUT);
		} else {
			if (getSize() == BenchSize.SMALL)
				Dijkstra.search(SMALL_INPUT);
			else
				Dijkstra.search(LARGE_INPUT);
		}
		return null;
	}
}
