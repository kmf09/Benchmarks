// Created by: Katrina Fishman
package edu.fsu.cs.mobile.benchmarks.sort;
import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

public class BitonicSort {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "BitonicSort";

	// this is the sorting algorithm
	private static ArrayList<Integer> sort(ArrayList<Integer> V, int size) {
		sortup(0, size, V); 
		return V; 
	}

	static void mergedown(int m, int n, ArrayList<Integer> V) {
		int temp; 

		if (n == 0)
			return; 
		for (int i = 0; i < n; i++) {
			if (V.get(m+i) < V.get(m+i+n)) {
				temp     = V.get(m+i); 
				V.set(m+i, m+i+n); 
				V.set(m+i+n, temp);
			}
		}

		mergedown(m,   n/2, V); 
		mergedown(m+n, n/2, V); 
	}

	static void mergeup(int m, int n, ArrayList<Integer> V) {
		int temp; 

		if (n == 0)
			return; 
		for (int i = 0; i < n; i++) {
			if (V.get(m+i) > V.get(m+i+n)) {
				temp     = V.get(m+i); 
				V.set(m+i, m+i+n); 
				V.set(m+i+n, temp); 
			}
		}
		mergeup(m,   n/2, V); 
		mergeup(m+n, n/2, V); 
	}

	static void sortdown(int m, int n, ArrayList<Integer> V) {
		if (n == 1)
			return; 
		sortup   (m, n/2, V); 
		sortdown ((m+(n/2)), n/2, V); 
		mergedown(m, n/2, V); 
	}

	static void sortup(int m, int n, ArrayList<Integer> V) { // from m to m+n
		if (n == 1) 
			return; 
		sortup  (m, n/2, V); 
		sortdown((m+(n/2)), n/2, V); 
		mergeup (m, n/2, V); 
	}

	static Boolean isWrong(ArrayList<Integer> V) {
		for (int i = 0; i < V.size() - 1; i++) {
			if (V.get(i) > V.get(i+1)) {
				Log.i("!!!Something is terribly incorrect!!!", "Not sorted correctly");
				return true; 
			}
		}
		return false; 
	}

	public static void sortLarge(ArrayList<Integer> array_list) {
		if (!isWrong(sort(array_list, array_list.size()))) {
			// Used for debugging purposes
			/*Iterator<Integer> iter = array_list.iterator();
			while(iter.hasNext()) 
				Log.i("Large ArrayList Bitonic", iter.next().toString());*/
		}
	}

	public native static void sortLargeNative(ArrayList<Integer> array_list);

	public static void sortSmall(ArrayList<Integer> array_list) {
		if (!isWrong(sort(array_list, array_list.size()))) {
			// Used for debugging purposes
			/*Iterator<Integer> iter = array_list.iterator();
			while(iter.hasNext()) 
				Log.i("Small ArrayList Bitonic", iter.next().toString());*/
		}
	}

	public native static void sortSmallNative(ArrayList<Integer> array_list);
}

