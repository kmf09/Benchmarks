package edu.fsu.cs.mobile.benchmarks.tasks;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.graph.Prim;
import edu.fsu.cs.mobile.benchmarks.search.DepthFirstSearch;
import edu.fsu.cs.mobile.benchmarks.search.DepthFirstSearch.Vertex;

public class PrimTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/qsort/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/qsort/input_large.dat";

	private static final int SMALL_SIZE = 10000;
	private static final int LARGE_SIZE = 60000;

	int[][] G, t;
	int[] near;

	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				Prim.sortSmallNative(G, t, near);
			else
				Prim.sortLargeNative(G, t, near);
		} else {
			if (getSize() == BenchSize.SMALL)
				Prim.sortSmall(G, t, near);
			else
				Prim.sortLarge(G, t, near);
		}
		return null;
	}

	// generate random vertices all connected
	@Override protected void onPreExecute() {
		Random random = new Random();
		
		// Create graph with random number of vertices (using adjacency matrix)
		int n = random.nextInt(1000);
		G = new int[n+1][n+1];
		t = new int[n+1][3];
		near = new int[n+1];

		// Fill out adjacency matrix with random values
		for(int i=1;i<=n;i++)
		{
			for(int j=1;j<=n;j++)
			{
				if((i!=j)&&(i<j))
				{
					// Random weight for this edge
					G[j][i] = G[i][j] = random.nextInt();
					if(G[i][j] == 0 )
						G[j][i] = G[i][j] = 7001;
				}
				if(i==j)
					G[i][j]=7001;
			}
		}
		
		super.onPreExecute();
	}
}