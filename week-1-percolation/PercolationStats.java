import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE = 1.96;
    private final int tries;
    private final double mean;
    private final double stdev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Either n or trials is out of range");
        tries = trials;
        int currentTry = 0;
        int randomX, randomY, count;
        Percolation perc;
        double[] openSites = new double[tries];

        while (currentTry < trials) {
            count = 0;
            perc = new Percolation(n);
            while (!perc.percolates()) {
                randomX = StdRandom.uniform(1, n + 1);
                randomY = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(randomX, randomY)) {
                    perc.open(randomX, randomY);
                    count++;
                }
            }
            openSites[currentTry] = count / ((double) n * n);
            currentTry++;
        }

        mean = StdStats.mean(openSites);
        stdev = StdStats.stddev(openSites);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (CONFIDENCE * stdev) / (Math.sqrt(tries));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (CONFIDENCE * stdev) / (Math.sqrt(tries));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percs = new PercolationStats(100, 10);
        System.out.println("mean = " + percs.mean());
        System.out.println("stddev = " + percs.stddev());
        System.out.println(
                "95% confidence interval = " + percs.confidenceLo() + percs.confidenceHi());
    }
}
