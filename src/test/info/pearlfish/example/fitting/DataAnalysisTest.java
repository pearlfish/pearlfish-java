package info.pearlfish.example.fitting;

import info.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static info.pearlfish.example.fitting.DoubleFunctions.map;
import static info.pearlfish.formats.TemplateFormats.SVG;

public class DataAnalysisTest {
    @Rule
    public ApprovalRule<Object> approval = new ApprovalRule<Object>("src/test", SVG.withTemplate("DataAnalysisTest.svg.template"));

    @Test
    public void fitSetI() throws IOException {
        testFitAgainst(AnscombesQuartet.SET_I);
    }

    @Test
    public void fitSetII() throws IOException {
        testFitAgainst(AnscombesQuartet.SET_II);
    }

    @Test
    public void fitSetIII() throws IOException {
        testFitAgainst(AnscombesQuartet.SET_III);
    }

    @Test
    public void fitSetIV() throws IOException {
        testFitAgainst(AnscombesQuartet.SET_IV);
    }

    private void testFitAgainst(final List<Point> points) throws IOException {
        // This is what we're testing...
        final Polynomial trendLine = DataAnalysis.fitLineTo(points);

        approval.check(new Object() {
            public Iterable<Point> data = points;
            public int minX = 0;
            public int minY = 0;
            public double width = 20;
            public double height = 20;
            public String meanX = toDP(2, DataAnalysis.mean(map(Point.toX, points)));
            public String meanY = toDP(2, DataAnalysis.mean(map(Point.toY, points)));
            public String varX = toDP(3, DataAnalysis.sampleVariance(map(Point.toX, points)));
            public String varY = toDP(3, DataAnalysis.sampleVariance(map(Point.toY, points)));
            public Object trend = new Object() {
                public Point p0 = trendLine.atX(minX);
                public Point p1 = trendLine.atX(width);
            };
        });
    }

    private String toDP(int dps, double value) {
        return String.format("%."+dps+"f", value);
    }
}
