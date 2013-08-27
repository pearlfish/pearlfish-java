package com.natpryce.pearlfish.example.fitting;

public class Line {
    // y = a + bx
    public final double a;
    public final double b;

    public Line(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Point atX(double x) {
        return new Point(x, a + b*x);
    }
}
