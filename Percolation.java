public class Percolation {
   private int[][] grid;
   private int gridsize;
   
   // create N-by-N grid, with all sites blocked
   public Percolation(int N) {
       this.grid = new int[N][N];
       this.gridsize = N*N;
   }
   
   public void open(int i, int j)         // open site (row i, column j) if it is not already
       // union
       
   public boolean isOpen(int i, int j)    // is site (row i, column j) open?
       
       
   public boolean isFull(int i, int j)    // is site (row i, column j) full?...does that site touch the top?
       // connected (top to i,j)
       
   public boolean percolates()            // does the system percolate?
       // connected (top to bottom)
}

// void union - merge component containing site 1 to component containing site 2
// boolean connected - are two sites in the same component?
// int find - returns component identifier (root)