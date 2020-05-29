import edu.princeton.cs.algs4.Merge;

import java.util.ArrayList;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        checkDuplicates(points);

        ArrayList<LineSegment> lines = new ArrayList<>();

        for (int i = 0; i < points.length - 1; i++) {
            Point point1 = points[i];

            for (int j = i + 1; j < points.length; j++) {
                Point point2 = points[j];
                double slope1 = point1.slopeTo(point2);

                for (int k = j + 1; k < points.length; k++) {
                    Point point3 = points[k];
                    double slope2 = point1.slopeTo(point3);

                    for (int m = k + 1; m < points.length; m++) {
                        Point point4 = points[m];
                        double slope3 = point1.slopeTo(point4);

                        if (slope1 == slope2 && slope1 == slope3) {
                            LineSegment line = combinePoints(point1, point2, point3, point4);
                            lines.add(line);
                        }
                    }
                }
            }
            segments = lines.toArray(new LineSegment[0]);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        if (segments == null) return new LineSegment[0];
        LineSegment[] lines = new LineSegment[numberOfSegments()];
        System.arraycopy(segments, 0, lines, 0, numberOfSegments());
        return lines;
    }

    private LineSegment combinePoints(Point point1, Point point2, Point point3, Point point4) {
        if (point1.compareTo(point2) > 0) {
            Point tmp = point2;
            point2 = point1;
            point1 = tmp;
        }
        if (point1.compareTo(point3) > 0) {
            Point tmp = point3;
            point3 = point1;
            point1 = tmp;
        }
        if (point1.compareTo(point4) > 0) {
            Point tmp = point4;
            point4 = point1;
            point1 = tmp;
        }
        if (point4.compareTo(point2) < 0) {
            point4 = point2;
        }
        if (point4.compareTo(point3) < 0) {
            point4 = point3;
        }
        return new LineSegment(point1, point4);
    }

    private void checkNull(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points can't be null");
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("No point can be null");
        }
    }

    private void checkDuplicates(Point[] points) {
        Point[] copy = new Point[points.length];
        System.arraycopy(points, 0, copy, 0, points.length);
        Merge.sort(copy);
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i].compareTo(copy[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points aren't allowed");
            }
        }
    }
}
