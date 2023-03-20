/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private final int minMoves;
    private final boolean solvable;
    private final Node lastNode;

    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException();

        boolean p1 = false, p2 = false;

        Comparator<Node> comparator = new PriorityFunction();

        MinPQ<Node> oT = new MinPQ<>(comparator);
        MinPQ<Node> dT = new MinPQ<>(comparator);

        Board twin = initial.twin();

        Node oCurr = new Node(initial, 0, null);
        Node dCurr = new Node(twin, 0, null);

        Node oPrev, dPrev;

        Node oLastNode = oCurr;

        oT.insert(oCurr);
        dT.insert(dCurr);

        while (!p1 && !p2) {

            oCurr = oT.delMin();
            Board oTemp = oCurr.getBoard();
            oPrev = oCurr.getPrev();
            p1 = oTemp.isGoal();

            dCurr = dT.delMin();
            Board dTemp = dCurr.getBoard();
            dPrev = dCurr.getPrev();
            p2 = dTemp.isGoal();

            for (Board b : oTemp.neighbors()) {
                if (oPrev != null && (oPrev.getBoard()).equals(b)) continue;
                Node newNode = new Node(b, oCurr.getMoves() + 1, oCurr);
                oT.insert(newNode);
            }

            for (Board b : dTemp.neighbors()) {
                if (dPrev != null && (dPrev.getBoard()).equals(b)) continue;
                Node newNode = new Node(b, dCurr.getMoves() + 1, dCurr);
                dT.insert(newNode);
            }

            oLastNode = oCurr;

        }

        if (p1) {
            this.minMoves = oLastNode.getMoves();
            this.solvable = true;
            this.lastNode = oLastNode;
        }
        else {
            this.minMoves = -1;
            this.solvable = false;
            this.lastNode = null;
        }

    }

    public boolean isSolvable() {
        return this.solvable;
    }

    public int moves() {
        return this.minMoves;
    }

    public Iterable<Board> solution() {

        if (!this.isSolvable()) return null;

        Stack<Board> stack = new Stack<Board>();

        Node node = this.lastNode;

        while (node != null) {

            Board temp = node.getBoard();
            stack.push(temp);
            node = node.prev;

        }

        return stack;

    }

    private class Node implements Comparable<Node> {

        private Board board;
        private int move;
        private int priority;
        private Node prev;

        private Node(Board b, int m, Node p) {
            this.board = b;
            this.move = m;
            this.priority = b.manhattan() + m;
            this.prev = p;
        }

        public int getMoves() {
            return this.move;
        }

        public int getPriority() {
            return this.priority;
        }

        public Board getBoard() {
            Board temp = this.board;
            return temp;
        }

        public Node getPrev() {
            Node temp = this.prev;
            return temp;
        }

        public int compareTo(Node that) {
            return Integer.compare(this.priority, that.priority);
        }


    }

    private class PriorityFunction implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.getPriority(), o2.getPriority());
        }
    }


    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        
    }
}
