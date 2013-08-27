package com.natpryce.pearlfish.example.fitting;

public class Polynomial {
    private final double[] coefficients;

    public Polynomial(double... coefficients) {
        this.coefficients = coefficients;
    }

    public Point atX(double x) {
        double y = 0;

        for (int i = 0; i < coefficients.length; i++) {
            y += coefficients[i] * Math.pow(x, i);
        }

        return new Point(x, y);
    }
}
