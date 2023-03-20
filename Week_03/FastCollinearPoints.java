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
import java.util.Comparator;
import java.util.Iterator;

public class FastCollinearPoints {

    private Stack<LineSegment> lines;
    private int lineNum = 0;

    public FastCollinearPoints(Point[] points) {

        validate(points);

        lines = new Stack<>();

        if (points.length >= 4) {

            Point[] p = removeDuplicates(points);

            int n = p.length;

            for (int i = 0; i < n; i++) {
                update1(p, i);
            }

        }

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

    private void validate(Point[] points) {

        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();

    }

    private Point[] removeDuplicates(Point[] p) {

        Point[] p1 = new Point[p.length];
        for (int i = 0; i < p.length; i++) p1[i] = p[i];

        Arrays.sort(p1);

        int cI = 1, cPi = 0;

        while (cI < p1.length) {
            if (p1[cI].compareTo(p1[cPi]) != 0) {
                p1[++cPi] = p1[cI];
            }
            cI++;
        }

        Point[] p2 = new Point[cPi + 1];

        for (int i = 0; i <= cPi; i++) p2[i] = p1[i];

        return p2;


    }

    private void update1(Point[] p, int i) {

        Point pR = p[i];

        Point[] p2 = new Point[p.length - 1];

        int p2I = 0, pI = 0;

        while (p2I < p2.length) {
            if (pI != i) p2[p2I++] = p[pI++];
            else pI++;
        }

        Comparator<Point> comparator = pR.slopeOrder();

        Arrays.sort(p2, comparator);

        double s1 = pR.slopeTo(p2[0]);

        int sI = 0, eI = 0, cI = 1;

        while (cI < p2.length) {

            double s2 = pR.slopeTo(p2[cI]);

            if (s1 == s2) {
                eI++;
                cI++;
            }
            else {
                update2(p2, pR, sI, eI);
                sI = cI;
                eI = sI;
                cI++;
                s1 = s2;
            }

        }

        update2(p2, pR, sI, eI);


    }

    private void update2(Point[] p1, Point p2, int sI, int eI) {

        if (eI - sI + 1 < 3 || p2.compareTo(p1[sI]) > 0) return;

        LineSegment li = new LineSegment(p2, p1[eI]);

        lines.push(li);
        lineNum++;

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
