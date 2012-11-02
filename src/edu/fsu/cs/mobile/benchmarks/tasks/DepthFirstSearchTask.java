package edu.fsu.cs.mobile.benchmarks.tasks;

import java.util.ArrayList;
import java.util.Random;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.search.DepthFirstSearch;

public class DepthFirstSearchTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/qsort/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/qsort/input_large.dat";

	private static final int SMALL_SIZE = 10000;
	private static final int LARGE_SIZE = 60000;

	ArrayList<Integer> array_list_small, array_list_large;
	static ArrayList<ArrayList<Integer>> graph_small, graph_large;

	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				DepthFirstSearch.sortSmallNative(graph_small);
			else
				DepthFirstSearch.sortLargeNative(graph_large);
		} else {
			if (getSize() == BenchSize.SMALL)
				DepthFirstSearch.sortSmall(graph_small);
			else
				DepthFirstSearch.sortLarge(graph_large);
		}
		return null;
	}

	// generate random edges
	@Override protected void onPreExecute() {
		graph_small = new ArrayList<ArrayList<Integer>>();
		graph_large = new ArrayList<ArrayList<Integer>>();
		int randomNum1, randomNum2, stable = 1;
		
		// seed is 1
		// same sequence of random numbers every time - deterministic
		Random ran = new Random(1);
		
		for (int i = 0; i < SMALL_SIZE; i++)
			graph_small.add(new ArrayList<Integer>()); 
		
		for (int i = 0; i < SMALL_SIZE; i++){
			randomNum1 = ran.nextInt(1000);
			randomNum2 = ran.nextInt(1000);
			addNeighbor_small(randomNum1, randomNum2);
		};
		
		for (int i = 0; i < graph_small.size(); i++) {
			addNeighbor_small(stable, graph_small.get(i).get(i));
		}
		 
		// seed is 1
		// same sequence of random numbers every time - deterministic
		for (int i = 0; i < SMALL_SIZE; i++)
			graph_small.add(new ArrayList<Integer>()); 
		
		for (int i = 0; i < SMALL_SIZE; i++){
			randomNum1 = ran.nextInt(1000);
			randomNum2 = ran.nextInt(1000);
			addNeighbor_small(randomNum1, randomNum2);
		};
		
		for (int i = 0; i < SMALL_SIZE; i++) {
			addNeighbor_small(stable, graph_small.get(i).get(i));
		}

		super.onPreExecute();
	}
	
	public static void addNeighbor_small(int vertex, int append) {
		graph_small.get(vertex).add(append);
		graph_small.get(append).add(vertex);
	}

	public static ArrayList<Integer> neighbors_small(int vertex) {
		return graph_small.get(vertex);
	}
	
	public static void addNeighbor_large(int vertex, int append) {
		graph_large.get(vertex).add(append);
		graph_large.get(append).add(vertex);
	}

	public static ArrayList<Integer> neighbors_large(int vertex) {
		return graph_large.get(vertex);
	}
}