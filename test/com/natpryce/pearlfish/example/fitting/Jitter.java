package com.natpryce.pearlfish.example.fitting;

import java.util.Iterator;
import java.util.Random;

public class Jitter implements Iterable<Point> {
    private final Random rng;
    private final double maxJitter;
    private final Iterable<Point> points;

    public Jitter(Random rng, double maxJitter, Iterable<Point> points) {
        this.rng = rng;
        this.maxJitter = maxJitter;
        this.points = points;
    }

    @Override
    public Iterator<Point> iterator() {
        final Iterator<Point> points = this.points.iterator();

        return new Iterator<Point>() {
            @Override
            public boolean hasNext() {
                return points.hasNext();
            }

            @Override
            public Point next() {
                return jitter(points.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private Point jitter(Point point) {
        double offset = rng.nextDouble() * maxJitter;
        double angle = rng.nextDouble() * Math.PI * 2.0;
        double offsetX = Math.cos(angle) * offset;
        double offsetY = Math.sin(angle) * offset;

        return new Point(point.x+offsetX, point.y+offsetY);
    }
}
