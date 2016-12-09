import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

  private int N;
  private int trials;
  private double[] threshold;
  
  public PercolationStats(int N, int trials) {

    if (N <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    this.N = N;
    this.trials = trials;
    threshold = new double[trials];

    for (int i = 0; i < trials; i++) {
      Percolation percolation = new Percolation(N);
      int  openedSites = 0;
      while (!percolation.percolates()) {
   
        int row = StdRandom.uniform(N) + 1;
        int col = StdRandom.uniform(N) + 1;
        if (!percolation.isOpen(row, col)) {
          percolation.open(row, col);
          openedSites++;
        }
      }
      threshold[i] = (double) openedSites / (N * N);
    }
  }

  public double mean() {
    return StdStats.mean(threshold);
  }

  public double stddev() {
    return StdStats.stddev(threshold);
  }
  
  public double confidenceLo() {
    return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
  }
  
  public double confidenceHi() {
    return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
  }

  public static void main(String[] args) {

    int N = Integer.valueOf(args[0]);
    int trials = Integer.valueOf(args[1]);
    Stopwatch stopwatch = new Stopwatch();
    PercolationStats ps = new PercolationStats(N, trials);
    double t = stopwatch.elapsedTime();W
    System.out.println("Elapsed time : " + t);
    
    String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
    System.out.println("mean                    = " + ps.mean());
    System.out.println("stddev                  = " + ps.stddev());
    System.out.println("95% confidence interval = " + confidence);
  }
}