// Katrina Fishman
// http://www.aduni.org/courses/algorithms/courseware/handouts/Reciation_04.html
package edu.fsu.cs.mobile.benchmarks.search;

import java.util.ArrayDeque;
import java.util.ArrayList;

import android.util.Log;

public class BreadthFirstSearch {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "BreadthFirstSearch";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;
	
	static ArrayList<Integer> mresult; 

	public static boolean search( BinaryTree.Node node, int value ) {
		BinaryTree.Node x; 
		ArrayDeque<BinaryTree.Node> q = new ArrayDeque<BinaryTree.Node>(); 
		q.offer(node);
		while (!q.isEmpty()) {
			x = q.remove();
			mresult.add(x.value);
			if (x.left != null) 
				q.offer(x.left);
			if (x.right != null) 
				q.offer(x.right);
		}
		
		return true; 
	}

	public static void searchLarge(int[] array, BinaryTree BTree) {
		mresult = new ArrayList<Integer>();
		search(BTree.root, 8);
		/* For debugging purposes */
		for (int i = 0; i < mresult.size(); i++)
			Log.i("Result", ""+mresult.get(i));
	}

	public native static void searchLargeNative(int[] array, BinaryTree BTree);

	public static void searchSmall(int[] array, BinaryTree BTree) {
		mresult = new ArrayList<Integer>();
		search(BTree.root, 8);
		/* For debugging purposes */
		for (int i = 0; i < mresult.size(); i++)
			Log.i("Result", ""+mresult.get(i));
	}

	public native static void searchSmallNative(int[] array, BinaryTree BTree);
}

