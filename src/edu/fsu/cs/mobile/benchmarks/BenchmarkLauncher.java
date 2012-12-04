/*
 * Author: Katrina Fishman and Ira Jenkins
 */
package edu.fsu.cs.mobile.benchmarks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import edu.fsu.cs.mobile.benchmarks.tasks.BenchmarkTask;
import edu.fsu.cs.mobile.benchmarks.tasks.BiDirectionalTask;
import edu.fsu.cs.mobile.benchmarks.tasks.BitonicTask;
import edu.fsu.cs.mobile.benchmarks.tasks.BreadthFirstSearchTask;
import edu.fsu.cs.mobile.benchmarks.tasks.BubbleSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.BucketSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.ClosestPairNaiveTask;
import edu.fsu.cs.mobile.benchmarks.tasks.CountingSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.DepthFirstSearchTask;
import edu.fsu.cs.mobile.benchmarks.tasks.DijkstraTask;
import edu.fsu.cs.mobile.benchmarks.tasks.EuclidTask;
import edu.fsu.cs.mobile.benchmarks.tasks.FFTInPlaceTask;
import edu.fsu.cs.mobile.benchmarks.tasks.FFTNaiveTask;
import edu.fsu.cs.mobile.benchmarks.tasks.FibonacciTask;
import edu.fsu.cs.mobile.benchmarks.tasks.HeapSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.HuffmanTask;
import edu.fsu.cs.mobile.benchmarks.tasks.InsertionSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.KMPTask;
import edu.fsu.cs.mobile.benchmarks.tasks.LCSTask;
import edu.fsu.cs.mobile.benchmarks.tasks.MatrixMultTask;
import edu.fsu.cs.mobile.benchmarks.tasks.MergeSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.PrimTask;
import edu.fsu.cs.mobile.benchmarks.tasks.QuickSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.RadixSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.SubSeqTask;
import edu.fsu.cs.mobile.benchmarks.tasks.SubStrTask;
import edu.fsu.cs.mobile.benchmarks.tasks.SubsetSumTask;

public final class BenchmarkLauncher extends Activity {
	public static enum Benchmark {
		BIDIRECTIONAL, BITONICSORT, BREADTHFIRSTSEARCH, BUBBLESORT, BUCKETSORT, 
		COUNTINGSORT, CLOSESTPAIRNAIVE,
		DEPTHFIRSTSEARCH, DIJKSTRA,
		EUCLID,
		FFTInPlace, FFTNaive,
		HEAPSORT, HUFFCODE,
		INSERTIONSORT,
		KMP,
		LCS, 
		MERGESORT, MMULT,
		NFIB,
		PRIM,
		QSORT,
		RADIXSORT,
		SUBSEQ, SUBSTR, SUBSUM,    
	}

	public static enum BenchSize { SMALL, LARGE }
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "BenchmarkLauncher";
	private static final String BENCH_KEY = "bench";
	private static final String SIZE_KEY = "size";
	private static final String LARGE = "large";
	private static Map<String, Benchmark> BENCHMARKS; 
	private BenchmarkTask mTask;
	protected boolean mNative = false;
	
	// CHANGE BENCHMAKRS HERE
	protected BenchSize mSize = BenchSize.SMALL;
	protected Benchmark mBench = Benchmark.BIDIRECTIONAL;

