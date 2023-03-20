/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;

public class BruteCollinearPoints {

    private Stack<LineSegment> lines;
    private int lineNum = 0;

    public BruteCollinearPoints(Point[] points) {

        validate(points);

        lines = new Stack<>();

        int n = points.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {
                        double s1 = points[i].slopeTo(points[j]);
                        double s2 = points[j].slopeTo(points[k]);
                        double s3 = points[k].slopeTo(points[m]);
                        if (s1 == s2 && s2 == s3 && s3 == s1) {
                            update(points[i], points[j], points[k], points[m]);
                            lineNum++;
                        }
                    }
                }
            }
        }

    }

    private void update(Point p1, Point p2, Point p3, Point p4) {
        Point[] poi = { p1, p2, p3, p4 };
        Arrays.sort(poi);
        LineSegment l1 = new LineSegment(poi[0], poi[3]);
        lines.push(l1);
    }

    private void validate(Point[] points) {

        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();

    }

    public int numberOfSegments() {
        return lineNum;
    }

    public LineSegment[] segments() {

        LineSegment[] lines1 = new LineSegment[lineNum];

        Iterator<LineSegment> k = lines.iterator();

        int i = 0;

        while (k.hasNext()) {
            lines1[i++] = k.next();
        }

        return lines1;

    }

    public static void main(String[] args) {

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
