/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.Iterator;

public class PointSET {

    private SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        return set.contains(p);
    }

    public void draw() {
        for (Point2D point2D : set) point2D.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("");
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : set) if (rect.contains(p)) queue.enqueue(p);
        return queue;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        if (set.isEmpty()) return null;

        Iterator<Point2D> iterator = set.iterator();

        Point2D p1 = iterator.next();

        double d1 = p1.distanceSquaredTo(p);

        while (iterator.hasNext()) {
            Point2D p2 = iterator.next();
            double d2 = p2.distanceSquaredTo(p);
            if (d2 < d1) {
                d1 = d2;
                p1 = p2;
            }
        }
        return p1;
        
    }


    public static void main(String[] args) {

    }
}
