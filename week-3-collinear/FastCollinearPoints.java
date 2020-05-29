import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNull(points);

        ArrayList<LineSegment> lines = new ArrayList<>();

        Point[] sortedByPoint = points.clone();
        Point[] sortedBySlope;

        Arrays.sort(sortedByPoint);
        checkDuplicates(sortedByPoint);

        for (Point origin : sortedByPoint) {
            sortedBySlope = sortedByPoint.clone();
            Arrays.sort(sortedBySlope, origin.slopeOrder());

            int j = 1;

            while (j < sortedBySlope.length) {
                ArrayList<Point> curPoints = new ArrayList<>();
                double curSlope = origin.slopeTo(sortedBySlope[j]);

                curPoints.add(sortedBySlope[j++]);

                while (j < sortedBySlope.length && origin.slopeTo(sortedBySlope[j]) == curSlope) {
                    curPoints.add(sortedBySlope[j++]);
                }

                if (curPoints.size() >= 3 && origin.compareTo(curPoints.get(0)) < 0) {
                    lines.add(new LineSegment(origin, curPoints.get(curPoints.size() - 1)));
                }
            }
        }

        segments = lines.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }

    private void checkNull(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points can't be null");
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("No point can be null");
        }
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points aren't allowed");
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
