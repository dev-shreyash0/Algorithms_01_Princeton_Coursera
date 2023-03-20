/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] p;
    private WeightedQuickUnionUF u;
    private WeightedQuickUnionUF u1;
    private int openSites;

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("Not a valid input");
        p = new boolean[n][n];
        u = new WeightedQuickUnionUF((n * n) + 2);
        u1 = new WeightedQuickUnionUF((n * n) + 2);
        openSites = 0;
    }

    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > this.p[0].length || col > this.p[0].length) {
            throw new IllegalArgumentException("Not valid location");
        }
    }

    private int xyToz(int row, int col) {
        row--;
        col--;
        int n = this.p[0].length;
        return row * n + (col + 1);
    }

    public void open(int row, int col) {

        validate(row, col);
        if (this.p[row - 1][col - 1]) return;
        this.p[row - 1][col - 1] = true;
        openSites++;
        int n = this.p[0].length;

        int centre = xyToz(row, col);
        int left = xyToz(row, col - 1);
        int right = xyToz(row, col + 1);
        int up = xyToz(row - 1, col);
        int down = xyToz(row + 1, col);

        row--;
        col--;

        if (row == 0) {
            u.union(centre, 0);
            u1.union(centre, 0);
        }

        if (row == n - 1) u.union(centre, n * n + 1);

        if (col > 0 && this.p[row][col - 1]) {
            u.union(centre, left);
            u1.union(centre, left);
        }
        if (row > 0 && this.p[row - 1][col]) {
            u.union(centre, up);
            u1.union(centre, up);
        }
        if (col < n - 1 && this.p[row][col + 1]) {
            u.union(centre, right);
            u1.union(centre, right);
        }
        if (row < n - 1 && this.p[row + 1][col]) {
            u.union(centre, down);
            u1.union(centre, down);
        }

    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return this.p[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return this.isOpen(row, col) && u1.find(0) == u1.find(xyToz(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        int n = this.p[0].length;
        return u.find(0) == u.find(n * n + 1);
    }

    public static void main(String[] args) {
        
    }
}
