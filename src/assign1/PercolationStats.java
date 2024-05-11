package assign1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * It takes two command-line arguments n and T
 * , performs T independent computational experiments (discussed above) on an n-by-n grid
 * , and prints the sample mean, sample standard deviation
 * , and the 95% confidence interval for the percolation threshold
 */
public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private int t;

    // store all thresholds
    private double[] probabilities;

    /**
     * perform independent trials on an n-by-n grid
     * @param n size of grid
     * @param trials numbr of independent trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or tirals <= 0");
        }
        probabilities = new double[trials];
        t = trials;
        for (int i = 0; i < trials; i++) {
            probabilities[i] = getThreshold(n);
        }
    }
    

    /**
     * 
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(probabilities);
    }

    /**
     * 
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(probabilities);
    }

    /**
     * 
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95*stddev()) / Math.sqrt(t));
    }

    /**
     * 
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95*stddev()) / Math.sqrt(t));
    }


    private double getThreshold(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniformInt(1, n+1);
            int col = StdRandom.uniformInt(1, n+1);
            percolation.open(row, col);
        }
        return (double) percolation.numberOfOpenSites() / (n*n); 
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),  Integer.parseInt(args[1]));
        System.out.printf("%s = %f\n", "mean", stats.mean());
        System.out.printf("%s = %f\n", "stddev", stats.stddev());
        System.out.printf("%s = [%f, %f]", "95% confidence interval", stats.confidenceLo(), stats.confidenceHi());
    }
}
