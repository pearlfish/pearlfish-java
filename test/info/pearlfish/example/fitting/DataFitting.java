package info.pearlfish.example.fitting;

import java.util.List;

import static info.pearlfish.example.fitting.DoubleFunctions.*;

public class DataFitting {
    /**
     * @param points the points to fit a trend line to
     * @return a Polynomial of degree 1 (i.e. linear)
     */
    public static Polynomial fitLineTo(List<Point> points) {
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
