// Created by: Katrina Fishman
package edu.fsu.cs.mobile.benchmarks.sort;
import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

public class BiDirectionalSort {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "BitonicSort";

	// this is the sorting algorithm
	private static ArrayList<Integer> sort(ArrayList<Integer> V) {
		Boolean flippedFlag = true; 
		int limit = V.size(), st = -1, temp; 

		while (st < limit) {
			flippedFlag = false; 
			st++; limit--; 
			for (int j = st; j < limit; j++) {
				if (V.get(j) > V.get(j+1)) {
					temp = V.get(j); 
					V.set(j, j+1); 
					V.set(j+1, temp);  
					flippedFlag = true; 
				}
			}

			if (!flippedFlag) 
				return V;
			flippedFlag = false; 
			for (int j = limit - 1; j >= st; --j) {
				if (V.get(j) > V.get(j+1)) {
					temp = V.get(j); 
					V.set(j, j+1); 
					V.set(j+1, temp); 
					flippedFlag = true; 
				}
			}
			if (!flippedFlag)
				return V;
		}       

		return V; 
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
		if (!isWrong(sort(array_list))) {
			// Used for debugging purposes
			Iterator<Integer> iter = array_list.iterator();
			while(iter.hasNext())  
				Log.i("Large ArrayList Bi-Directional", iter.next().toString());
		}
	}

	public native static void sortLargeNative(ArrayList<Integer> array_list);

	public static void sortSmall(ArrayList<Integer> array_list) {

		
		if (!isWrong(sort(array_list))) {
			// Used for debugging purposes 
			Iterator<Integer> iter = array_list.iterator();
			while(iter.hasNext()) 
				Log.i("Small ArrayList Bi-Directional", iter.next().toString());
		}
	}

	public native static void sortSmallNative(ArrayList<Integer> array_list);
}

