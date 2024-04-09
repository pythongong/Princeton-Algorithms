package assign4;

import java.util.Arrays;

import edu.princeton.cs.algs4.Bag;

/**
 * Board
 */
public class Board {

    // this is for connecting neibours of a site
    private static final int[] DX = {-1, 0, 0, 1};
    private static final int[] DY = {0, -1, 1, 0};

    private int[][] grid;

    private int hammingNum;

    private int manhattanNum;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null || tiles.length == 0) {
            throw new IllegalArgumentException("tiles can't be null or empty");
        }
        grid = copyTitles(tiles);
        hammingNum = -1;
        manhattanNum = -1;
    }
                                        

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int len = dimension();
        stringBuilder.append(len + System.lineSeparator());
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (j == len - 1) {
                    stringBuilder.append(grid[i][j] + System.lineSeparator());
                } else {
                    stringBuilder.append(String.format("%2d", grid[i][j]));
                }
            }
        }
        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return grid.length;
    }

    // // number of tiles out of place
    public int hamming() {
        if (hammingNum == -1) {
            initManhattanAndHamming();
        }
        return hammingNum;
    }

    // // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanNum == -1) {
            initManhattanAndHamming();
        }
        return manhattanNum;
    }

    // // is this board the goal board?
    public boolean isGoal() {
        int num = 1;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != num++ && grid[i][j] != 0) {
                    return false;
                }
            }
        }
        return grid[n-1][n-1] == 0;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Board other = (Board) obj;
        if (!Arrays.deepEquals(grid, other.grid))
            return false;
        return true;
    }
    

    // all neighboring boards 
    public Iterable<Board> neighbors() {
        Bag<Board> neighbors = new Bag<>();
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    continue;
                }
                for (int k = 0; k < DX.length; k++) {
                    int neighborRow = i + DY[k];
                    int neighborCol = j + DX[k];
                    if (isInGrid(neighborRow) && isInGrid(neighborCol)) {
                        neighbors.add(new Board(getExchedGrid(i, j, neighborRow, neighborCol)));
                    }
                }
                break;
            }
        }
        return neighbors;
    }

    private int[][] getExchedGrid(int i, int j, int newRow, int newCol) {
        int[][] newTiles = copyTitles(grid);
        int oldVal = newTiles[newRow][newCol];
        newTiles[newRow][newCol] = newTiles[i][j];
        newTiles[i][j] = oldVal;
        return newTiles;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                for (int k = 0; k < DX.length; k++) {
                    int neighborRow = i + DY[k];
                    int neighborCol = j + DX[k];
                    if (isInGrid(neighborRow) && isInGrid(neighborCol) 
                    && grid[neighborRow][neighborCol] != 0) {
                        return new Board(getExchedGrid(i, j, neighborRow, neighborCol));
                    }
                }
            }
        }
        return null;
    }


    private void initManhattanAndHamming() {
        int n = dimension();
        hammingNum = 0;
        manhattanNum = 0;
        int num = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != num++ && grid[i][j] != 0) {
                    hammingNum++;
                    int cur = grid[i][j];
                    if (cur % n == 0) {
                        manhattanNum += Math.abs(i - ((cur / n) - 1)) 
                        + Math.abs(j - (n-1));
                    } else {
                        manhattanNum += Math.abs(i - (cur / n)) 
                        + Math.abs(j - ((grid[i][j] % n) - 1));
                    }
                    
                }
            }
        }
    }

    private int[][] copyTitles(int[][] tiles) {
        int n = tiles.length;
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            newTiles[i] = Arrays.copyOf(tiles[i], n);
        }
        return newTiles;
    }
    private boolean isInGrid(int index) {
        return index >= 0 && index < grid.length;
    }
}