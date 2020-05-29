import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {

    private Node root;
    private int size;
    private Point2D closest;
    private double minDistance;

    private static class Node {
        private final Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;
        private final boolean isVertical;

        public Node(Point2D p, Node left, Node right, boolean isVertical, Node parent) {
            this.point = p;
            this.left = left;
            this.right = right;
            this.isVertical = isVertical;

            if (parent == null) {
                this.rect = new RectHV(0, 0, 1, 1);
            } else {
                double xmin = parent.rect.xmin();
                double xmax = parent.rect.xmax();
                double ymin = parent.rect.ymin();
                double ymax = parent.rect.ymax();

                int cmp = parent.compareTo(point);

                if (isVertical) {
                    if (cmp > 0) {
                        ymax = parent.point.y();
                    } else {
                        ymin = parent.point.y();
                    }
                } else {
                    if (cmp > 0) {
                        xmax = parent.point.x();
                    } else {
                        xmin = parent.point.x();
                    }
                }

                this.rect = new RectHV(xmin, ymin, xmax, ymax);
            }

        }

        public int compareTo(Point2D p2) {
            if (isVertical)
                return Double.compare(this.point.x(), p2.x());
            return Double.compare(this.point.y(), p2.y());
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("An argument can't be null");

        if (contains(p)) return;

        root = insert(root, p, true, null);
    }

    private Node insert(Node node, Point2D p, boolean isVertical, Node parent) {
        if (node == null) {
            size++;
            return new Node(p, null, null, isVertical, parent);
        }

        int cmp = node.compareTo(p);

        if (cmp > 0) {
            node.left = insert(node.left, p, !isVertical, node);
        } else {
            node.right = insert(node.right, p, !isVertical, node);
        }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("An argument can't be null");

        return contains(root, p);
    }

    private boolean contains(Node node, Point2D p) {
        if (node == null) return false;
        else if (p.equals(node.point)) return true;

        int cmp = node.compareTo(p);

        if (cmp > 0) return contains(node.left, p);
        else return contains(node.right, p);
    }


    // draw all points to standard draw
    public void draw() {
        // todo
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("An argument can't be null");
        ArrayList<Point2D> points = new ArrayList<>();

        double minRectX = rect.xmin();
        double minRectY = rect.ymin();
        double maxRectX = rect.xmax();
        double maxRectY = rect.ymax();

        range(root, points, rect, minRectX, maxRectX, minRectY, maxRectY);
        return points;
    }

    private void range(Node node, ArrayList<Point2D> points, RectHV rect, double minRectX, double maxRectX, double minRectY, double maxRectY) {
        if (node == null) return;

        if (rect.contains(node.point))
            points.add(node.point);

        int cmpMin = rangeCmp(node, minRectX, minRectY);
        int cmpMax = rangeCmp(node, maxRectX, maxRectY);

        if (cmpMin >= 0) {
            range(node.left, points, rect, minRectX, maxRectX, minRectY, maxRectY);
        }
        if (cmpMax <= 0) {
            range(node.right, points, rect, minRectX, maxRectX, minRectY, maxRectY);
        }
    }

    // replaces Node.compareTo(Point2D p) method for range because of autograder issues
    private int rangeCmp(Node node, double rectX, double rectY) {
        if (node.isVertical)
            return Double.compare(node.point.x(), rectX);
        return Double.compare(node.point.y(), rectY);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("An argument can't be null");
        if (isEmpty()) return null;

        closest = root.point;
        minDistance = Double.POSITIVE_INFINITY;

        nearest(root, p);
        return closest;
    }

    private void nearest(Node node, Point2D p) {
        if (node == null) return;

        if (node.rect.distanceSquaredTo(p) < minDistance) {
            double curDist = p.distanceSquaredTo(node.point);

            if (curDist < minDistance) {
                closest = node.point;
                minDistance = curDist;
            }

            if (node.compareTo(p) > 0) {
                nearest(node.left, p);
                nearest(node.right, p);
            } else {
                nearest(node.right, p);
                nearest(node.left, p);
            }
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        StdOut.print(tree.isEmpty()); // true
        tree.insert(new Point2D(0.3, 0.4));
        tree.insert(new Point2D(0.1, 0.2));
        tree.insert(new Point2D(0.3, 0.4));
        StdOut.print(tree.size()); // 2
        StdOut.print(tree.contains(new Point2D(0.1, 0.2))); // true
        StdOut.print(tree.contains(new Point2D(0.1, 0.3))); // false
        StdOut.print(tree.nearest(new Point2D(0.09, 0.29))); // 0.1, 0.3
    }
}
