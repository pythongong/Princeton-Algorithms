package assign5;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;

    private int size;

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    
    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        if (contains(p)) {
            return;
        }
        root = insert(root, null, p, 1);
        size++;
    }

    private Node insert(Node node, Node parent, Point2D p, int level) {
        if (node == null) {
            return createNewNode(parent, p, level);
        }

        double pVal = p.x(), nVal = node.point.x();
        if (level % 2 == 0) {
            pVal = p.y(); 
            nVal = node.point.y();
        }
        
        if (pVal < nVal) {
            node.left = insert(node.left, node, p, level+1); 
        } else  {
            node.right = insert(node.right, node, p, level+1); 
        } 
        return node;
    }

    private Node createNewNode(Node parent, Point2D p, int level) {
        if (isEmpty()) {
            return new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0));
        }

        if (level % 2 == 0) {
            double xmin = parent.point.x(), xmax = parent.rect.xmax();
            if (p.x() < parent.point.x()) {
                xmin = parent.rect.xmin();
                xmax = parent.point.x();
            }
            return new Node(p, new RectHV(xmin, parent.rect.ymin(), xmax, parent.rect.ymax()));
        } 

        double ymin = parent.point.y(), ymax = parent.rect.ymax();
        if (p.y() < parent.point.y()) {
            ymin = parent.rect.ymin();
            ymax = parent.point.y();
        } 
        return new Node(p,  new RectHV(parent.rect.xmin(), ymin, parent.rect.xmax(), ymax));
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        return contains(root, p, 1);
    }

    private boolean contains(Node node, Point2D p, int level) {
        if (node == null) {
            return false;
        }
        if (node.point.equals(p)) {
            return true;
        }
        double pVal = p.x(), nVal = node.point.x();
        if (level % 2 == 0) {
            pVal = p.y(); 
            nVal = node.point.y();
        }
        if (pVal < nVal) {
            return contains(node.left, p, level+1); 
        } else {
            return contains(node.right, p, level+1); 
        } 
    }

    // draw all points to standard draw 
    public void draw() {
        draw(root, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.05);
        
    }

     private void draw(Node node, int level) {
        if (node == null) {
            return;
        }
        draw(node.left, level+1);
        draw(node.right, level+1);

        StdDraw.setPenColor(StdDraw.BLACK);
        double radus = 0.01;
        StdDraw.setPenRadius(radus);
        node.point.draw();
        StdDraw.text(node.point.x(), node.point.y(), "(" + node.point.x()+","+node.point.y());
        
        StdDraw.setPenRadius(radus/2);
        if (level % 2 == 0) {
            Point2D left = new Point2D(node.rect.xmin(), node.point.y());
            Point2D right = new Point2D(node.rect.xmax(), node.point.y());
            StdDraw.setPenColor(StdDraw.BLUE);
            left.drawTo(right);
        } else {
            Point2D up = new Point2D(node.point.x(), node.rect.ymax());
            Point2D down = new Point2D(node.point.x(), node.rect.ymin());
            StdDraw.setPenColor(StdDraw.RED);
            down.drawTo(up);
        }
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        List<Point2D> points = new ArrayList<>();
        range(root, rect, points);
        return points;
    }

    private void range(Node node, final RectHV rect, final List<Point2D> points) {
        if (node == null || !rect.intersects(node.rect)) {
            return;
        }
        if (rect.contains(node.point)) {
            points.add(node.point);
        }
        range(node.left, rect, points);
        range(node.right, rect, points);
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        Node minNode = new Node(null, null);
        nearest(p, root, 1, minNode);
        return minNode.point;
    }


    private void nearest(Point2D p, Node node, int level, Node minNode) {
        if (node == null) {
            return;
        }
        if (minNode.point == null) {
            minNode.point = node.point;
        } else {
            double minDistance = minNode.point.distanceSquaredTo(p);
            if (minDistance <= node.rect.distanceSquaredTo(p)) {
                return;
            }
            if (minDistance > node.point.distanceSquaredTo(p)) {
                minNode.point = node.point;
            }
        }
        
        double pVal = p.x(), nVal = node.point.x();
        if (level % 2 == 0) {
            pVal = p.y(); 
            nVal = node.point.y();
        } 
        if (pVal < nVal) {
            nearest(p, node.left, level+1, minNode);
            nearest(p, node.right, level+1, minNode);
        } else {
            nearest(p, node.right, level+1, minNode);
            nearest(p, node.left, level+1, minNode);
        }
    }


    private static class Node {

        // the point
        private Point2D point;      

        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;    
        
        // the left/bottom subtree
        private Node left;        
        
        // the right/top subtree
        private Node right;

        public Node(Point2D p, RectHV rect) {
            this.point = p;
            this.rect = rect;
        }
        
     }
    
}
