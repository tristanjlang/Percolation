public class PercolationStats {
    private int N;
    private int T;
    private double[] sitesRequired;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0) { throw new IllegalArgumentException("N must be > 0"); }
        if (T <= 0) { throw new IllegalArgumentException("T must be > 0"); }
        this.N = N;
        this.T = T;
        this.sitesRequired = new double[T];
    }
    
    // adds an entry to the array of the ratio of sites required
    private void addToSitesRequired(int index, int numsites)
    { this.sitesRequired[index] = (double) numsites / (double) (N * N); }
    
    // sample mean of percolation threshold
    public double mean()
    { return StdStats.mean(sitesRequired); }
    
    // sample standard deviation of percolation threshold
    public double stddev()
    { return StdStats.stddev(sitesRequired); }
    
    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    { return mean() - 1.96 * stddev() / Math.sqrt(T); }
    
    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    { return mean() + 1.96 * stddev() / Math.sqrt(T); }        
    
    public static void main(int N, int T) {
        PercolationStats ps = new PercolationStats(N, T);
        
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            int opencount = 0;
            
            while (!p.percolates()) {
                int row, col;
                do {
                    row = StdRandom.uniform(1, N + 1);
                    col = StdRandom.uniform(1, N + 1);
                } while (p.isOpen(row, col));
            
                p.open(row, col);
                opencount++;
            }
            
            ps.addToSitesRequired(i, opencount);
        }
        
        StdOut.println("mean\t\t\t" + "= " + ps.mean());
        StdOut.println("stddev\t\t\t" + "= " + ps.stddev());
        StdOut.println("95% confidence interval\t" + "= " + ps.confidenceLo()
                           + ", " + ps.confidenceHi());
    }
}