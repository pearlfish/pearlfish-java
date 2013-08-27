package com.natpryce.pearlfish.example.fitting;

public class Point {
    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static final DoubleFunction<Point> toX = new DoubleFunction<Point>() {
        public double eval(Point p) {
            return p.x;
        }
    };

    public static final DoubleFunction<Point> toY = new DoubleFunction<Point>() {
        public double eval(Point p) {
            return p.y;
        }
    };
}
