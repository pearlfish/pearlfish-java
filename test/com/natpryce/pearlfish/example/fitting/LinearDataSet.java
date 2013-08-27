package com.natpryce.pearlfish.example.fitting;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Lists.newArrayList;

public class LinearDataSet implements Iterator<Point>, Iterable<Point> {
    private final Random rng;
    private final Line line;
    private final double minX;
    private final double xRange;
    private final double maxJitter;


    public LinearDataSet(long seed, Line line, double minX, double maxX, double maxJitter) {
        this.line = line;
        this.minX = minX;
        this.xRange = maxX - minX;
        this.maxJitter = maxJitter;
        rng = new Random(seed);
    }

    public List<Point> next(int count) {
        return newArrayList(limit(this, count));
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    public Point next() {
        return jitter(line.atX(minX + rng.nextDouble() * xRange));
    }

    private Point jitter(Point point) {
        double offset = rng.nextDouble() * maxJitter;
        double angle = rng.nextDouble() * Math.PI * 2.0;
        double offsetX = Math.cos(angle) * offset;
        double offsetY = Math.sin(angle) * offset;

        return new Point(point.x+offsetX, point.y+offsetY);
    }

    @Override
    public Iterator<Point> iterator() {
        return this;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
