package edu.fsu.cs.mobile.benchmarks.sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.util.Log;
// sort created by Katrina Fishman
public class HeapSort {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "HeapSort";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;

	// gloabal variable
	static int mheap_size; 

	// this is the sorting algorithm
	public static ArrayList<Integer> sort(ArrayList<Integer> V) {
		int i, temp; 
		V = build_min_heap(V);
		for (i = V.size()-1; i >= 1; i--) {
			temp = V.get(0); 
			V.set(0, V.get(i));
			V.set(i, temp);
			mheap_size -= 1; 
			V = min_heapify(V, 0); 
		}

		return V; 
	}

	public static ArrayList<Integer> min_heapify(ArrayList<Integer> V, int i) {
		int largest, temp; 
		// to get the left child
		int l = (2*i) + 1; 
		// to get the right child
		int r = (2*i) + 2;

		if ((l < mheap_size) && (V.get(l) > V.get(i)))
			largest = l; 
		else 
			largest = i; 
		if ((r < mheap_size) && (V.get(r) > V.get(largest)))
			largest = r; 
		if (largest != i) {
			temp = V.get(i);
			V.set(i, V.get(largest));
			V.set(largest, temp);
			V = min_heapify(V, largest); 
		}

		return V; 
	}

	public static ArrayList<Integer> build_min_heap(ArrayList<Integer> V) {
		int i; 
		mheap_size = V.size(); 

		for (i = (int)(Math.floor((V.size()/2))); i >= 0; i--)
			V =  min_heapify(V, i); 

		return V; 
	}

	@SuppressWarnings("rawtypes")
	public static void sortLarge(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		/*Iterator iter = array_list.iterator();
		while(iter.hasNext()) 
			Log.i("Large ArrayList Heap", iter.next().toString());*/
	}

	public native static void sortLargeNative(ArrayList<Integer> array_list);

	@SuppressWarnings("rawtypes")
	public static void sortSmall(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		/*Iterator iter2 = array_list.iterator();
		while(iter2.hasNext()) 
			Log.i("Small ArrayList Heap", iter2.next().toString());*/
	}

	public native static void sortSmallNative(ArrayList<Integer> array_list);
}