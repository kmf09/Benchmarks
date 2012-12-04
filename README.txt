Benchmarks implemented by Katrina Fishman and Ira Jenkins. 

To give which benchmark you want to run the BenchmarkLauncher
code must be edited manually. This the code that must be changed:
protected Benchmark mBench = Benchmark.ENTER_ALGORITHM_HERE;
The algorithm that is entered must match the enumeration for that
algorithm. For example, to run bubble sort use the BUBBLESORT
enumeration.  

There are only two sizes that may be test LARGE and SMALL. 
To change from large to small and vice versa the BenchmarkLauncher
code must be edited manually. This is the code that must be changed:
protected BenchSize mSize = BenchSize.SMALL_OR_LARGE;
The size must be either the exact words LARGE or SMALL. The exact
size for LARGE and SMALL varies based on the algorithm used. For 
example, the sizes for bi-directional bubble sort must be a power 
of two while the sizes for the bubble sort can be any integer. 

If you want to change the size of LARGE and SMALL you must manually
go into the algorithm code. Usually this can be found in the 
edu.fsu.cs.mobile.benchmarks.tasks package that corresponds to the
algorithm you wish to change. The sizes are found at the top of the 
file and may be changed to any positive integer. For example, 
in HeapSortTask the following code may be seen: 

private static final int SMALL_SIZE = 10000;
private static final int LARGE_SIZE = 60000;

The value for SMALL_SIZE and LARGE_SIZE may be changed.

For all of these algorithms, any of the previous information may 
be changed and the algorithm may run successfully.

/******************************************************************/

If you wish to see more or any output in the algorithm this must also be 
done manually. Edit the corresponding algorithm that is NOT located
in the edu.fsu.cs.mobile.benchmakrs.tasks package. This is the code 
to look for:  

public static void sortLarge(ArrayList<Integer> array_list) {
		INPUT Log.i() STATEMENTS HERE TO VIEW OUTPUT
	}

	public static void sortSmall(ArrayList<Integer> array_list) {
		INPUT Log.i() STATEMENTS HERE TO VIEW OUTPUT
	}
}

This may be viewed easily when transported and used in the Eclipse 
environment.

/******************************************************************/

If you wish to add more algorithms follow the pattern as seen in 
the previously written code. In the BenchmarkLauncher.java, enter 
a new enumeration with the name of the algorithm, NEW_ALG. Then, 
in the static line where there are a bunch of BENCHMARKS.put() 
statements put in your new benchmark. 
BENCHMARKS.put("ALG_NAME", Benchmark.NEW_ALG);

In the switch statement, follow the pattern and add to the 
previously implemented benchmarks. 
case NEW_ALG:
			task = new NEW_ALG_Task();
			break;
			
Place the NEW_ALG_TASK in the edu.fsu.cs.mobile.benchmarks.sort
package. When creating this, keep in mind that if you are 
generating integers, strings, a graph, etc. that does not have
to do with the algorithm itself, the task is the place to put this. 
Pass into the following functions the parameters that your benchmark
needs to use: 

YOUR_ALG_NAME.sortSmallNative(INSERT_PARAMETERS);
YOUR_ALG_NAME.sortLargeNative(INSERT_PARAMETERS);
YOUR_ALG_NAME.sortSmall(INSERT_PARAMETERS);
YOUR_ALG_NAME.sortLarge(INSERT_PARAMETERS);

The algorithm itself may be put into any of the packages, besides the tasks 
package and edu.fsu.cs.mobile.benchmarks, in the Benchmark application. 
You may also create your own package. 

Your algorithm must include the functions: 
YOUR_ALG_NAME.sortSmallNative(INSERT_PARAMETERS) {}
YOUR_ALG_NAME.sortLargeNative(INSERT_PARAMETERS) {}
YOUR_ALG_NAME.sortSmall(INSERT_PARAMETERS) {}
YOUR_ALG_NAME.sortLarge(INSERT_PARAMETERS) {} 

Also, the BenchmarkRun must be changed as well: 
	public void NEW_ALGORITHM_Large() throws InterruptedException {
		Intent mIntent = new Intent();
		mIntent.putExtra("bench", "NEW_ALGORITHM_NAME_LOWERCASED");
		mIntent.putExtra("size", "large");
		setActivityIntent(mIntent);
		BenchmarkLauncher benchmark = getActivity();
		synchronized (benchmark) { benchmark.wait(); }
	}

	public void NEW_ALGORITHM_LargeNative() throws InterruptedException {
		Intent mIntent = new Intent();
		mIntent.putExtra("bench", "NEW_ALGORITHM_NAME_LOWERCASED");
		mIntent.putExtra("size", "large");
		mIntent.putExtra("native", "");
		setActivityIntent(mIntent);
		BenchmarkLauncher benchmark = getActivity();
		synchronized (benchmark) { benchmark.wait(); }
	}

	public void NEW_ALGORITHM_Small() throws InterruptedException {
		Intent mIntent = new Intent();
		mIntent.putExtra("bench", "NEW_ALGORITHM_NAME_LOWERCASED");
		mIntent.putExtra("size", "small");
		setActivityIntent(mIntent);
		BenchmarkLauncher benchmark = getActivity(); 
		synchronized (benchmark) { benchmark.wait(); }
	}

	public void NEW_ALGORITHM_SmallNative() throws InterruptedException {
		Intent mIntent = new Intent();
		mIntent.putExtra("bench", "NEW_ALGORITHM_NAME_LOWERCASED");
		mIntent.putExtra("size", "small");
		mIntent.putExtra("native", "");
		setActivityIntent(mIntent);
		BenchmarkLauncher benchmark = getActivity();
		synchronized (benchmark) { benchmark.wait(); }
	}



