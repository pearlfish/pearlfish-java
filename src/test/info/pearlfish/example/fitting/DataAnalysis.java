package info.pearlfish.example.fitting;

import java.util.List;

import static info.pearlfish.example.fitting.DoubleFunctions.*;

public class DataAnalysis {
    /**
     * @param points the points to fit a trend line to
     * @return a Polynomial of degree 1 (i.e. linear)
     */
    public static Polynomial fitLineTo(List<Point> points) {
        double n = points.size();
        double sumX = sum(map(Point.toX, points));
        double sumY = sum(map(Point.toY, points));
        double sumXY = sum(map(mul(Point.toX, Point.toY), points));
        double sumXX = sum(map(sq(Point.toX), points));

        double divisor = n*sumXX - sumX*sumX;

        double a = (sumY*sumXX - sumX*sumXY) / divisor;
        double b = (n*sumXY - sumX*sumY) / divisor;

        return new Polynomial(a,b);
    }

    public static <T> double mean(double[] xs) {
        return sum(xs) / xs.length;
    }

    public static <T> double sampleVariance(double[] xs) {
        double mean = mean(xs);

        double sumMeanDiffSq = 0;
        for (double x : xs) {
            sumMeanDiffSq += sq(x - mean);
        }

        return sumMeanDiffSq / (xs.length-1);
    }
}
