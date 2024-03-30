package assign1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * It models a percolation system by using an union-find data type
 */
public class Percolation {

    // this is for connecting neibours of a site
    private static final int[] DX = {-1, 0, 0, 1};
    private static final int[] DY = {0, -1, 1, 0};

    private boolean[][] grid;

    // number of open sites
    private int openSites;

    // For all sites with vitrual top and virtual bottom
    private WeightedQuickUnionUF uf;

    // For all sites with vitrual top
    private WeightedQuickUnionUF ufTop;

    private int width;

    // virtual top index
    private int top;

    // virtual bottom index
    private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("input number must be greater than 0");
        }
        grid = new boolean[n][n];
        // initialize all sites to blocked
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        uf = new WeightedQuickUnionUF(n*n+2);
        ufTop = new WeightedQuickUnionUF(n*n+1);
        openSites = 0;
        width = n;
        top = 0;
        bottom = n*n+1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row);
        validate(col);
        if (isOpen(row, col)) {
            return;
        }
        grid[row-1][col-1] = true;
        openSites++;
        connectNeighbors(row, col);
    }

    private void connectNeighbors(int row, int col) {
        int ufIndex = getUfIndex(row, col);
        // note that when n = 1, row can be either the first line and last line
        if (row == 1) {
            uf.union(top, ufIndex);
            ufTop.union(top, ufIndex);
        } 
        if (row == width) {
            uf.union(bottom, ufIndex);
        }

        for (int i = 0; i < DX.length; i++) {
            int neighborRow = row + DY[i];
            int neighborCol = col + DX[i];
            if (isInGrid(neighborRow, neighborCol) && isOpen(neighborRow, neighborCol)) {
                int neighborUfIndex = getUfIndex(neighborRow, neighborCol);
                uf.union(neighborUfIndex, ufIndex);
                ufTop.union(neighborUfIndex, ufIndex);
            }
        }
        
    }

    private boolean isInGrid(int row, int col) {
        return row > 0 && row <= width && col > 0 && col <= width;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return grid[row-1][col-1]; 
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        return ufTop.find(top) == ufTop.find(getUfIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(bottom) == uf.find(top);
    }
    
    private void validate(int n) {
        if (n <= 0 || n > width) {
            throw new IllegalArgumentException("input number can't be 0 and negative and greater than n");
        }
    }

    private int getUfIndex(int row, int col) {
        return (row-1)*width+col;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        while (!percolation.percolates()) {
            int row = StdRandom.uniformInt(1, 4);
            int col = StdRandom.uniformInt(1, 4);
            percolation.open(row, col);
        }

        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (percolation.isOpen(i, j)) {
                    System.out.println(i +","+j);
                }
            }
        }
    }
}
