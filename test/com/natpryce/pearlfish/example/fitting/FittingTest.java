package com.natpryce.pearlfish.example.fitting;

import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.natpryce.pearlfish.formats.Formats.SVG;

public class FittingTest {
    @Rule
    public ApprovalRule<Object> approval = new ApprovalRule<Object>("test", SVG);


    @Test
    public void linearFitting() throws IOException {
        final int minX = 0;
        final int maxX = 1000;
        final LinearDataSet dataset = new LinearDataSet(1L, new Line(100,0.5), minX, maxX, 50);
        final List<Point> points = dataset.next(100);

        final Line trendLine = linearFit(points);

        approval.check(new Object() {
            List<Point> data = points;
            Object trend = new Object() {
                Point p0 = trendLine.atX(minX);
                Point p1 = trendLine.atX(maxX);
            };
        });
    }

    private Line linearFit(List<Point> points) {
        double n = points.size();
        double sumX = sum(points, new PointToDouble() {
            public double eval(Point p) { return p.x; }
        });
        double sumY = sum(points, new PointToDouble() {
            public double eval(Point p) { return p.y; }
        });
        double sumXY = sum(points, new PointToDouble() {
            public double eval(Point p) { return p.x * p.y; }
        });
        double sumXX = sum(points, new PointToDouble() {
            public double eval(Point p) { return p.x * p.x; }
        });

        final double divisor = n*sumXX - sumX * sumX;

        double a = (sumY*sumXX - sumX*sumXY) / divisor;
        double b = (n*sumXY - sumX*sumY) / divisor;

        return new Line(a,b);
    }

    private double sum(List<Point> points, PointToDouble fn) {
        double total = 0;
        for (Point p : points) {
            total += fn.eval(p);
        }
        return total;
    }

    private interface PointToDouble {
        double eval(Point p);
    }
}
