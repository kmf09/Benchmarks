package edu.fsu.cs.mobile.benchmarks.sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.util.Log;
//from http://www.vogella.com/articles/JavaAlgorithmsMergesort/article.html
public class MergeSort {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "MergeSort";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;

	// this is the sorting algorithm
	@SuppressWarnings("rawtypes")
	private static ArrayList<Integer> sort(ArrayList<Integer> V) {
		mergeSort(V, 0, V.size() - 1);
		return V;
	}

	private static void mergeSort(ArrayList<Integer> V, int p, int r) {
		if (p < r) {
			int q = ((p + r) / 2); // this is truncated, and therefore the floor is taken
			mergeSort(V, p, q);
			mergeSort(V, q + 1, r);
			mergeMe(V, p, q, r);
		}
	}

	public static void mergeMe(ArrayList<Integer> V, int p, int q, int r) {
		ArrayList<Integer> L = new ArrayList<Integer>();
		ArrayList<Integer> R = new ArrayList<Integer>();
		int n1 = q - p + 1;
		int n2 = r - q;
		int i, j;

		for (i = 0; i < n1; i++)
			L.add(V.get(p+i));
		for (j = 0; j < n2; j++)
			R.add(V.get(q+j+1));
		i = j = 0;
		for (int k = p; k <= r; k++) {
			if (j >= R.size() || (i < L.size() && L.get(i) <= R.get(j))) {
				V.set(k, L.get(i));
				i = i + 1;
			}
			else {
				if (j < R.size()) {
					V.set(k, R.get(j));
					j = j + 1;
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void sortLarge(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		/*Iterator iter = array_list.iterator();
		while(iter.hasNext()) 
			Log.i("Large ArrayList Merge", iter.next().toString());*/
	}

	public native static void sortLargeNative(ArrayList<Integer> array_list);

	@SuppressWarnings("rawtypes")
	public static void sortSmall(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		/*Iterator iter2 = array_list.iterator();
		while(iter2.hasNext()) 
			Log.i("Small ArrayList Merge", iter2.next().toString());*/
	}

	public native static void sortSmallNative(ArrayList<Integer> array_list);
}

