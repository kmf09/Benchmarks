// Created by Katrina Fishman
package edu.fsu.cs.mobile.benchmarks.sort;

import java.util.ArrayList;

public class InsertionSort {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "InsertionSort";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;

	// this is the sorting algorithm
	private static ArrayList<Integer> sort(ArrayList<Integer> v) {
		int key, i, j; 
		for (j = 1; j != v.size(); j++) {
			key = v.get(j); 
			i = j - 1; 
			while ((i >= 0) && (v.get(i) > key)) {
				v.set(i+1, v.get(i));
				i -= 1; 
			}
			v.set(i+1, key);  
		}
		return v; 
	} 

	public static void sortLarge(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		/*Iterator iter = array_list.iterator();
		while(iter.hasNext()) 
			Log.i("Large ArrayList Insertion", iter.next().toString());*/
	}

	public native static void sortLargeNative(ArrayList<Integer> array_list);

	public static void sortSmall(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		/*Iterator iter2 = array_list.iterator();
		while(iter2.hasNext()) 
			Log.i("Small ArrayList Insertion", iter2.next().toString());*/
	}

	public native static void sortSmallNative(ArrayList<Integer> array_list);
}


