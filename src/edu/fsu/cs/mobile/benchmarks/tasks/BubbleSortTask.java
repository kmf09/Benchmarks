package edu.fsu.cs.mobile.benchmarks.tasks;

import java.util.ArrayList;
import java.util.Random;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.sort.BubbleSort;

public class BubbleSortTask extends BenchmarkTask {
	private static final int SMALL_SIZE = 10000;
	private static final int LARGE_SIZE = 60000;
	public static final int seed = 0;
	
	ArrayList<Integer> array_list_small, array_list_large; 
	
	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				BubbleSort.sortSmallNative(array_list_small);
			else
				BubbleSort.sortLargeNative(array_list_large);
		} else {
			if (getSize() == BenchSize.SMALL)
				BubbleSort.sortSmall(array_list_small);
			else
				BubbleSort.sortLarge(array_list_large);
		}
		return null;
	}
	
	// generate random numbers
	@Override protected void onPreExecute() {
		array_list_small = new ArrayList<Integer>();
		// same sequence of random numbers every time - deterministic
		Random ran = new Random(seed);
		for (int i = 0; i < SMALL_SIZE; i++)
			array_list_small.add(ran.nextInt(1000000)); 
		
		array_list_large = new ArrayList<Integer>(); 
		// same sequence of random numbers every time - deterministic
		for (int i = 0; i < LARGE_SIZE; i++)
			array_list_large.add(ran.nextInt(1000000));
		
		super.onPreExecute();
	}
}