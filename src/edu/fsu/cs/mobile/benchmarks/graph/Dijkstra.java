/*
 * Author: Ira Ray Jenkins
 * Original: http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 */
package edu.fsu.cs.mobile.benchmarks.graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

import android.util.Log;

public class Dijkstra {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static int distances[];

	private static final Comparator<? super Integer> distCompare = new Comparator<Integer>() {
		public int compare(Integer arg0, Integer arg1) {
			assert (distances != null);
			return distances[arg0] - distances[arg1];
		}
	};

	static {
		System.loadLibrary("native_bench");
	}

	static boolean contains(int[] S, int size, int item) {
		int i;
		for (i = 0; i < size; i++) {
			if (S[i] == item)
				return true;
		}
		return false;
	}

	static int poll(Integer[] Q, int size) {
		int result = Q[0];

		Q[0] = Q[size - 1];
		Q[size - 1] = -1;

		Arrays.sort(Q, 0, size - 1, distCompare);

		return result;
	}

	private static int[] search(int[][] G, int source) {
		int u, alt, qcount = G.length, scount = 0;
		distances = new int[G.length];
		int[] predecessors = new int[G.length];
		Integer[] Q = new Integer[G.length];
		int[] S = new int[G.length];

		for (int i = 0; i < G.length; i++) {
			distances[i] = Integer.MAX_VALUE;
			Q[i] = i;
			predecessors[i] = S[i] = -1;
		}

		distances[source] = 0;

		Arrays.sort(Q, distCompare);

		while (qcount > 0) {
			u = poll(Q, qcount--);
			if (distances[u] == Integer.MAX_VALUE)
				break;
			S[scount++] = u;
			for (int i = 0; i < G.length; i++) {
				if (contains(S, scount, i) || G[u][i] < 1)
					continue;

				alt = distances[u] + G[u][i];

				if (alt < Math.abs(distances[i])) {
					distances[i] = alt;
					predecessors[i] = u;

					Arrays.sort(Q, 0, qcount, distCompare);
				}
			}
		}

		return predecessors;
	}

	public static void search(String fileName) {
		BufferedReader input = null;
		String line = null;
		int size = 0;
		int[][] G = null;
		int[] result = null;
		String[] split;

		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));

			size = Integer.parseInt(input.readLine());
			G = new int[size][size];
			result = new int[size];

			while ((line = input.readLine()) != null) {
				split = line.split(" ");
				if (split.length != 3)
					throw new Exception();

				G[Integer.parseInt(split[0])][Integer.parseInt(split[1])] = Integer
						.parseInt(split[2]);
			}
		} catch (Exception e) {
			Log.e(PKG, "Error reading input file.");
			e.printStackTrace();
			return;
		}

		result = search(G, 0);

		for (int i = 0; i < size; i++)
			Log.i(PKG,
					"Predecessor: [" + String.valueOf(i) + "]: "
							+ String.valueOf(result[i]));

	}

	public static native void searchNative(String fileName);

	// private static int[] search(int[][] G, int source) {
	// distances = new int[G.length];
	// int[] predecessors = new int[G.length];
	// PriorityQueue<Integer> Q = new PriorityQueue<Integer>(G.length,
	// shortestDistanceComparator);
	// HashSet<Integer> S = new HashSet<Integer>();
	//
	// for (int i = 0; i < G.length; i++) {
	// distances[i] = Integer.MAX_VALUE;
	// predecessors[i] = -1;
	// }
	//
	// distances[source] = 0;
	//
	// for (int i = 0; i < G.length; i++)
	// Q.add(i);
	// int u;
	// int alt;
	//
	// while (!Q.isEmpty()) {
	// u = Q.poll();
	// if (distances[u] == Integer.MAX_VALUE)
	// break;
	// S.add(u);
	// for (int i = 0; i < G.length; i++) {
	// if (S.contains(i) || G[u][i] < 1)
	// continue;
	//
	// alt = distances[u] + G[u][i];
	//
	// if (alt < Math.abs(distances[i])) {
	// distances[i] = alt;
	// predecessors[i] = u;
	// Q.remove(i);
	// Q.offer(i);
	// }
	// }
	// }
	//
	// return predecessors;
	// }
}
