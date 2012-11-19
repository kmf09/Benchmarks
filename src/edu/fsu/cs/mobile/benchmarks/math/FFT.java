package edu.fsu.cs.mobile.benchmarks.math;

import java.lang.Math;
import java.lang.Integer;
import java.util.Random;

class FFT {
    
    private static Complex[] _fftNaive (final Complex[] za, final boolean inverse)
    {    
        int n = za.length;
        
        if (n == 0)
            return new Complex[0];
        
        if (n == 1)
            return new Complex[] { za[0] };
        
        int nr = n >> 1;
        
        Complex[] ze = new Complex[nr];
        Complex[] zo = new Complex[nr];
        
        for (int i = 0; i < nr; i++) {
            ze[i] = za[i << 1];
            zo[i] = za[(i << 1) + 1];
        }
        
        Complex[] zet = _fftNaive(ze, inverse);
        Complex[] zot = _fftNaive(zo, inverse);

        ze = null;
        zo = null;
        
        Complex[] zr = new Complex[n];
        
        for (int i = 0; i < nr; i++) {
            
            double t = -2.0 * i * Math.PI / n;
            if (inverse)
                t = -t;
            Complex w = new Complex(Math.cos(t), Math.sin(t));
            w = w.mul(zot[i]);
            
            zr[i] = zet[i].add(w);
            zr[i + nr] = zet[i].sub(w);

        }

        zet = null;
        zot = null;

        return zr;
        
    }

    public static Complex[] fftNaive(final Complex[] za)
    {
        int n = za.length;
        assert (n & (n - 1)) == 0;
        return _fftNaive(za, false);
    }

    public static Complex[] ifftNaive(final Complex[] za)
    {
        int n = za.length;
        assert (n & (n - 1)) == 0;

        Complex[] zr = _fftNaive(za, true);

        for (int i = 0; i < n; i++) {
            zr[i] = zr[i].div((double) n);
        }

        return zr;
    }

    private static void _fftInplace (double[] ra, double[] ia)
    {
        
        int n = ra.length;

        assert n == ia.length;

        // length must be a power of 2
        assert (n & (n - 1)) == 0;
        
        if (n < 2)
            return;
        
        int l = Integer.numberOfTrailingZeros(ra.length);
        
        // precalculate sines & cosines
        double[] rt = new double[n];
        double[] it = new double[n];
        
        for (int i = 0; i < n; i++) {
            
            double t = -2.0 * Math.PI * (((double)i) / ((double)n));
            rt[i] = Math.cos(t);
            it[i] = Math.sin(t);

        }
        
        // permute arrays by bit reversal
        for (int i = 0; i < n; i++) {

            int i2 = Integer.reverse(i) >>> (32 - l);

            if (i < i2) {
                double rs = ra[i];
                double is = ia[i];
                ra[i] = ra[i2];
                ia[i] = ia[i2];
                ra[i2] = rs;
                ia[i2] = is;
            }

        }
        
        // dit fft
        for (int s = 0; s < l; ++s) {
           
            for (int i = 0; i < (1 << s); ++i) {

                int wj = i << (l - s - 1);
                
                double wr = rt[wj];
                double wi = it[wj];
                
                for (int j = 0; j < (1 << (l - s - 1)); ++j) {

                    int m0 = (j << (s + 1)) + i;
                    int m1 = m0 + (1 << s);

                    double r0 = ra[m0], r1 = ra[m1];
                    double i0 = ia[m0], i1 = ia[m1];
                    
                    double tr = wr * r1 - wi * i1;
                    double ti = wr * i1 + wi * r1;
                    
                    ra[m0] = r0 + tr;
                    ia[m0] = i0 + ti;

                    ra[m1] = r0 - tr;
                    ia[m1] = i0 - ti;

                }
                
            }
            
        }
        
    }
    
    public static void fftInplace (double[] ra, double[] ia)
    {
        _fftInplace (ra, ia);
    }

    public static void ifftInplace (double[] ra, double[] ia)
    {
        _fftInplace (ia, ra);

        int n = ra.length;

        for (int i = 0; i < n; ++i) {
            ra[i] /= n;
            ia[i] /= n;
        }
    }

    private static final int seed = 0;
    private static final int size = 1024;
    private static final double epsilon = 0.000001;
    
    // functionality test -- not the actual benchmark
    public static void main (String[] args)
    {
        Random r = new Random(seed);
        
        double[] ra = new double[size];
        double[] ia = new double[size];

        double[] rb = new double[size];
        double[] ib = new double[size];

        double[] rc = new double[size];
        double[] ic = new double[size];

        double[] rd = new double[size];
        double[] id = new double[size];
        
        Complex[] za = new Complex[size];
        
        for (int i = 0; i < size; ++i) {
            ra[i] = r.nextDouble();
            ia[i] = r.nextDouble();
            rb[i] = ra[i];
            ib[i] = ia[i];
            za[i] = new Complex(ra[i], ia[i]);
        }

        fftInplace(ra, ia);
        DFT.dft(rb, ib, rc, ic);
        Complex[] zb = fftNaive(za);
        Complex[] zc = DFT.dftNaive(za);
        
        for (int i = 0; i < size; ++i) {
            //System.out.format("(%+02.8f, %+02.8f) (%+02.8f, %+02.8f) (%+02.8f, %+02.8f)\n", ra[i], ia[i], rc[i], ic[i], zc[i].r, zc[i].i);
            assert Math.abs(ra[i] - rc[i]) < epsilon;
            assert Math.abs(ia[i] - ic[i]) < epsilon;
            assert Math.abs(zb[i].r - rc[i]) < epsilon;
            assert Math.abs(zb[i].i - ic[i]) < epsilon;
            assert Math.abs(zc[i].r - rc[i]) < epsilon;
            assert Math.abs(zc[i].i - ic[i]) < epsilon;
        }

        ifftInplace(ra, ia);
        DFT.idft(rc, ic, rd, id);
        Complex[] zd = ifftNaive(zc);
        Complex[] ze = DFT.idftNaive(zb);
         
        for (int i = 0; i < size; ++i) {
            //System.out.format("(%+02.8f, %+02.8f) (%+02.8f, %+02.8f) (%+02.8f, %+02.8f) (%+02.8f, %+02.8f) (%+02.8f, %+02.8f)\n", ra[i], ia[i], rb[i], ib[i], rd[i], id[i], zd[i].r, zd[i].i, ze[i].r, ze[i].i);
            assert Math.abs(ra[i] - rb[i]) < epsilon;
            assert Math.abs(ia[i] - ib[i]) < epsilon;
            assert Math.abs(rd[i] - rb[i]) < epsilon;
            assert Math.abs(id[i] - ib[i]) < epsilon;
            assert Math.abs(zd[i].r - rb[i]) < epsilon;
            assert Math.abs(zd[i].i - ib[i]) < epsilon;
            assert Math.abs(ze[i].r - rb[i]) < epsilon;
            assert Math.abs(ze[i].i - ib[i]) < epsilon;
        }

    }

}