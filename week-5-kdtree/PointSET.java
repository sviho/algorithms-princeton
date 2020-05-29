import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("An argument can't be null");

        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("An argument can't be null");

        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : pointSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("An argument can't be null");

        ArrayList<Point2D> insideRect = new ArrayList<>();

        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                insideRect.add(point);
            }
        }

        return insideRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("An argument can't be null");

        if (pointSet.isEmpty()) return null;

        Point2D closest = null;

        for (Point2D point : pointSet) {
            if (closest == null || point.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
                closest = point;
            }
        }

        return closest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // todo
    }
}
