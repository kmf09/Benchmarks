package edu.fsu.cs.mobile.benchmarks.math;
import java.util.ArrayList;

import android.util.Log;

public class ClosestPairNaive {  	
	
	public static void ClosestPair(ArrayList<Node> AL) {
		double minDist = Double.MAX_VALUE;
		Node point1 = null, point2 = null;
		Node p = new Node();
		Node q = new Node(); 
		 
		for (int i = 0; i < AL.size(); i++) {
			for (int j = i+1; j < AL.size(); j++) {
				p = AL.get(i);
				q = AL.get(j);
				if (dist(p, q) < minDist) {
					minDist = dist(p,q);
					point1 = p; 
					point2 = q; 
				}
			}
		}
		Log.i("closest pair: ", point1.x + " " + point1.y);
		Log.i("closest pair: ", point2.x + " " + point2.y);
	}

	public static double dist(Node x, Node y) {
		int a = (x.x - y.x) * (x.x - y.x);
		int b = (x.y - y.y) * (x.y - y.y);
		return Math.sqrt(a+b); 
	}

	public static void sortLarge(ArrayList<Node> al) {
		ClosestPair(al);
	}

	public native static void sortLargeNative(ArrayList<Node> al);

	public static void sortSmall(ArrayList<Node> al) {
		ClosestPair(al);
	}

	public native static void sortSmallNative(ArrayList<Node> al);
}
