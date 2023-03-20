/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        if (this.y == that.y) return +0.0;
        return ((this.y - that.y) * 1.0) / (this.x - that.x);
    }

    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else {
            return Integer.compare(this.x, that.x);
        }
    }


    public Comparator<Point> slopeOrder() {
        return new SlopeCompare();
    }

    private class SlopeCompare implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double a = slopeTo(p1);
            double b = slopeTo(p2);
            return Double.compare(a, b);
        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
