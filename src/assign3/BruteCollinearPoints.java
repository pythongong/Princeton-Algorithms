package assign3;

import java.util.Arrays;

import edu.princeton.cs.algs4.Bag;

/**
 * This class implements brute force solution to find all line segments. 
 * It examines 4 points at a time and checks whether they all lie on the same line segment, 
 * returning all such line segments. 
 */
public class BruteCollinearPoints {

    private LineSegment[] lines;

    public BruteCollinearPoints(Point[] points) {
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
        for (int i = 0; i < n - 3; i++) {
            Point starPoint = points[i];
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    if (starPoint.slopeTo(points[j]) != starPoint.slopeTo(points[k])) {
                        continue;
                    }
                    for (int m = k + 1; m < n; m++) {
                        if (starPoint.slopeTo(points[j]) == starPoint.slopeTo(points[m])) {
                            lineBag.add(new LineSegment(starPoint, points[m]));
                            break;
                        }
                    }
                }
            }
        }
        lines = new LineSegment[lineBag.size()];
        int i = 0;
        for (LineSegment line : lineBag) {
            lines[i++] = line;
        }
    }
    

}
