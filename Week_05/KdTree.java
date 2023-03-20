/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;

    private class Node {

        private Point2D point2D;
        private Node left, right;
        private int size;
        private int level;

        public Node(Point2D p, int le, int s) {
            point2D = p;
            left = null;
            right = null;
            size = s;
            level = le;
        }

    }

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {

        if (x == null) return false;

        if (x.point2D.equals(p)) return true;

        if (x.level % 2 == 0) {
            if (p.x() < x.point2D.x()) return contains(x.left, p);
            else return contains(x.right, p);
        }
        else {
            if (p.y() < x.point2D.y()) return contains(x.left, p);
            else return contains(x.right, p);
        }

    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        if (contains(p)) return;
        root = insert(root, p, 0);
    }

    private Node insert(Node x, Point2D p, int le) {

        if (x == null) return new Node(p, le, 1);

        if (x.level % 2 == 0) {
            if (p.x() < x.point2D.x()) x.left = insert(x.left, p, le + 1);
            else x.right = insert(x.right, p, le + 1);
        }
        else {
            if (p.y() < x.point2D.y()) x.left = insert(x.left, p, le + 1);
            else x.right = insert(x.right, p, le + 1);
        }

        x.size = 1 + size(x.right) + size(x.left);

        return x;

    }

    public void draw() {
        draw(0, 1, 0, 1, root);
    }

    private void draw(double x1, double x2, double y1, double y2, Node x) {

        if (x == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.point2D.draw();

        if (x.level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            double t = x.point2D.x();
            Point2D p1 = new Point2D(t, y1);
            Point2D p2 = new Point2D(t, y2);
            p1.drawTo(p2);
            draw(x1, t, y1, y2, x.left);
            draw(t, x2, y1, y2, x.right);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            double t = x.point2D.y();
            Point2D p1 = new Point2D(x1, t);
            Point2D p2 = new Point2D(x2, t);
            p1.drawTo(p2);
            draw(x1, x2, y1, t, x.left);
            draw(x1, x2, t, y2, x.right);
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("");
        Queue<Point2D> queue = new Queue<>();
        range(root, rect, queue);
        return queue;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> queue) {

        if (x == null) return;

        if (rect.contains(x.point2D)) queue.enqueue(x.point2D);

        if (x.level % 2 == 0) {
            double t = x.point2D.x();
            if (t <= rect.xmax() && t >= rect.xmin()) {
                range(x.left, rect, queue);
                range(x.right, rect, queue);
            }
            else if (t < rect.xmin()) {
                range(x.right, rect, queue);
            }
            else {
                range(x.left, rect, queue);
            }
        }
        else {
            double t = x.point2D.y();
            if (t <= rect.ymax() && t >= rect.ymin()) {
                range(x.left, rect, queue);
                range(x.right, rect, queue);
            }
            else if (t < rect.ymin()) {
                range(x.right, rect, queue);
            }
            else {
                range(x.left, rect, queue);
            }
        }

    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        if (isEmpty()) return null;


        Point2D[] champ = new Point2D[1];

        champ[0] = null;

        nearest(root, p, champ);
        return champ[0];

    }

    private void nearest(Node x, Point2D p, Point2D[] champ) {

        if (x == null) return;

        if (champ[0] == null || p.distanceSquaredTo(champ[0]) > p.distanceSquaredTo(x.point2D))
            champ[0] = x.point2D;

        if (x.level % 2 == 0) {
            double t = x.point2D.x();
            if (p.x() < t) {
                nearest(x.left, p, champ);
                if (p.distanceSquaredTo(champ[0]) > (t - p.x()) * (t - p.x()))
                    nearest(x.right, p, champ);
            }
            else {
                nearest(x.right, p, champ);
                if (p.distanceSquaredTo(champ[0]) > (p.x() - t) * (p.x() - t))
                    nearest(x.left, p, champ);
            }

        }
        else {
            double t = x.point2D.y();
            if (p.y() < t) {
                nearest(x.left, p, champ);
                if (p.distanceSquaredTo(champ[0]) > (t - p.y()) * (t - p.y()))
                    nearest(x.right, p, champ);
            }
            else {
                nearest(x.right, p, champ);
                if (p.distanceSquaredTo(champ[0]) > (p.y() - t) * (p.y() - t))
                    nearest(x.left, p, champ);
            }

        }

    }

    public static void main(String[] args) {


    }

}

