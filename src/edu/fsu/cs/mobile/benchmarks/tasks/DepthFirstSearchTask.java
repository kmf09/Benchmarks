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

	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				DepthFirstSearch.sortSmallNative(array_list_small);
			else
				DepthFirstSearch.sortLargeNative(array_list_large);
		} else {
			if (getSize() == BenchSize.SMALL)
				DepthFirstSearch.sortSmall(array_list_small);
			else
				DepthFirstSearch.sortLarge(array_list_large);
		}
		return null;
	}

	// generate random edges
	@Override protected void onPreExecute() {
		int randomNum1, randomNum2;
		array_list_small = new ArrayList<Integer>();
		// seed is 1
		// same sequence of random numbers every time - deterministic
		Random ran = new Random(1);
		for (int i = 0; i < SMALL_SIZE; i++){
			randomNum1 = ran.nextInt(1000);
			randomNum2 = ran.nextInt(1000);
			array_list_small.add(randomNum1);
			array_list_small.add(randomNum2);
			array_list_small.add(randomNum2);
			array_list_small.add(randomNum1);
		}

		array_list_large = new ArrayList<Integer>(); 
		// seed is 1
		// same sequence of random numbers every time - deterministic
		for (int i = 0; i < LARGE_SIZE; i++) {
			randomNum1 = ran.nextInt(1000);
			randomNum2 = ran.nextInt(1000);
			array_list_large.add(randomNum1);
			array_list_large.add(randomNum2);
			array_list_large.add(randomNum2);
			array_list_large.add(randomNum1);
		}

		super.onPreExecute();
	}
}