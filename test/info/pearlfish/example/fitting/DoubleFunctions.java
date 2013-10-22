package info.pearlfish.example.fitting;

public class DoubleFunctions {
    public static <T> double sum(Iterable<T> values, DoubleFunction<T> fn) {
        double total = 0;
        for (T v : values) {
            total += fn.eval(v);
        }
        return total;
    }


    public static <T> double min(double initial, Iterable<T> values, DoubleFunction<T> fn) {
        double min = initial;
        for (T v : values) {
            min = Math.min(min, fn.eval(v));
        }
        return min;
    }

    public static <T> double max(double initial, Iterable<T> values, DoubleFunction<T> fn) {
        double max = initial;
        for (T v : values) {
            max = Math.max(max, fn.eval(v));
        }
        return max;
    }

    public static <T> double range(double initial, Iterable<T> values, DoubleFunction<T> fn) {
        return max(initial, values, fn) - min(initial, values, fn);
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

    public static int roundUpToNearest(int n, double value) {
        return n*(((int)(value+n-1))/n);
    }
}
