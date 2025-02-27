package assign5;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * This class implements a kd-tree by using TreeSet.
 */
public class PointSET {
    
    private TreeSet<Point2D> pointSet;

    public PointSET() {
        pointSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    
    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        return pointSet.contains(p);
    }

    // draw all points to standard draw 
    public void draw() {
        for (Point2D point2d : pointSet) {
            point2d.draw();
        }
    }

     // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        List<Point2D> points = new ArrayList<>();
        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                points.add(point);
            }
        }
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        Point2D nearestPoint = null;
        for (Point2D point : pointSet) {
            if (nearestPoint == null) {
                nearestPoint = point;
            }
            if (point.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p)) {
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
}
