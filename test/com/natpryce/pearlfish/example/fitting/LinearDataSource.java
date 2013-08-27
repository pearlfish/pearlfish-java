package com.natpryce.pearlfish.example.fitting;

import java.util.Iterator;
import java.util.Random;

public class LinearDataSource implements Iterable<Point>, Iterator<Point> {
    private final Random rng;
    private final Line line;
    private final double minX;
    private final double xRange;


    public LinearDataSource(final Random rng, Line line, double minX, double maxX) {
        this.line = line;
        this.minX = minX;
        this.xRange = maxX - minX;
        this.rng = rng;
    }

    @Override
    public Iterator<Point> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    public Point next() {
        return line.atX(minX + rng.nextDouble() * xRange);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
