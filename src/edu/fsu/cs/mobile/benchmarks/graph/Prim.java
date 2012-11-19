// Katrina Fishman
// http://cs.fit.edu/~ryan/java/programs/graph/Prim-java.html
// To read output: 
// Example:
// S: 0
// 1
// 2
// 3
// S is the starting vertex
// A[index] = 1
// A[index] = 2
// A[index] = 3
// The edge in the minimum spanning tree is the edge between 
// the first vertex, the index, and the second vertex, the number (1,2,3)
package edu.fsu.cs.mobile.benchmarks.graph;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

public class Prim {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "Prim";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;
	public static final int seed = 0;

	public static int[] prim (UndirectedGraph G, int s) {
		final int[] dist = new int [G.size()];  // shortest known distance to MST
		final int[] pred = new int [G.size()];  // preceeding node in tree
		final boolean[] visited = new boolean [G.size()]; // all false initially

		for (int i = 0; i < dist.length; i++) 
			dist[i] = Integer.MAX_VALUE;
		
		dist[s] = 0;

		for (int i = 0; i < dist.length; i++) {
			final int next = minVertex (dist, visited);
			visited[next] = true;

			// The edge from pred[next] to next is in the MST (if next!=s)
			ArrayList<Integer> n = new ArrayList<Integer>(); 
			n = G.neighbors(next);
			
			for (int j = 0; j < n.size(); j++) {
				final int v = n.get(j);
				final int d = G.getWeight(next,v);
				if (dist[v] > d && !visited[v]) {
					dist[v] = d;
					pred[v] = next;
				}
			}
		} 
		Log.i("S: ", ""+s);
		return pred;  // (ignore pred[s]==0!)
	}

	private static int minVertex (int [] dist, boolean [] v) {
		int x = Integer.MAX_VALUE;
		int y = -1;   // graph not connected, or no unvisited vertices
		for (int i = 0; i < dist.length; i++) {
			if (!v[i] && dist[i] < x) {
				y = i; x = dist[i];
			}
		} 
		return y;
	}

	public static void sortLarge(UndirectedGraph g) {
		Random ran = new Random(seed);
		int[] ans = prim(g, ran.nextInt(g.size()));
		for (int i = 0; i < ans.length; i++)
			Log.i("Result ", ""+ans[i]);
	}

	public native static void sortLargeNative(UndirectedGraph g); 

	public static void sortSmall(UndirectedGraph g) {
		Random ran = new Random(seed); 
		int[] ans = prim(g, ran.nextInt(g.size()));
		for (int i = 0; i < ans.length; i++)
			Log.i("Result ", ""+ans[i]);
	}

	public native static void sortSmallNative(UndirectedGraph g);
}

