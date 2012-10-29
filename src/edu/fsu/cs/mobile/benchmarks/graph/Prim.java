package edu.fsu.cs.mobile.benchmarks.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import android.util.Log;
//from http://mathbits.com/MathBits/Java/arrays/Bubble.htm
public class Prim {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "BubbleSort";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;

	static class Katniss {
		static int [][] G;
		static int [][] t;
		static int [] near;
		static int n;
		static int mincost = 0;
		static int k, l;

		static void prims() {
			getMinKL();
			mincost = G[k][l];
			t[1][1] = l;
			t[1][2] = k;
			for(int i=1; i<=n; i++)
				near[i] = (G[i][l]<G[i][k])?l:k;
			near[k] = near[l] = 0;
			for(int i=2; i<n; i++) {
				int j = getMin();
				t[i][1] = j; t[i][2] = near[j];
				mincost = mincost+G[j][near[j]];
				near[j] =0;
				for (int k = 1; k <= n; k++)
					if( (near[k] != 0) && G[k][ near[k] ]> G[k][j] )
						near[k] = j;
			}
		}
		static int getMin() {
			int j=1;
			for(int i=1;i<=n;i++)
				if(near[i] !=0) {
					j = i;
					break;
				}
			for(int i=1;i<=n;i++)
				if(near[i] !=0)
					if(G[j][near[j]]> G[i][near[i]])
						j =i;
			return j;
		}
		static void getMinKL() {
			int k1 = 1, l1 = 2;
			for(int i = 1;i <= n;i++)
				for(int j = 1; j <= n;j++) {
					if((i!=j)&&(i<j)) {
						if((G[i][j] < G[k1][l1]) && G[i][j] !=0 ) {
					 		k1 = i;
							l1 = j;
						}
					}
				}
			if(G[k1][l1] !=0 ) {
				k =k1; l=l1;
			}
		}
	}


	@SuppressWarnings("rawtypes")
	public static void sortLarge(int[][] G, int[][] t, int[] near) {
		Katniss.n = G.length;
		Katniss.G = G;
		Katniss.t = t;
		Katniss.near = near;
		Katniss.prims();
		Log.i("Prim MST Weight", ""+Katniss.mincost);
	}

	public native static void sortLargeNative(int[][] G, int[][] t, int[] near);

	@SuppressWarnings("rawtypes")
	public static void sortSmall(int[][] G, int[][] t, int[] near) {
		Katniss.n = G.length;
		Katniss.G = G;
		Katniss.t = t;
		Katniss.near = near;
		Katniss.prims();
		Log.i("Prim MST Weight", ""+Katniss.mincost);
	}

	public native static void sortSmallNative(int[][] G, int[][] t, int[] near);
}

