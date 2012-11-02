/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import edu.fsu.cs.mobile.benchmarks.tasks.BenchmarkTask;
import edu.fsu.cs.mobile.benchmarks.tasks.BubbleSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.BucketSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.CountingSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.DepthFirstSearchTask;
import edu.fsu.cs.mobile.benchmarks.tasks.DijkstraTask;
import edu.fsu.cs.mobile.benchmarks.tasks.EuclidTask;
import edu.fsu.cs.mobile.benchmarks.tasks.FibonacciTask;
import edu.fsu.cs.mobile.benchmarks.tasks.HeapSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.HuffmanTask;
import edu.fsu.cs.mobile.benchmarks.tasks.InsertionSortTask;
import edu.fsu.cs.mobile.benchmarks.tasks.KMPTask;
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
		QSORT, MMULT, NFIB, EUCLID, SUBSUM, SUBSEQ, SUBSTR, KMP, HUFFCODE, DIJKSTRA, BUBBLESORT, HEAPSORT, MERGESORT, INSERTIONSORT, COUNTINGSORT, RADIXSORT, BUCKETSORT, DEPTHFIRSTSEARCH, PRIMS
	}

	public static enum BenchSize {
		SMALL, LARGE
	}

	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "BenchmarkLauncher";
	private static final String BENCH_KEY = "bench";
	private static final String SIZE_KEY = "size";
	private static final String LARGE = "large";

	private static Map<String, Benchmark> BENCHMARKS;

	private BenchmarkTask mTask;

	protected BenchSize mSize = BenchSize.SMALL;

	protected Benchmark mBench = Benchmark.DEPTHFIRSTSEARCH;

	protected boolean mNative = false;;

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
		BENCHMARKS.put("prims", Benchmark.PRIMS);
		BENCHMARKS = Collections.unmodifiableMap(BENCHMARKS);
	}

	public void done() {
		synchronized (this) {
			notifyAll();
		}
	}

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
		case PRIMS:
			task = new PrimTask();
			break;
		default:
			Log.i(PKG, TAG + ": Unknown benchmark requested.");
		}
		return task;
	}

	public BenchmarkTask getTask() {
		return mTask;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Benchmark bench;
		String benchParam;

		benchParam = getIntent().getStringExtra(BENCH_KEY);
		if (benchParam != null && (bench = BENCHMARKS.get(benchParam)) != null) {
			mBench = bench;
		}

		benchParam = getIntent().getStringExtra(SIZE_KEY);
		if (benchParam != null) {
			mSize = benchParam.equals(LARGE) ? BenchSize.LARGE
					: BenchSize.SMALL;
		}

		if (getIntent().getStringExtra("native") != null) {
			mNative = true;
		} 
	} 

	@Override
	protected void onStart() {
		super.onStart(); 
		setTask(getBenchTask(mBench));
		getTask().execute(this, mSize, mNative);
	}

	public void setTask(BenchmarkTask mTask) {
		this.mTask = mTask;
	}
}