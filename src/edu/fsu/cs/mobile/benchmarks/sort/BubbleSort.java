package edu.fsu.cs.mobile.benchmarks.sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.util.Log;
//from http://mathbits.com/MathBits/Java/arrays/Bubble.htm
public class BubbleSort {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "BubbleSort";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;

	// this is the sorting algorithm
	@SuppressWarnings("rawtypes")
	private static ArrayList<Integer> sort(ArrayList<Integer> v) {
		boolean flag = true; 
		int j, temp;

		while ( flag ) {
			flag = false; //set flag to false, may swap
			for( j = 0; j < v.size() - 1;  j++ ) {
				if ( v.get(j) > v.get(j+1) ) {
					temp = v.get(j);                
					v.set(j, v.get(j+1));
					v.set(j+1, temp);
					flag = true;                
				} 
			} 
		}
		
		return v; 
	}

	@SuppressWarnings("rawtypes")
	public static void sortLarge(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		/*Iterator iter = array_list.iterator();
		while(iter.hasNext()) 
			Log.i("Large ArrayList Bubble", iter.next().toString());*/
	}

	public native static void sortLargeNative(ArrayList<Integer> array_list);

	@SuppressWarnings("rawtypes")
	public static void sortSmall(ArrayList<Integer> array_list) {
		sort(array_list);
		// Used for debugging purposes
		/*Iterator iter2 = array_list.iterator();
		while(iter2.hasNext()) 
			Log.i("Small ArrayList Bubble", iter2.next().toString());*/
	}

	public native static void sortSmallNative(ArrayList<Integer> array_list);
}

