/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] p;

    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Not a valid input");

        p = new double[trials];

        for (int i = 0; i < trials; i++) {

            Percolation exp = new Percolation(n);

            int count = 0;

            int[] rand = new int[n * n];

            for (int j = 0; j < rand.length; j++) rand[j] = j;

            StdRandom.shuffle(rand);

            int rI = 0;

            while (!exp.percolates()) {

                count++;

                int row = rand[rI] / n;
                int col = rand[rI] % n;
                rI++;

                exp.open(row + 1, col + 1);

            }

            p[i] = count * 1.0 / (n * n);

        }

    }

    public double mean() {
        return StdStats.mean(this.p);
    }

    public double stddev() {
        return StdStats.stddev(this.p);
    }

    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev() / Math.sqrt(this.p.length));
    }

    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev() / Math.sqrt(this.p.length));
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats exp = new PercolationStats(n, t);

        System.out.println("mean                    = " + exp.mean());
        System.out.println("stddev                  = " + exp.stddev());
        System.out.println(
                "95% confidence interval = [" + exp.confidenceLo() + ", " + exp.confidenceHi()
                        + "]");

    }

}
