package info.pearlfish.example.fitting;

import info.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static info.pearlfish.formats.TemplateFormats.SVG;

/**
 * Note: all these tests will pass because the results have been approved.  But what's interesting is *should* they
 * have been?  The visualisation makes it clear which results are valid and which are not.
 */
public class DataFittingTest {
    @Rule
    public ApprovalRule<Object> approval = new ApprovalRule<Object>("test", SVG.withTemplate("DataFittingTest.svg.template"));

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
        final Polynomial trendLine = DataFitting.fitLineTo(points);

        approval.check(new Object() {
            public Iterable<Point> data = points;
            public int minX = 0;
            public int minY = 0;
            public double width = 20;
            public double height = 20;
            public Object trend = new Object() {
                public Point p0 = trendLine.atX(minX);
                public Point p1 = trendLine.atX(width);
            };
        });
    }
}
