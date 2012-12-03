// Created by Peter Gavin
package edu.fsu.cs.mobile.benchmarks.math;
import java.util.Random;

public class FFTNaiveBench {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "FFFTNative";
	private static final int SMALL_SIZE = 8192; // must be a power of 2
	private static final int LARGE_SIZE = 65536; // must be a power of 2
	public static final int seed = 0;
    public static final int testsize = 16384;
    
	public static void sortLarge() {
		Random r = new Random(seed);

        Complex[] za = new Complex[LARGE_SIZE];
        
        for (int i = 0; i < LARGE_SIZE; ++i)
            za[i] = new Complex(r.nextDouble(), r.nextDouble());

        Complex[] zr = FFT.fftNaive(za);
	}

	public native static void sortLargeNative(); 

	public static void sortSmall() {
		Random r = new Random(seed);
        
        Complex[] za = new Complex[SMALL_SIZE];
        
        for (int i = 0; i < SMALL_SIZE; ++i)
            za[i] = new Complex(r.nextDouble(), r.nextDouble());

        Complex[] zr = FFT.fftNaive(za);
	}

	public native static void sortSmallNative();
}