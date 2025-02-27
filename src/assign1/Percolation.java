package assign1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * It models a percolation system by  n-by-n grid of sites
 * Each site can either be open or blocked. A full site is an open site 
 * that can connect to an open site in the top row 
 * via neighboring (left, right, up, down) open sites. 
 * A system percolates if there exists an open site 
 * connected to the top row that also connect to the bottom row.
 */
public class Percolation {

    // Direction vectors for negihbors (left, right, up, down)
    private static final byte[] DX = {-1, 0, 0, 1};
    private static final byte[] DY = {0, -1, 1, 0};

    // Constant integer for the top row in the grid (1-indexed)
    private static final int TOP = 1;

    // Constant bytes for site connectivity 
   // (to bottom row 001, to top 010, to open 100 and percolation 111)
    private static final byte BOTTOM_CONNECTED = 1;
    private static final byte TOP_CONNECTED = 2;
    private static final byte OPEN = 4;
    private static final byte PERCOLATED =  7;

    // An array tp store the status of each site
    private final byte[] statuses;

    // Counter for the number of open sites
    private int openSites;

    // Grid size (length of one side of the square grid)
    private final int len;

    // Union-find structure to track site connectivity
    private final WeightedQuickUnionUF uf;

    // Flag indicating whether the system peroclates
    private boolean isPercolated;


    /**
     * Constructor initializes the percolation system.
     * @param n The size of the grid (n x n).
     * @throws IllegalArgumentException if n <= 0.
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        // the row and col start from 1, so we plus 2
        statuses = new byte[n*n+2];
        uf = new WeightedQuickUnionUF(n*n+2);
        openSites = 0;
        len = n;
    }

 
    /**
     * Opens the site at the specified row and column.
     * @param row The row of the site
     * @param col The column of the site
     */
    public void open(int row, int col) {
        validate(row);
        validate(col);
        // If the site is already open, do nothing
        if (isOpen(row, col)) {
            return;
        }
        openSites++;
        connectNeighbors(row, col);
    }


    /**
     * Checks if the site at the specified row and column is open.
     * @param row row of the site
     * @param col col of the site
     * @return is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return (statuses[convertTwoDindex(row, col)] & OPEN) == OPEN; 
    }

    /**
     * Checks if the site at the specified row and column is full (connected to top row).
     * @param row row of the site
     * @param col column of the site
     * @return True if the site is open, false otherwise.
     */
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        int index = convertTwoDindex(row, col);
        int root = uf.find(index);
        return (statuses[root] & TOP_CONNECTED) == TOP_CONNECTED;
    }

    /**
     * Get the number of open sites in the system.
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Checks if the system percolates (i.e., there's a path from top to bottom).
     * @return true if the system percolates, false otherwise. 
     */
    public boolean percolates() {
        return isPercolated;
    }

   
    /**
     * Connects the current site with its neighbors if they're open.
     * Update the site statuses and union-find structure.
     * @param row row of the site
     * @param col column of the site
     */
    private void connectNeighbors(int row, int col) {
        int index = convertTwoDindex(row, col);

        // Initial status of the site
        byte curStatus = OPEN;
        if (row == TOP) {
            curStatus = (byte) (curStatus | TOP_CONNECTED);
        }

        // If it's in the bottom row, mark it's bottom-connected
        if (row == len) {
            curStatus = (byte) (curStatus | BOTTOM_CONNECTED);
        }
        
        // Check and connect to all neighboring open sites
        for (int i = 0; i < DX.length; i++) {
            int neighborRow = row + DY[i];
            int neighborCol = col + DX[i];
            if (isInGrid(neighborRow, neighborCol) && isOpen(neighborRow, neighborCol)) {
                int neighborIndex = convertTwoDindex(neighborRow, neighborCol);
                int root = uf.find(neighborIndex);
                
                // Update current based on neighbors' statues
                curStatus = (byte) (statuses[root] | curStatus);
                statuses[neighborIndex] = curStatus;
                uf.union(neighborIndex, index);
            }
        }

        int root = uf.find(index);
        statuses[root] = curStatus;
        statuses[index] = curStatus;

        // If the systems percolates, update the flag
        if (curStatus == PERCOLATED) {
            isPercolated = true;
        }
    }
    
    /**
     * Validate the row/column values (must be within valid bounds).
     * @param n row/column of the site
     */
    private void validate(int n) {
        if (n <= 0 || n > len) {
            throw new IllegalArgumentException("Invalid index for row/column");
        }
    }

    /**
     * Convert 2D index to 1D index.
     * @param row row of the site
     * @param col column of the site
     * @return 1D index
     */
    private int convertTwoDindex(int row, int col) {
        return ((row-1) * len) + (col-1);
    }

    /**
     *  Check if the site is within the grid boundaries.
     * @param row row of the site
     * @param col column of the site
     * @return true if the site is within the grid boundaries, false otherwise.
     */
    private boolean isInGrid(int row, int col) {
        return row > 0 && row <= len && col > 0 && col <= len;
    }

}
