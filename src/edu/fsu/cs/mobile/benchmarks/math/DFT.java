package edu.fsu.cs.mobile.benchmarks.math;
import java.lang.Math;

class DFT {

    private static Complex[] _dftNaive(final Complex[] za, final boolean inverse)
    {

        int n = za.length;

        Complex[] ta = new Complex[n];

        for (int i = 0; i < n; i++) {
            
            double t = -2.0 * Math.PI * (((double)i) / n);
            if (inverse)
                t = -t;
            ta[i] = new Complex(Math.cos(t), Math.sin(t));

        }

        Complex[] zo = new Complex[n];
        
        for (int i = 0; i < n; i++) {
            
            zo[i] = new Complex();
            
            for (int j = 0; j < n; j++) {

                int k = (i * j) % n;
                Complex w = ta[k];
                zo[i] = zo[i].add(za[j].mul(w));

            }
            
        }

        if (inverse) {
            for (int i = 0; i < n; i++)
                zo[i] = zo[i].div((double) n);
        }

        return zo;
        
    }

    public static Complex[] dftNaive(final Complex[] za)
    {
        return _dftNaive(za, false);
    }

    public static Complex[] idftNaive(final Complex[] za)
    {
        return _dftNaive(za, true);
    }

    private static void _dft(final double[] ra, final double[] ia, double[] ro, double[] io)
    {
        
        int n = ra.length;

        assert n == ia.length;

        double[] rt = new double[n];
        double[] it = new double[n];
        
        for (int i = 0; i < n; i++) {
            
            double t = -2.0 * Math.PI * (((double)i) / n);
            rt[i] = Math.cos(t);
            it[i] = Math.sin(t);

        }

        for (int i = 0; i < n; i++) {
            
            ro[i] = 0.0;
            io[i] = 0.0;
            
            for (int j = 0; j < n; j++) {

                int k = (i * j) % n;
                double wr = rt[k];
                double wi = it[k];
                ro[i] += ra[j] * wr - ia[j] * wi;
                io[i] += ra[j] * wi + ia[j] * wr;

            }
            
        }
        
    }

    public static void dft(final double[] ra, final double[] ia, double[] ro, double[] io)
    {
        _dft (ra, ia, ro, io);
    }
    
    public static void idft(final double[] ra, final double[] ia, double[] ro, double[] io)
    {
        _dft (ia, ra, io, ro);

        int n = ra.length;

        for (int i = 0; i < n; ++i) {
            ro[i] /= n;
            io[i] /= n;
        }
    }

}