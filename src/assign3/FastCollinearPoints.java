package assign3;

import java.util.Arrays;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

// finds all line segments containing 4 or more points
public class FastCollinearPoints {

    private LineSegment[] lines;
    
    public FastCollinearPoints(Point[] points) {
        if (points == null || points.length == 0) {
            throw new IllegalArgumentException();
        }
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i]  == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] copiedPoints = Arrays.copyOf(points, n);
        Arrays.sort(copiedPoints);
        for (int i = 0; i < n-1; i++) {
            if (copiedPoints[i] .equals(copiedPoints[i+1])) {
                throw new IllegalArgumentException();
            }
        }
        inilizeLines(copiedPoints);
    }

    // the number of line segments   
    public int numberOfSegments() {
        return lines.length;
    }         
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(lines, lines.length);
    }
    
    private void inilizeLines(Point[] points) {
        int n = points.length;
        if (n < 4) {
            lines = new LineSegment[0];
            return;
        }
        Bag<LineSegment> lineBag = new Bag<>();
        Point[] slopeSortedPs = Arrays.copyOf(points, n);
        for (Point p : points) {
            Arrays.sort(slopeSortedPs, p.slopeOrder());
            for (int i = 1; i < n;) {
                Point minPoint = slopeSortedPs[i], maxPoint = slopeSortedPs[i];
                int j = i + 1;
                for (; j < n && p.slopeTo(slopeSortedPs[i]) == p.slopeTo(slopeSortedPs[j]); j++) {
                    if (minPoint.compareTo(slopeSortedPs[j]) > 0) {
                        minPoint = slopeSortedPs[j];
                    }
                    if (maxPoint.compareTo(slopeSortedPs[j]) < 0) {
                        maxPoint = slopeSortedPs[j];
                    }
                }
                if (j - i >= 3 && p.compareTo(minPoint) < 0) {
                    lineBag.add(new LineSegment(p, maxPoint));
                }
                i = j;
            }
        }
        lines = new LineSegment[lineBag.size()];
        int i = 0;
        for (LineSegment line : lineBag) {
            lines[i++] = line;
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
