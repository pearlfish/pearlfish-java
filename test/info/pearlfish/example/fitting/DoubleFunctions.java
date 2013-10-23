package info.pearlfish.example.fitting;

import java.util.Collection;

public class DoubleFunctions {
    public static <T> double[] map(DoubleFunction<T> fn, Collection<T> values) {
        double[] result = new double[values.size()];
        int i = 0;
        for (T value : values) {
            result[i++] = fn.eval(value);
        }
        return result;
    }

    public static double sum(double[] vs) {
        double total = 0;
        for (double v : vs) {
            total += v;
        }
        return total;
    }

    public static <T> DoubleFunction<T> mul(final DoubleFunction<T> f1, final DoubleFunction<T> f2) {
        return new DoubleFunction<T>() {
            @Override
            public double eval(T p) {
                return f1.eval(p) * f2.eval(p);
            }
        };
    }

    public static <T> DoubleFunction<T> sq(final DoubleFunction<T> f1) {
        return new DoubleFunction<T>() {
            @Override
            public double eval(T p) {
                double v = f1.eval(p);
                return v * v;
            }
        };
    }

    public static double sq(double x) {
        return x*x;
    }

    public static double[] sq(double[] xs) {
        double[] result = new double[xs.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = sq(xs[i]);
        }
        return result;
    }
}
