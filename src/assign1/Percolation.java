package assign1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;

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
        if(n <= 0) {
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
    public void open(int row, int col){
        validate(row);
        validate(col);
        if (isOpen(row, col)) {
            return;
        }
        grid[row-1][col-1] = true;
        openSites ++;
        connectNeighbors(row, col);
    }

    private void connectNeighbors(int row, int col) {
        int up = row -1, down = row + 1, left = col - 1, right = col + 1;
        int ufIndex = getUfIndex(row, col);
        if (row == 1) {
            uf.union(top, ufIndex);
            ufTop.union(top, ufIndex);
        } else if (up > top && isOpen(up, col) ) {
            int upUfIndex = getUfIndex(up, col);
            uf.union(upUfIndex, ufIndex);
            ufTop.union(upUfIndex, ufIndex);
        } 
        if(row == width -1) {
            uf.union(bottom, ufIndex);
        } else if (down < bottom && isOpen(down, col)) {
            int downUfIndex = getUfIndex(down, col);
            uf.union(downUfIndex, ufIndex);
            ufTop.union(downUfIndex, ufIndex);
        } 

        if(left > top && isOpen(row, left)) {
            int leftUfIndex = getUfIndex(row, left);
            uf.union(leftUfIndex, ufIndex);
            ufTop.union(leftUfIndex, ufIndex);
        }
        if(right < bottom && isOpen(row, right)) {
            int rightUfIndex = getUfIndex(row, right);
            uf.union(rightUfIndex, ufIndex);
            ufTop.union(rightUfIndex, ufIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        validate(row);
        validate(col);
        return grid[row-1][col-1] == true; 
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        validate(row);
        validate(col);
        return ufTop.find(top) == ufTop.find(getUfIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.find(bottom) == uf.find(top);
    }
    
    private void validate(int n) {
        if(n < top || n >= width) {
            throw new IllegalArgumentException("input number can't be 0 and negative and greater than n");
        }
    }

    private int getUfIndex(int row, int col) {
        return (row-1)*width+col;
    }
}
