package com.natpryce.pearlfish.example.fitting;

import java.util.List;

import static com.natpryce.pearlfish.example.fitting.DoubleFunctions.*;

public class DataFitting {

    public static Polynomial linearFit(List<Point> points) {
        double n = points.size();
        double sumX = sum(points, Point.toX);
        double sumY = sum(points, Point.toY);
        double sumXY = sum(points, mul(Point.toX, Point.toY));
        double sumXX = sum(points, sq(Point.toX));

        double divisor = n*sumXX - sumX*sumX;

        double a = (sumY*sumXX - sumX*sumXY) / divisor;
        double b = (n*sumXY - sumX*sumY) / divisor;

        return new Polynomial(a,b);
    }
}
