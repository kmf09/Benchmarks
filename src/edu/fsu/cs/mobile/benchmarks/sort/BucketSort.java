//from http://mathbits.com/MathBits/Java/arrays/Bubble.htm
package edu.fsu.cs.mobile.benchmarks.sort;
import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

public class BucketSort {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "BucketSort";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;

	// this is the sorting algorithm
	private static ArrayList<Double> sort(ArrayList<Double> V) {
		ArrayList< ArrayList<Double> > B = new ArrayList<ArrayList<Double>>();
		B.add(new ArrayList<Double>());
		ArrayList<Double> D = new ArrayList<Double>(); 
		int n = V.size(); 

		// this pushes back n empty lists
		for (int i = 0; i < n; i++)
			B.add(D);

		for (int i = 0; i < n; i++)
			B.get((int)(n*V.get(i))).add(V.get(i)); 
                                               
		for (int i = 0; i < n; i++) 
			B.set(i, insertion(B.get(i))); 

		V.clear(); 
		for (int i = 0; i < B.size(); i++) { 
			for (int j = 0; j < B.get(i).size(); j++)
				V.add(B.get(i).get(j));
		}

		return V;
	}

	public static ArrayList<Double> insertion(ArrayList<Double> V) {
		double key; int i, j; 
		for (j = 1; j < V.size(); j++) {
			key = V.get(j); 
			i = j - 1; 
			while ((i >= 0) && (V.get(i) > key)) {
				V.set(i+1, V.get(i));				
				i -= 1; 
			}
			V.set(i+1, key);
		}
		return V;
	}

	@SuppressWarnings("rawtypes")
	public static void sortLarge(ArrayList<Double> array_list) {
		sort(array_list);
		// Used for debugging purposes
		Iterator iter = array_list.iterator();
		while(iter.hasNext()) 
			Log.i("Large ArrayList Bucket", iter.next().toString());
	}

	public native static void sortLargeNative(ArrayList<Double> array_list);

	@SuppressWarnings("rawtypes")
	public static void sortSmall(ArrayList<Double> array_list) {
		sort(array_list);
		// Used for debugging purposes
		Iterator iter2 = array_list.iterator();
		while(iter2.hasNext()) 
			Log.i("Small ArrayList Bucket", iter2.next().toString());
	}

	public native static void sortSmallNative(ArrayList<Double> array_list);
}

