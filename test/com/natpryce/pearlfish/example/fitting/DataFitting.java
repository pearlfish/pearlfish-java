package com.natpryce.pearlfish.example.fitting;

import java.util.List;

public class DataFitting {
    public static Line linearFit(List<Point> points) {
        double n = points.size();
        double sumX = DoubleFunctions.sum(points, new DoubleFunction<Point>() {
            public double eval(Point p) {
                return p.x;
            }
        });
        double sumY = DoubleFunctions.sum(points, new DoubleFunction<Point>() {
            public double eval(Point p) {
                return p.y;
            }
        });
        double sumXY = DoubleFunctions.sum(points, new DoubleFunction<Point>() {
            public double eval(Point p) {
                return p.x * p.y;
            }
        });
        double sumXX = DoubleFunctions.sum(points, new DoubleFunction<Point>() {
            public double eval(Point p) {
                return p.x * p.x;
            }
        });

        final double divisor = n*sumXX - sumX * sumX;

        double a = (sumY*sumXX - sumX*sumXY) / divisor;
        double b = (n*sumXY - sumX*sumY) / divisor;

        return new Line(a,b);
    }
}