	static {
		BENCHMARKS = new HashMap<String, Benchmark>();
		BENCHMARKS.put("qsort", Benchmark.QSORT);
		BENCHMARKS.put("mmult", Benchmark.MMULT);
		BENCHMARKS.put("nfib", Benchmark.NFIB);
		BENCHMARKS.put("euclid", Benchmark.EUCLID);
		BENCHMARKS.put("subsum", Benchmark.SUBSUM);
		BENCHMARKS.put("subseq", Benchmark.SUBSEQ);
		BENCHMARKS.put("substr", Benchmark.SUBSTR);
		BENCHMARKS.put("kmp", Benchmark.KMP);
		BENCHMARKS.put("huffcode", Benchmark.HUFFCODE);
		BENCHMARKS.put("dijkstra", Benchmark.DIJKSTRA);
		BENCHMARKS.put("bubblesort", Benchmark.BUBBLESORT);
		BENCHMARKS.put("heapsort", Benchmark.HEAPSORT);
		BENCHMARKS.put("mergesort", Benchmark.MERGESORT);
		BENCHMARKS.put("insertionsort", Benchmark.INSERTIONSORT);
		BENCHMARKS.put("countingsort", Benchmark.COUNTINGSORT);
		BENCHMARKS.put("radixsort", Benchmark.RADIXSORT);
		BENCHMARKS.put("bucketsort", Benchmark.BUCKETSORT);
		BENCHMARKS.put("depthfirstsearch", Benchmark.DEPTHFIRSTSEARCH);
		BENCHMARKS.put("breathfirstsearch", Benchmark.BREADTHFIRSTSEARCH);
		BENCHMARKS.put("prims", Benchmark.PRIM);
		BENCHMARKS.put("topologicalsort", Benchmark.FFTNaive);
		BENCHMARKS.put("topologicalsort", Benchmark.FFTInPlace);
		BENCHMARKS.put("longestcommonsubsequence", Benchmark.LCS);
		BENCHMARKS.put("closestpairnaive", Benchmark.CLOSESTPAIRNAIVE);
		BENCHMARKS.put("bitonic", Benchmark.BITONICSORT);
		BENCHMARKS.put("bidirectional", Benchmark.BIDIRECTIONAL);
		BENCHMARKS = Collections.unmodifiableMap(BENCHMARKS);
	}

	public void done() { synchronized (this) { notifyAll(); } }

	private BenchmarkTask getBenchTask(Benchmark bench) {
		BenchmarkTask task = null;

		switch (bench) {
		case QSORT:
		    task = new QuickSortTask();
			break;
		case MMULT:
			task = new MatrixMultTask();
			break;
		case NFIB:
			task = new FibonacciTask();
			break;
		case EUCLID:
			task = new EuclidTask();
			break;
		case SUBSUM:
			task = new SubsetSumTask();
			break;
		case SUBSEQ:
			task = new SubSeqTask();
			break;
		case SUBSTR:
			task = new SubStrTask();
			break;
		case KMP:
			task = new KMPTask();
			break;
		case HUFFCODE:
			task = new HuffmanTask();
			break;
		case DIJKSTRA:
			task = new DijkstraTask();
			break;
		case BUBBLESORT: 
			task = new BubbleSortTask(); 
			break; 
		case HEAPSORT: 
			task = new HeapSortTask(); 
			break; 
		case MERGESORT: 
			task = new MergeSortTask();
			break; 
		case INSERTIONSORT: 
			task = new InsertionSortTask(); 
			break;
		case COUNTINGSORT: 
			task = new CountingSortTask(); 
			break;
		case RADIXSORT: 
			task = new RadixSortTask(); 
			break;
		case BUCKETSORT: 
			task = new BucketSortTask(); 
			break;
		case DEPTHFIRSTSEARCH: 
			task = new DepthFirstSearchTask(); 
			break;
		case BREADTHFIRSTSEARCH: 
			task = new BreadthFirstSearchTask(); 
			break;
		case PRIM:
			task = new PrimTask();
			break;
		case FFTNaive:
			task = new FFTNaiveTask();
			break;
		case FFTInPlace:
			task = new FFTInPlaceTask();
			break;
		case LCS:
			task = new LCSTask();
			break;
		case CLOSESTPAIRNAIVE:
			task = new ClosestPairNaiveTask();
			break;
		case BITONICSORT:
			task = new BitonicTask();
			break;
		case BIDIRECTIONAL:
			task = new BiDirectionalTask();
			break;
		default:
			Log.i(PKG, TAG + ": Unknown benchmark requested.");
		}
		return task;
	}

	public BenchmarkTask getTask() { return mTask; }

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Benchmark bench;
		String benchParam;

		benchParam = getIntent().getStringExtra(BENCH_KEY);
		if (benchParam != null && (bench = BENCHMARKS.get(benchParam)) != null) 
			{ mBench = bench; }

		benchParam = getIntent().getStringExtra(SIZE_KEY);
		if (benchParam != null) {
			mSize = benchParam.equals(LARGE) ? BenchSize.LARGE
					: BenchSize.SMALL;
		}

		if (getIntent().getStringExtra("native") != null) 
			{ mNative = true; } 
	} 

	@Override protected void onStart() {
		super.onStart(); 
		setTask(getBenchTask(mBench));
		getTask().execute(this, mSize, mNative);
	}

	public void setTask(BenchmarkTask mTask) { this.mTask = mTask; }
}