package edu.fsu.cs.mobile.benchmarks.sort;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;
// Created by Katrina Fishman
public class CountingSort {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "CountingSort";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;

	// this is the sorting algorithm
	@SuppressWarnings("rawtypes")
	public static ArrayList<Integer> sort(ArrayList<Integer> V) {
	    ArrayList<Integer> B = new ArrayList<Integer>();

	    int max = 0;
	    for (int i = 0; i < V.size(); i++) {
	        if (max < V.get(i))
	            max = V.get(i);
	    }
	 
	    return countingSort(V, B, max);
	}
	
	@SuppressWarnings("null")
	public static ArrayList<Integer> countingSort(ArrayList<Integer> V, ArrayList<Integer> B, int max) {
	    ArrayList<Integer> C = new ArrayList<Integer>();

	    // now the array must be as big as the biggest number
	    // this has the potential to eat a lot of memory
	    for (int i = 0; i <= max; i++)  
	        C.add(0); 

	    for (int j = 0; j < V.size(); j++)
	    	C.set(V.get(j), C.get(V.get(j))+1);

	    for (int i = 0; i < C.size(); i++) {
	        for (int j = 0; j < C.get(i); j++)
	        	B.add(i);
	    }
	    return B;
	}
	
	@SuppressWarnings("rawtypes")
	public static void sortLarge(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		Iterator iter = array_list.iterator();
		while(iter.hasNext()) 
			Log.i("Large ArrayList Counting", iter.next().toString());
	}

	public native static void sortLargeNative(ArrayList<Integer> array_list);

	@SuppressWarnings("rawtypes")
	public static void sortSmall(ArrayList<Integer> array_list) {
		array_list = sort(array_list);
		// Used for debugging purposes
		Iterator iter2 = array_list.iterator();
		while(iter2.hasNext()) 
			Log.i("Small ArrayList Counting", iter2.next().toString());
	}

	public native static void sortSmallNative(ArrayList<Integer> array_list);
}

