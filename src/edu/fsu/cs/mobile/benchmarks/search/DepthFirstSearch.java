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

	static ArrayList<ArrayList<Integer>> graph;

	public static void depthFirstSearch(int start) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> neighbor = new ArrayList<Integer>();
		Stack<Integer> s = new Stack<Integer>();

		s.push(start);
		result.add(start);
		neighbor = neighbors(start);

		while (s.size() > 0) {
			int currentVertex = s.pop();
			neighbor = neighbors(currentVertex);
			for (int i = 0; i < neighbor.size(); i++) {
				if (!(result.contains(neighbor.get(i)))) { 
					s.push(neighbor.get(i));
					result.add(neighbor.get(i));
				}
			}
		}

		for (int i = 0; i < result.size(); i++)
			System.out.println("result: "+result.get(i));
	}

	public static void addNeighbor(int vertex, int append) {
		graph.get(vertex).add(append);
		graph.get(append).add(vertex);
	}

	public static ArrayList<Integer> neighbors(int vertex) {
		return graph.get(vertex);
	}

	@SuppressWarnings("rawtypes")
	public static void sortLarge(ArrayList<ArrayList<Integer>> array_list) {
		// stable is 1
		depthFirstSearch(1);
	}

	public native static void sortLargeNative(ArrayList<ArrayList<Integer>> array_list);

	@SuppressWarnings("rawtypes")
	public static void sortSmall(ArrayList<ArrayList<Integer>> array_list) {
		// stable is 1
		depthFirstSearch(1); 
	}

	public native static void sortSmallNative(ArrayList<ArrayList<Integer>> array_list);
}

