package com.natpryce.pearlfish.example.fitting;

public class Polynomial {
    private final double[] coefficients;

    /**
     * @param coefficients the coefficients of the polynomial:
     *                     coefficients.length > 0 && coefficients[coefficients.length-1] != 0;
     */
    public Polynomial(double... coefficients) {
        assert coefficients.length > 0;
        assert coefficients[coefficients.length-1] != 0;

        this.coefficients = coefficients;
    }

    public int degree() {
        return coefficients.length - 1;
    }

    public Point atX(double x) {
        double y = 0;

        for (int i = 0; i < coefficients.length; i++) {
            y += coefficients[i] * Math.pow(x, i);
        }

        return new Point(x, y);
    }
}
