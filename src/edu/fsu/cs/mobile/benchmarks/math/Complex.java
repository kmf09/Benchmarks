package edu.fsu.cs.mobile.benchmarks.math;

import java.lang.Math;

final class Complex {
    
    public double r, i;

    public Complex()
    {
        r = 0.0;
        i = 0.0;
    }
    
    public Complex(double _r)
    {
        r = _r;
        i = 0.0;
    }
    
    public Complex(double _r, double _i)
    {
        r = _r;
        i = _i;
    }
    
    Complex add(Complex z)
    {
        return new Complex(r + z.r, i + z.i);
    }

    Complex sub(Complex z)
    {
        return new Complex(r - z.r, i - z.i);
    }

    Complex mul(Complex z)
    {
        return new Complex(r * z.r - i * z.i, r * z.i + i * z.r);
    }

    Complex mul(double d)
    {
        return new Complex(r * d, i * d);
    }

    Complex div(double d)
    {
        return new Complex(r / d, i / d);
    }

    double abs()
    {
        return Math.sqrt(r * r + i * i);
    }

}
