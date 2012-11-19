package edu.fsu.cs.mobile.benchmarks.math;
import java.util.Random;

import android.util.Log;

// Katrina Fishman
// Longest Common Subsequence
// http://introcs.cs.princeton.edu/java/96optimization/LCS.java.html
public class LCS {
	static Random ran; 
	public static final int seed = 0;

	public static void LongestCommonSubsequence(int[] intArray1, int[] intArray2, char[] charArray1, char[] charArray2, boolean doInt) {
		ran = new Random(seed);
		int[][] opt; 
		int i, j;

		int intSize1 =  intArray1.length;
		int intSize2 =  intArray2.length;
		int charSize1 = charArray1.length;
		int charSize2 = charArray2.length;

		/* FOR THE INT ARRAY */ 
		if (doInt == true) {

			// opt[i][j] = length of LCS of x[i..M] and y[j..N]
			opt = new int[intSize1+1][intSize2+1];

			// compute length of LCS and all subproblems via dynamic programming
			for (i = intSize1-1; i >= 0; i--) {
				for (j = intSize2-1; j >= 0; j--) {
					if (intArray1[i] == intArray2[j])
						opt[i][j] = opt[i+1][j+1] + 1;
					else 
						opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
				}
			}

			// recover LCS itself and print it to standard output
			i = 0; j = 0;
			while(i < intSize1 && j < intSize2) {
				if (intArray1[i] == intArray2[j]) {
					Log.i("Answer: ", intArray1[i] + " ");
					i++; j++;
				}
				else if (opt[i+1][j] >= opt[i][j+1]) 
					i++;
				else                                 
					j++;
			}

			/* INT ARRAY END */

		}
		else { 
			/* FOR THE CHAR ARRAY */ 

			// opt[i][j] = length of LCS of x[i..M] and y[j..N]
			opt = new int[charSize1+1][charSize2+1];

			// compute length of LCS and all subproblems via dynamic programming
			for (i = charSize1-1; i >= 0; i--) {
				for (j = charSize2-1; j >= 0; j--) {
					if (charArray1[i] == charArray2[j])
						opt[i][j] = opt[i+1][j+1] + 1;
					else 
						opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
				}
			}

			// recover LCS itself and print it to standard output
			i = 0; j = 0;
			while(i < charSize1 && j < charSize2) {
				if (charArray1[i] == charArray2[j]) {
					Log.i("Answer: ", charArray1[i] + " ");
					i++;
					j++;
				}
				else if (opt[i+1][j] >= opt[i][j+1]) 
					i++;
				else                                 
					j++;
			}
		}
		/* CHAR ARRAY END */ 
	}

	public static void printIntArray (int[] array) {
		for (int i = 0; i < array.length; i++)
			Log.i("int array: ", "" + array[i]);
	}

	public static void printCharArray (char[] array) {
		for (int i = 0; i < array.length; i++)
			Log.i("char array: ", "" + array[i]);
	}

	public static void sortLarge(int[] intArray1, int[] intArray2, char[] charArray1, char[] charArray2) {
		LongestCommonSubsequence(intArray1, intArray2, charArray1, charArray2, true);
		LongestCommonSubsequence(intArray1, intArray2, charArray1, charArray2, false);
	}

	public native static void sortLargeNative(int[] intArray1, int[] intArray2, char[] charArray1, char[] charArray2);

	public static void sortSmall(int[] intArray1, int[] intArray2, char[] charArray1, char[] charArray2) {
		LongestCommonSubsequence(intArray1, intArray2, charArray1, charArray2, true);
		LongestCommonSubsequence(intArray1, intArray2, charArray1, charArray2, false);
	}

	public native static void sortSmallNative(int[] intArray1, int[] intArray2, char[] charArray1, char[] charArray2);
}
