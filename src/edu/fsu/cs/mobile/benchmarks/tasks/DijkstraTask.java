/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.util.Log;
import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.graph.Dijkstra;

public class DijkstraTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/graph/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/graph/input_large.dat";
	BufferedReader inputSmall = null;
	BufferedReader inputLarge = null;
	String line = null;
	int size = 0;
	int[][] G = null;
	int[] result = null;
	String[] split;
	
	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);
		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				Dijkstra.searchNative(SMALL_INPUT);
			else
				Dijkstra.searchNative(LARGE_INPUT);
		} else {
			if (getSize() == BenchSize.SMALL)
				Dijkstra.search(size, G);
			else
				Dijkstra.search(size, G);
		}
		return null;
	}

	@Override protected void onPreExecute() {
		try {
			inputSmall = new BufferedReader(new InputStreamReader(
					new FileInputStream(SMALL_INPUT)));

			size = Integer.parseInt(inputSmall.readLine());
			G = new int[size][size];
			result = new int[size];

			while ((line = inputSmall.readLine()) != null) {
				split = line.split(" ");
				if (split.length != 3)
					throw new Exception();

				G[Integer.parseInt(split[0])][Integer.parseInt(split[1])] = Integer
				.parseInt(split[2]);
			}
		} catch (Exception e) {
			Log.e(PKG, "Error reading input file.");
			e.printStackTrace();
			return;
		}
		
		try {
			inputLarge = new BufferedReader(new InputStreamReader(
					new FileInputStream(LARGE_INPUT)));

			size = Integer.parseInt(inputLarge.readLine());
			G = new int[size][size];
			result = new int[size];

			while ((line = inputLarge.readLine()) != null) {
				split = line.split(" ");
				if (split.length != 3)
					throw new Exception();

				G[Integer.parseInt(split[0])][Integer.parseInt(split[1])] = Integer
				.parseInt(split[2]);
			}
		} catch (Exception e) {
			Log.e(PKG, "Error reading input file.");
			e.printStackTrace();
			return;
		}
	}
}
