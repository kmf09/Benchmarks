package edu.fsu.cs.mobile.benchmarks.tasks;
import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.graph.UndirectedGraph;
import edu.fsu.cs.mobile.benchmarks.graph.Prim;

public class PrimTask extends BenchmarkTask {

	UndirectedGraph g; 

	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				Prim.sortSmallNative(g);
			else
				Prim.sortLargeNative(g);
		} else {
			if (getSize() == BenchSize.SMALL)
				Prim.sortSmall(g);
			else
				Prim.sortLarge(g);
		}
		return null;
	}

	// generate random vertices all connected
	@Override protected void onPreExecute() { g = new UndirectedGraph(10); }
}