package edu.fsu.cs.mobile.benchmarks.tasks;

import java.util.ArrayList;
import java.util.Random;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.math.ClosestPairNaive;
import edu.fsu.cs.mobile.benchmarks.math.Node;

public class ClosestPairNaiveTask extends BenchmarkTask {
	private static final int SMALL_SIZE = 5000;
	private static final int LARGE_SIZE = 10000;
	public static final int seed = 1;
	static Random ran; 
	
	static ArrayList<Node> ALsmall, ALlarge; 
	static int size = 10;
	
	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				ClosestPairNaive.sortSmallNative(ALsmall);
			else
				ClosestPairNaive.sortLargeNative(ALlarge);
		} else {
			if (getSize() == BenchSize.SMALL)
				ClosestPairNaive.sortSmall(ALsmall);
			else
				ClosestPairNaive.sortLarge(ALlarge);
		}
		return null;
	}
	
	// generate values
	@Override protected void onPreExecute() {
		ran = new Random(seed);
		ALsmall = new ArrayList<Node>();
		ALlarge = new ArrayList<Node>();

		Node n = new Node(); 
		n.x = 0; n.y = 0; 
		
		// CHANGE SIZE TO SMALL_SIZE AND LARGE_SIZE RESPECTIVELY
		for (int i = 0; i < size; i++)
			ALsmall.add(insert(ALsmall));
		
		for (int i = 0; i < size; i++)
			ALlarge.add(insert(ALlarge));
	}
	
	public static Node insert(ArrayList<Node> AL) {
		Node n = new Node();
		// TAKE OUT THE 50'S
		int x = ran.nextInt(50), y = ran.nextInt(50);
		// check if equal 
		for (int i = 0; i < AL.size(); i++) {
			while (AL.get(i).x == x && AL.get(i).y == y) {
				x = ran.nextInt(size); 
				y = ran.nextInt(size); 
				i = 0; 
			}
		}
		n.x = x; n.y = y; 
		return n; 
	}
}