package info.pearlfish.example.fitting;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * See http://en.wikipedia.org/wiki/Anscombe%27s_quartet
 */
public class AnscombesQuartet {
    public static final List<Point> SET_I = asList(
            new Point(10.0, 8.04),
            new Point(8.0, 6.95),
            new Point(13.0, 7.58),
            new Point(9.0, 8.81),
            new Point(11.0, 8.33),
            new Point(14.0, 9.96),
            new Point(6.0, 7.24),
            new Point(4.0, 4.26),
            new Point(12.0, 10.84),
            new Point(7.0, 4.82),
            new Point(5.0, 5.68));

    public static final List<Point> SET_II = asList(
            new Point(10.0, 9.14),
            new Point(8.0, 8.14),
            new Point(13.0, 8.74),
            new Point(9.0, 8.77),
            new Point(11.0, 9.26),
            new Point(14.0, 8.10),
            new Point(6.0, 6.13),
            new Point(4.0, 3.10),
            new Point(12.0, 9.13),
            new Point(7.0, 7.26),
            new Point(5.0, 4.74));

    public static final List<Point> SET_III = asList(
            new Point(10.0, 7.46),
            new Point(8.0, 6.77),
            new Point(13.0, 12.74),
            new Point(9.0, 7.11),
            new Point(11.0, 7.81),
            new Point(14.0, 8.84),
            new Point(6.0, 6.08),
            new Point(4.0, 5.39),
            new Point(12.0, 8.15),
            new Point(7.0, 6.42),
            new Point(5.0, 5.73));

    public static final List<Point> SET_IV = asList(
            new Point(8.0, 6.58),
            new Point(8.0, 5.76),
            new Point(8.0, 7.71),
            new Point(8.0, 8.84),
            new Point(8.0, 8.47),
            new Point(8.0, 7.04),
            new Point(8.0, 5.25),
            new Point(19.0, 12.50),
            new Point(8.0, 5.56),
            new Point(8.0, 7.91),
            new Point(8.0, 6.89));
}
