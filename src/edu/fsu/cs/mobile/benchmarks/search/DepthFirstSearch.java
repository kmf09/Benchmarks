//http://www.brilliantsheep.com/depth-first-search-implementation-for-binary-trees/
package edu.fsu.cs.mobile.benchmarks.search;
import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "DepthFirstSearch";
	private static final int SMALL_SIZE = 10000; 
	private static final int LARGE_SIZE = 60000;
	
	public static boolean search( BinaryTree.Node node, int value ) {
		// Check if it's an empty tree.
		if( node == null )
			return false;

		Stack<BinaryTree.Node> stack = new Stack<BinaryTree.Node>( );
		stack.push( node );

		while( ! stack.isEmpty( ) ) {
			BinaryTree.Node currentNode = stack.pop( );
			if( currentNode.value == value ) 
				// Found the value!
				return true;

			if( currentNode.right != null )
				stack.push( currentNode.right );

			// As we want to visit left child first, we must push this node last
			if( currentNode.left != null )
				stack.push( currentNode.left );
		}

		// Not found.
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static void searchLarge(int[] array, BinaryTree BTree) {
		System.out.println( "Is 8 in the tree? " + 
				( DepthFirstSearch.search( BTree.root, 8 )  ? "Yes!" : "No." ) );

		System.out.println( "Is 42 in the tree? " + 
				( DepthFirstSearch.search( BTree.root, 42 )  ? "Yes!" : "No." ) );
	}

	public native static void searchLargeNative(int[] array, BinaryTree BTree);

	@SuppressWarnings("rawtypes")
	public static void searchSmall(int[] array, BinaryTree BTree) {
		System.out.println( "Is 8 in the tree? " + 
				( DepthFirstSearch.search( BTree.root, 8 )  ? "Yes!" : "No." ) );

		System.out.println( "Is 42 in the tree? " + 
				( DepthFirstSearch.search( BTree.root, 42 )  ? "Yes!" : "No." ) );
	}

	public native static void searchSmallNative(int[] array, BinaryTree BTree);
}

