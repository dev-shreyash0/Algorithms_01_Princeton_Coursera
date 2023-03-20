/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int[][] t;
    private final int n;

    public Board(int[][] tiles) {

        n = tiles.length;
        t = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                t[i][j] = tiles[i][j];

    }

    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append(n);
        s.append("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(t[i][j] + " ");
            }
            s.append("\n");
        }

        return s.toString();

    }

    public int dimension() {
        return n;
    }

    public int hamming() {

        int num = 0;

        for (int i = 1; i <= n * n - 1; i++) if (!inPlace(i)) num++;

        return num;

    }

    public int manhattan() {

        int num = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                num += distance(t[i][j], i, j);
            }
        }

        return num;

    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {

        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        if (this.dimension() != that.dimension()) return false;

        int size = this.dimension();

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (this.t[i][j] != that.t[i][j]) return false;

        return true;

    }

    public Iterable<Board> neighbors() {

        Stack<Board> stack = fill();

        return stack;

    }

    public Board twin() {

        int i = -1, j = -1, x = -1, y = -1;

        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (t[u][v] != 0) {
                    if (i == -1) {
                        i = u;
                        j = v;
                    }
                    else {
                        x = u;
                        y = v;
                    }
                }
                if (x != -1) break;
            }
            if (x != -1) break;
        }
        return shift(i, j, x, y);

    }

    private Stack<Board> fill() {

        Stack<Board> stack = new Stack<>();

        int[] pos = getZero();

        if (pos[0] != 0) stack.push(shift(pos[0], pos[1], pos[0] - 1, pos[1]));
        if (pos[0] != n - 1) stack.push(shift(pos[0], pos[1], pos[0] + 1, pos[1]));
        if (pos[1] != 0) stack.push(shift(pos[0], pos[1], pos[0], pos[1] - 1));
        if (pos[1] != n - 1) stack.push(shift(pos[0], pos[1], pos[0], pos[1] + 1));

        return stack;

    }

    private Board shift(int i, int j, int x, int y) {

        int[][] t1 = new int[n][n];

        for (int u = 0; u < n; u++)
            for (int v = 0; v < n; v++)
                t1[u][v] = this.t[u][v];

        int temp = t1[i][j];
        t1[i][j] = t1[x][y];
        t1[x][y] = temp;

        return new Board(t1);

    }

    private int[] getZero() {

        int[] pos = new int[2];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (t[i][j] == 0) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }

        return pos;

    }

    private boolean inPlace(int i) {

        int row = (i - 1) / n;
        int col = (i - 1) % n;

        return i == t[row][col];

    }

    private int distance(int tile, int i, int j) {

        if (tile == 0) return 0;

        int x = (tile - 1) / n;
        int y = (tile - 1) % n;

        return Math.abs(i - x) + Math.abs(j - y);

    }


    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        System.out.println(initial.isGoal());
        System.out.println(initial.hamming());

    }
}
