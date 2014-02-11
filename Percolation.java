public class Percolation {
    private int[][] grid; // stores 0 and 1 to show whether a site is open
    private int gridsize; // length of one side of the square grid
    private WeightedQuickUnionUF sites; // the connectedness of the cells in the grid
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        this.grid = new int[N][N];
        this.gridsize = N;
        this.sites = new WeightedQuickUnionUF(N*N + 2); // +2 are virtual start/end
    }
    
    // return the 1D representation of the point (i,j)
    private int xyTo1D(int i, int j)
    { return (i - 1) + (j - 1) * this.gridsize + 1; }
    
    // throw exception of i or j is outside of the specified bounds
    private void validateIndices(int i, int j) {
        if (i <= 0 || i > this.gridsize) {
            throw new IndexOutOfBoundsException("row index " + i + " out of bounds");
        }
        if (j <= 0 || j > this.gridsize) {
            throw new IndexOutOfBoundsException("col index " + j + " out of bounds");
        }
    }
    
    // opens site (row i, column j) if it is not already open
    // connects it to its open neighbors
    public void open(int i, int j) {
        validateIndices(i, j);
        if (!isOpen(i, j)) {
            this.grid[i - 1][j - 1] = 1;
            
            // if in the first/last row, connect to virtual start/end, respectively
            if (i == 1) sites.union(0, xyTo1D(i, j));
            if (i == this.gridsize) {
                sites.union(xyTo1D(i, j), this.gridsize * this.gridsize + 1);
            }
            
            // connect to other open neighbors
            if (i > 1 && isOpen(i - 1, j)) {
                sites.union(xyTo1D(i - 1, j), xyTo1D(i, j));
            }
            if (i < this.gridsize && isOpen(i + 1, j)) {
                sites.union(xyTo1D(i + 1, j), xyTo1D(i, j));
            }
            if (j > 1 && isOpen(i, j - 1)) {
                sites.union(xyTo1D(i, j - 1), xyTo1D(i, j));
            }
            if (j < this.gridsize && isOpen(i, j + 1)) {
                sites.union(xyTo1D(i, j + 1), xyTo1D(i, j));
            }
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateIndices(i, j);
        return (this.grid[i - 1][j - 1] == 1);
    }
    
    // is site (row i, column j) full?...does that site touch the top?
    public boolean isFull(int i, int j) {
        validateIndices(i, j);
        return this.sites.connected(xyTo1D(i, j), 0) && this.isOpen(i, j);
    }
    
    // does the system percolate?
    public boolean percolates()
    { return this.sites.connected(0, this.gridsize * this.gridsize + 1); }
}