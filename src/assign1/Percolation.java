package assign1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * It models a percolation system by  n-by-n grid of sites
 * Each site is either open or blocked. 
 * A full site is an open site that can be connected to an open site in the top row 
 * via a chain of neighboring (left, right, up, down) open sites. 
 * We say the system percolates if there is a full site in the bottom row. 
 * In other words, a system percolates 
 * if we fill all open sites connected to the top row 
 * and that process fills some open site on the bottom row.
 */
public class Percolation {

    // this is for connecting neibours of a site
    private static final byte[] DX = {-1, 0, 0, 1};
    private static final byte[] DY = {0, -1, 1, 0};

    // row starts from 1
    private static final int TOP = 1;

    // use the byte 100 to express a site is open
    private static final byte OPEN = 4;

    // use the byte 010 to express a site connects to the top row
    private static final byte TOP_CONNECTED = 2;

    // use the byte 001 to express a site connects to the bottom row
    private static final byte BOTTOM_CONNECTED = 1;

    // use the byte 111 to express the system percolates
    private static final byte PERCOLATED =  7;

    // an byte array tp store the status of the site
    private final byte[] statuses;

    // number of open sites
    private int openSites;

    // the length of the system array
    private final int len;

    // union-find data type to determine whether two sites are connected
    private final WeightedQuickUnionUF uf;

    // flag of whether the system peroclates
    private boolean isPercolated;


    /**
     * 
     * @param n size of grid
     * @throws IllegalArgumentException
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("input number must be greater than 0");
        }
        // the row and col start from 1, so we plus 2
        statuses = new byte[n*n+2];
        uf = new WeightedQuickUnionUF(n*n+2);
        openSites = 0;
        len = n;
    }

    

 
    /**
     * opens the site (row, col) if it is not open already
     * 
     * @param row row of the site
     * @param col col of the site
     */
    public void open(int row, int col) {
        validate(row);
        validate(col);
        if (isOpen(row, col)) {
            return;
        }
        openSites++;
        connectNeighbors(row, col);
    }


    /**
     * 
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
     * If a site connects to the top row, then it's full
     * 
     * @param row row of the site
     * @param col col of the site
     * @return is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        int index = convertTwoDindex(row, col);
        int root = uf.find(index);
        return (statuses[root] & TOP_CONNECTED) == TOP_CONNECTED;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * 
     * @return does the system percolate?
     */
    public boolean percolates() {
        return isPercolated;
    }

   
    // connect current site with its neighbors 
    // and update current site's and its neihbors' statuses
    private void connectNeighbors(int row, int col) {
        int index = convertTwoDindex(row, col);

        // initial status to open
        byte curStatus = OPEN;
        if (row == TOP) {
            curStatus = (byte) (curStatus | TOP_CONNECTED);
        }

        // Don't use else as when n = 1, row can be either the top row and bottom row
        if (row == len) {
            curStatus = (byte) (curStatus | BOTTOM_CONNECTED);
        }
        
        for (int i = 0; i < DX.length; i++) {
            int neighborRow = row + DY[i];
            int neighborCol = col + DX[i];
            if (isInGrid(neighborRow, neighborCol) && isOpen(neighborRow, neighborCol)) {
                int neighborIndex = convertTwoDindex(neighborRow, neighborCol);
                int root = uf.find(neighborIndex);
                
                // update current status by old roots
                curStatus = (byte) (statuses[root] | curStatus);
                statuses[neighborIndex] = curStatus;
                uf.union(neighborIndex, index);
            }
        }

        int root = uf.find(index);
        statuses[root] = curStatus;
        statuses[index] = curStatus;

        // if the systems percolates, update the flag
        if (curStatus == PERCOLATED) {
            isPercolated = true;
        }
    }
    
    // row and col starts from 1 and is no greater than len
    private void validate(int n) {
        if (n <= 0 || n > len) {
            throw new IllegalArgumentException("input number can't be 0 and negative and greater than n");
        }
    }

    // convert 2D array index that starts from 0 to 1D index that starts from 1
    private int convertTwoDindex(int i, int j) {
        return ((i-1) * len) + (j-1);
    }

    private boolean isInGrid(int row, int col) {
        return row > 0 && row <= len && col > 0 && col <= len;
    }

}
