package assign1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * This class performs T independent computational experiments to estimate
 * the percolation threshod on an n-by-n grid and provides statistical analysis
 * on the results (mean, standard deviation and confidence interval). 
 */
public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    // Number of trials
    private int t;

    // An array that stores percolation thresholds for each trial
    private double[] probabilities;

    /**
     * Initializes the PercolationStats object and performs the trials.
     * @param n Size of the grid (n x n).
     * @param trials Number of independent trials.
     * @throws IllegalArgumentException if n or trials are <= 0.
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid size or number of trials must be greater than 0");
        }
        probabilities = new double[trials];
        t = trials;
        for (int i = 0; i < trials; i++) {
            // Perform percolation threshold calculation for each trial
            probabilities[i] = getThreshold(n);
        }
    }
    

    /**
     * Calculates the sample mean of the percolation thresholds.
     * @return Sample mean of percolation thresholds.
     */
    public double mean() {
        return StdStats.mean(probabilities);
    }

    /**
     * Calculates the sample standard deviation of the percolation thresholds.
     * @return Sample standard deviation of percolation thresholds.
     */
    public double stddev() {
        return StdStats.stddev(probabilities);
    }

    /**
     * Calculates the lower endpoint of the 95% confidence interval.
     * @return Lower endpoint of the confidence interval.
     */
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(t));
    }

    /**
     * Calculates the upper endpoint of the 95% confidence interval.
     * @return Upper endpoint of the confidence interval.
     */
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(t));
    }

    // Simulates percolation on an n-by-n grid and returns the percolation threshold.
    private double getThreshold(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniformInt(1, n + 1);
            int col = StdRandom.uniformInt(1, n + 1);
            percolation.open(row, col);
        }
        return (double) percolation.numberOfOpenSites() / (n * n);
    }

    /**
     * Main method to run the simulation from the command line with grid size and trials.
     * @param args Command-line arguments (grid size and number of trials).
     */
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
