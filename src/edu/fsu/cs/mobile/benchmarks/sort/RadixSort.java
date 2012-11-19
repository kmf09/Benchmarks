// Created by Katrina Fishman
package edu.fsu.cs.mobile.benchmarks.sort;
import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

public class RadixSort {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "RadixSort";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;

	// this is the sorting algorithm
	private static ArrayList<Integer> sort(ArrayList<Integer> V) {
			int num = 1, numbers_in_max = 0, max = 0, hold; 
		    ArrayList<ArrayList<Integer>> storage = new ArrayList<ArrayList<Integer>>();  
		    
		    for (int i = 0; i < 10; i++) 
		    	storage.add(new ArrayList<Integer>());

		    for (int i = 0; i < V.size(); i++) {
		        if (max < V.get(i))
		            max = V.get(i);
		    }

		    while (max > 0) {
		        numbers_in_max++;
		        max /= 10;
		    }
		    for (int k = 0; k < numbers_in_max; ++k) {
		        for (int j = 0; j < 10; j++) 
		        	storage.get(j).clear(); 
		        	
		        // go through digits
		        for (int i = 0; i < V.size(); i++) {
		        	hold = (V.get(i)/num)%10;
		        	storage.get(hold).add(V.get(i));
		        }
		
		        V.clear();
		        for (int i = 0; i < 10; i++) {
		        	for (int j = 0; j < storage.get(i).size(); j++)
		        		V.add(storage.get(i).get(j));
		        }
		        num *= 10;
		    }

		    return V;
		}


	@SuppressWarnings("rawtypes")
	public static void sortLarge(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		Iterator iter = array_list.iterator();
		while(iter.hasNext()) 
			Log.i("Large ArrayList Radix", iter.next().toString());
	}

	public native static void sortLargeNative(ArrayList<Integer> array_list);

	@SuppressWarnings("rawtypes")
	public static void sortSmall(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		Iterator iter2 = array_list.iterator();
		while(iter2.hasNext()) 
			Log.i("Small ArrayList Radix", iter2.next().toString());
	}

	public native static void sortSmallNative(ArrayList<Integer> array_list);
}

