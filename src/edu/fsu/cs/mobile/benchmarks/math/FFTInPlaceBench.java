// Created by Peter Gavin
package edu.fsu.cs.mobile.benchmarks.math;
import java.util.Random;

import edu.fsu.cs.mobile.benchmarks.graph.UndirectedGraph;

public class FFTInPlaceBench {
	private static final String PKG = "edu.fsu.cs.mobile.benchmarks";
	private static final String TAG = "FFFTInPlace";
	private static final int SMALL_SIZE = 8192; 
	private static final int LARGE_SIZE = 65536;
    public static final int seed = 0;
    public static final int testsize = 16384;
     
	public static void sortLarge() {
    	 Random r = new Random(seed);
         
         double[] ra = new double[LARGE_SIZE];
         double[] ia = new double[LARGE_SIZE]; 
          
         for (int i = 0; i < LARGE_SIZE; ++i) {
             ra[i] = r.nextDouble();
             ia[i] = r.nextDouble();
         }

         FFT.fftInplace(ra, ia);
	}

	public native static void sortLargeNative(); 

	public static void sortSmall() {
		 Random r = new Random(seed);
	        
	        double[] ra = new double[SMALL_SIZE];
	        double[] ia = new double[SMALL_SIZE];
	        
	        for (int i = 0; i < SMALL_SIZE; ++i) {
	            ra[i] = r.nextDouble();
	            ia[i] = r.nextDouble();
	        }

	        FFT.fftInplace(ra, ia);
	}

	public native static void sortSmallNative();

}
