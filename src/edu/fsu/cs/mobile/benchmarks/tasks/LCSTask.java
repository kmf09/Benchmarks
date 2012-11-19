package edu.fsu.cs.mobile.benchmarks.tasks;

import java.util.Random;

import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;
import edu.fsu.cs.mobile.benchmarks.math.LCS;

public class LCSTask extends BenchmarkTask {
	private static final int SMALL_SIZE = 5000;
	private static final int LARGE_SIZE = 10000;
	public static final int seed = 0;
	static Random ran; 
	int[] intArraySmall1, intArraySmall2, intArrayLarge1, intArrayLarge2; 
	char[] charArraySmall1, charArraySmall2, charArrayLarge1, charArrayLarge2; 
	
	@Override protected Void doInBackground(Object... params) {
		super.doInBackground(params);

		if (isNative()) {
			if (getSize() == BenchSize.SMALL)
				LCS.sortSmallNative(intArraySmall1, intArraySmall2, charArraySmall1, charArraySmall2);
			else
				LCS.sortLargeNative(intArrayLarge1, intArrayLarge2, charArrayLarge1, charArrayLarge2);
		} else {
			if (getSize() == BenchSize.SMALL)
				LCS.sortSmall(intArraySmall1, intArraySmall2, charArraySmall1, charArraySmall2);
			else
				LCS.sortLarge(intArrayLarge1, intArrayLarge2, charArrayLarge1, charArrayLarge2);
		}
		return null;
	}
	
	// generate values
	@Override protected void onPreExecute() {
		ran = new Random(seed);
		intArraySmall1 =  getIntArray (ran.nextInt(SMALL_SIZE)); 
		intArraySmall2 =  getIntArray (ran.nextInt(SMALL_SIZE)); 
		charArraySmall1 = getCharArray(ran.nextInt(SMALL_SIZE));
		charArraySmall2 = getCharArray(ran.nextInt(SMALL_SIZE));
		
		intArrayLarge1 =  getIntArray (ran.nextInt(LARGE_SIZE)); 
		intArrayLarge2 =  getIntArray (ran.nextInt(LARGE_SIZE)); 
		charArrayLarge1 = getCharArray(ran.nextInt(LARGE_SIZE));
		charArrayLarge2 = getCharArray(ran.nextInt(LARGE_SIZE));
	}
	
	public static int[] getIntArray(int size) {
		int[] intArray = new int[size];

		for (int i = 0; i < size; i++)
			intArray[i] = ran.nextInt(10000); 

		return intArray;
	}

	public static char[] getCharArray(int size) {
		char[] charArray = new char[size];
		int chary; 

		for (int i = 0; i < size; i++) {
			chary = ran.nextInt(26)+'A';
			charArray[i] = (char) chary; 
		}

		return charArray;
	}
}