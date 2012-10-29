/* Copyright (c) 2012 the authors listed at the following URL, and/or
	  2 the authors of referenced articles or incorporated external code:
	  3 http://en.literateprograms.org/Depth-first_search_(Java)?action=history&offset=20080109061750
	  4 
	  5 Permission is hereby granted, free of charge, to any person obtaining
	  6 a copy of this software and associated documentation files (the
	  7 "Software"), to deal in the Software without restriction, including
	  8 without limitation the rights to use, copy, modify, merge, publish,
	  9 distribute, sublicense, and/or sell copies of the Software, and to
	 10 permit persons to whom the Software is furnished to do so, subject to
	 11 the following conditions:
	 12 
	 13 The above copyright notice and this permission notice shall be
	 14 included in all copies or substantial portions of the Software.
	 15 
	 16 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
	 17 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	 18 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
	 19 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
	 20 CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
	 21 TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
	 22 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
	 23 
	 24 Retrieved from: http://en.literateprograms.org/Depth-first_search_(Java)?oldid=11979
	 25 */

package edu.fsu.cs.mobile.benchmarks.search;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import android.util.Log;

public class DepthFirstSearch {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "DepthFirstSearch";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;

	public static class Vertex<T> {
		private final T data;
		private final List<Vertex<T>> _successors = new ArrayList<Vertex<T>>();

		Vertex(T data)               { this.data = data;   }
		T getData()                  { return data;        }
		List<Vertex<T>> successors() { return _successors; }

		public static <T> boolean depthFirstSearch(Vertex<T> start, Vector<Vertex<T>> result) {
			if (result.contains(start))
				return false;

			result.add(start);
			Log.i("VISITING NODE:", ""+start.data);

			for (Vertex<T> v : start.successors()) {
				depthFirstSearch(v, result);
			}

			return false; 
		}

		public static List<Vertex<Integer>> petersenGraph(int[][] edges) {
			List<Vertex<Integer>> v = new ArrayList<Vertex<Integer>>();
			for (int i = 0; i < 1000; i++)
				v.add(new Vertex<Integer>(i));

			for (int[] e : edges)
				v.get(e[0]).successors().add(v.get(e[1]));

			return v;
		}
		
		@Override
		public String toString() {
			return "" + data;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Vector<Vertex<Integer>> sortLarge(ArrayList<Integer> array_list) {
		int[][] edges = new int[array_list.size() / 2][2];
		int i = 0; 
		int count = 0; 
		while (i < array_list.size()) {
			edges[count][0] = array_list.get(i);
			edges[count][1] = array_list.get(i+1);
			count++;
			i += 2; 
		}

		List<Vertex<Integer>> v = Vertex.petersenGraph(edges);
		Vector<Vertex<Integer>> visitedNodes = new Vector<Vertex<Integer>>();
		Vertex.depthFirstSearch(v.get(0), visitedNodes);
		
		Log.i("VISITED NODES", ""+visitedNodes);
		
		return visitedNodes; 
	}

	public native static void sortLargeNative(ArrayList<Integer> array_list);

	@SuppressWarnings("rawtypes")
	public static Vector<Vertex<Integer>> sortSmall(ArrayList<Integer> array_list) {
		int[][] edges = new int[array_list.size() / 2][2];
		int i = 0; 
		int count = 0; 
		while (i < array_list.size()) {
			edges[count][0] = array_list.get(i);
			edges[count][1] = array_list.get(i+1);
			count++;
			i += 2; 
		}

		List<Vertex<Integer>> v = Vertex.petersenGraph(edges);
		Vector<Vertex<Integer>> visitedNodes = new Vector<Vertex<Integer>>();
		Vertex.depthFirstSearch(v.get(0), visitedNodes);
		
		Log.i("VISITED NODES", ""+visitedNodes);
		
		return visitedNodes; 
	}

	public native static void sortSmallNative(ArrayList<Integer> array_list);
}

