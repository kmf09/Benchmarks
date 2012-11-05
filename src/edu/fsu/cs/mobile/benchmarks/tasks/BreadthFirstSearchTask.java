package edu.fsu.cs.mobile.benchmarks.tasks;

import java.util.ArrayList;
import java.util.Random;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.search.BinaryTree;
import edu.fsu.cs.mobile.benchmarks.search.BreadthFirstSearch;

public class BreadthFirstSearchTask extends BenchmarkTask {
	private static final String SMALL_INPUT = "/data/benchmarks/data/qsort/input_small.dat";
	private static final String LARGE_INPUT = "/data/benchmarks/data/qsort/input_large.dat";

	private static final int SMALL_SIZE = 10000;
	private static final int LARGE_SIZE = 60000;

	ArrayList<Integer> array_list_small, array_list_large;
	int[] array_small, array_large;
	BinaryTree BTreeSmall = new BinaryTree();
	BinaryTree BTreeLarge = new BinaryTree();

	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				BreadthFirstSearch.searchSmallNative(array_small, BTreeSmall);
			else
				BreadthFirstSearch.searchLargeNative(array_large, BTreeLarge);
		} else {
			if (getSize() == BenchSize.SMALL)
				BreadthFirstSearch.searchSmall(array_small, BTreeSmall);
			else
				BreadthFirstSearch.searchLarge(array_large, BTreeLarge);
		}
		return null;
	} 

	// generate random edges
	@Override protected void onPreExecute() {
		int randNum; 
		
		array_small = new int[SMALL_SIZE];
		// seed is 1
		// same sequence of random numbers every time - deterministic
		Random ran = new Random(1);
		for (int i = 0; i < SMALL_SIZE; i++) {
			randNum = ran.nextInt();
			// if the number already exists get another one
		/*	for (int j = 0; j < array_small.length; j++) {
				if (array_small[j] == randNum) {
					randNum = ran.nextInt();
					j = 0; 
				}	
			} */
			array_small[i] = randNum;  
		}
		
		// Initialize the binary search tree
		BTreeSmall = new BinaryTree();
		for (int i = 0; i < array_small.length; i++) 
			BTreeSmall.insertMe(array_small[i]);
		
		/************************************************************/
		
		int[] array_large = new int[LARGE_SIZE]; 
		// seed is 1
		// same sequence of random numbers every time - deterministic
		for (int i = 0; i < LARGE_SIZE; i++) {
			randNum = ran.nextInt();
			// if the number already exists get another one
		/*	for (int j = 0; j < array_large.length; j++) {
				if (array_large[j] == randNum) {
					randNum = ran.nextInt();
					j = 0; 
				}	
			}*/
			array_large[i] = randNum;
		}
		// Initialize the binary search tree
		BTreeLarge = new BinaryTree();
		for (int i = 0; i < array_large.length; i++) 
			BTreeLarge.insertMe(array_large[i]);

		super.onPreExecute();
	}
}