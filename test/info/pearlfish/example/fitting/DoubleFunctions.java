package info.pearlfish.example.fitting;

import java.util.List;

public class DoubleFunctions {
    public static <T> double sum(List<T> values, DoubleFunction<T> fn) {
        double total = 0;
        for (T v : values) {
            total += fn.eval(v);
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
}
