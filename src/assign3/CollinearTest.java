package assign3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import edu.princeton.cs.algs4.Stopwatch;

public class CollinearTest {

    public static void testBrtute(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            Point[] points = new Point[Integer.parseInt(reader.readLine())];
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                // Process each line of the file
                if (line.isEmpty()) {
                    continue;
                }
                line = line.trim();
                String[] split = line.split("\\s+");
                points[index++] = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
            BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
            System.out.println(filename);
            System.out.println(collinearPoints.numberOfSegments());
            collinearPoints.numberOfSegments();
            
            LineSegment[] segments = collinearPoints.segments();
            for (LineSegment lineSegment : segments) {
                System.out.println(lineSegment.toString());
            }
            for (int i = 0; i < points.length; i++) {
                points[i] = new Point(i, i);
            }
            for (int i = 0; i < segments.length; i++) {
                segments[i] = new LineSegment(new Point(0, 0), new Point(0, 0));
            }
            LineSegment[] segments2 = collinearPoints.segments();
            for (LineSegment lineSegment : segments2) {
                System.out.println(lineSegment);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testFaster(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            Point[] points = new Point[Integer.parseInt(reader.readLine())];
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                // Process each line of the file
                if (line.isEmpty()) {
                    continue;
                }
                line = line.trim();
                String[] split = line.split("\\s+");
                points[index++] = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
            Stopwatch stopwatch = new Stopwatch();
            FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
            System.out.println(stopwatch.elapsedTime());
            LineSegment[] segments = collinearPoints.segments();
            System.out.println(filename);
            System.out.println(collinearPoints.numberOfSegments());
            for (LineSegment lineSegment : segments) {
                System.out.println(lineSegment.toString());
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            // Get the filename from command-line arguments
            String filename = args[i];
            testBrtute(filename);
            testFaster(filename);
        }
        
    }
    
}
