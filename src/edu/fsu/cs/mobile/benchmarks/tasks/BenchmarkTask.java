/*
 * Author: Ira Ray Jenkins
 */
package edu.fsu.cs.mobile.benchmarks.tasks;

import java.io.IOException;

import android.os.AsyncTask;
import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher;
import edu.fsu.cs.mobile.benchmarks.BenchmarkLauncher.BenchSize;

public abstract class BenchmarkTask extends AsyncTask<Object, Void, Void> {
	protected static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	protected BenchmarkLauncher mLauncher;
	protected BenchSize mSize;
	protected Boolean mNative;

	@Override
	protected Void doInBackground(Object... params) {
		mLauncher = (BenchmarkLauncher) params[0];
		mSize = (BenchSize) params[1];
		mNative = (Boolean) params[2];

		return null;
	}

	protected BenchSize getSize() {
		return mSize;
	}

	protected Boolean isNative() {
		return mNative;
	}

	@Override
	protected void onPostExecute(Void result) {
		try {
			Runtime.getRuntime().exec("/sbin/m5 dumpstats");
		} catch (IOException e) {
		}

		if (mLauncher != null)
			mLauncher.done();
	}

	@Override
	protected void onPreExecute() {
		mLauncher = null;
		mSize = BenchSize.SMALL;
		mNative = false;
		try {
			Runtime.getRuntime().exec("/sbin/m5 resetstats");
		} catch (IOException e) {
		}
	}
}
