package com.natpryce.pearlfish.example.fitting;

import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Lists.newArrayList;
import static com.natpryce.pearlfish.formats.Formats.SVG;

public class DataFittingTest {
    @Rule
    public ApprovalRule<Object> approval = new ApprovalRule<Object>("test", SVG);

    final Random rng = new Random(1L);

    @Test
    public void fitLinearRandomData() throws IOException {
        final int minX = 0;
        final int maxX = 1000;
        final List<Point> points = take(100, new PolynomialDataSource(rng, new Polynomial(100,0.5), 50, minX, maxX));

        // This is what we're testing...
        final Polynomial trendLine = DataFitting.linearFit(points);

        approval.check(new Object() {
            public List<Point> data = points;
            public Object trend = new Object() {
                public Point p0 = trendLine.atX(minX);
                public Point p1 = trendLine.atX(maxX);
            };
        });
    }

    @Test
    @Ignore("example failing SVG test")
    public void fitQuadraticRandomData() throws IOException {
        final int minX = 0;
        final int maxX = 1000;
        final List<Point> points = take(100, new PolynomialDataSource(rng, new Polynomial(250, -0.5, 0.00125, -0.00000005), 50, minX, maxX));

        // This is what we're testing...
        final Polynomial trendLine = DataFitting.linearFit(points);

        approval.check(new Object() {
            public List<Point> data = points;
            public Object trend = new Object() {
                public Point p0 = trendLine.atX(minX);
                public Point p1 = trendLine.atX(maxX);
            };
        });
    }

    private static ArrayList<Point> take(int limitSize, Iterable<Point> dataset) {
        return newArrayList(limit(dataset, limitSize));
    }
}
