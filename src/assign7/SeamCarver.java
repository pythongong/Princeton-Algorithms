package assign7;

import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {

    private static final double BOUNDARY_ENERGY = 1000.0;

    private Picture picture;

    private double[][] energies;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("picture is null");
        }
        this.picture = copyPic(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // current picture
    public Picture picture() {
        return copyPic(picture);
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        checkIndex(x, y);
        if (onBorder(x, y)) {
            return BOUNDARY_ENERGY;
        }
        Color left = picture.get(x-1, y);
        Color right = picture.get(x+1, y);
        Color up = picture.get(x, y+1);
        Color down = picture.get(x, y-1);
        return Math.sqrt(calGradient(left, right)+ calGradient(up, down));
    }

     // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (picture.width() == 1) {
            return defaultSeam(picture.height());
        }
        if (picture.height() == 1) {
            return defaultSeam(1);
        }
        createEnergies();
        double[][] energyTo = createEnergyTo();
        int[][] colTo = createColTo(energyTo);
        double minEnergy = Double.POSITIVE_INFINITY;
        int minCol = 0;
        for (int row = 2; row < picture.height(); row++) {
            for (int col = 1; col < picture.width()-1; col++) {
                for (int i = -1; i < 2; i++) {
                    int x = col+i;
                    if (x < 1 || x > picture.width()-2 
                    || energyTo[col][row] < energies[col][row] + energyTo[x][row-1]) {
                        continue;
                    }
                    energyTo[col][row] = energies[col][row] + energyTo[x][row-1];
                    colTo[col][row] = x;
                }
                if (row == picture.height()-1 && minEnergy > energyTo[col][row]) {
                    minEnergy = energyTo[col][row];
                    minCol = col;
                }
            }
        }
        int[] cols = new int[picture.height()];
        for (int i = cols.length-1; i >= 0; minCol = colTo[minCol][i], i--) {
            cols[i] = minCol;
        }
        return cols;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (picture.height() == 1) {
            return defaultSeam(picture.width());
        }
        if (picture.width() == 1) {
            return defaultSeam(1);
        }
        createEnergies();
        double[][] energyTo = createEnergyTo();
        int[][] rowTo = createRowTo(energyTo);
        double minEnergy = Double.POSITIVE_INFINITY;
        int minRow = 0;
        for (int col = 2; col < picture.width(); col++) {
            for (int row = 1; row < picture.height()-1; row++) {
                for (int i = -1; i < 2; i++) {
                    int y = row +i;
                    if (y < 1 || y > picture.height()-2 
                    || energyTo[col][row] < energies[col][row] + energyTo[col-1][y]) {
                        continue;
                    }
                    energyTo[col][row] = energies[col][row] + energyTo[col-1][y];
                    rowTo[col][row] = y;
                }

                if (col == picture.width()-1 && minEnergy > energyTo[col][row]) {
                    minEnergy = energyTo[col][row];
                    minRow = row;
                }
            }
            
            
        }
        
        int[] rows = new int[picture.width()];
        for (int i = rows.length-1; i >= 0; minRow = rowTo[i][minRow], i--) {
            rows[i] = minRow;
        }
        return rows;
    }

    

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (picture.height() <= 1) {
            throw new IllegalArgumentException("picture's height is leass than or equal to 1");
        }
        checkSeam(seam, true);
        Picture newPicture = new Picture(picture.width(), picture.height()-1);
        for (int col = 0; col < picture.width(); col++) {
            int newRow = 0, oldRow = 0;
            while (oldRow < picture.height()) {
                if (seam[col] != oldRow) {
                    newPicture.setRGB(col, newRow++, picture.getRGB(col, oldRow++));
                } else {
                    oldRow++;
                }
            }
        }
        
        picture = newPicture;
        energies = null;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (picture.width() <= 1) {
            throw new IllegalArgumentException("picture's width is leass than or equal to 1");
        }
        checkSeam(seam, false);
        Picture newPicture = new Picture(picture.width()-1, picture.height());
        for (int row = 0; row < picture.height(); row++) {
            int newCol = 0, oldCol = 0;
            while (oldCol < picture.width()) {
                if (seam[row] != oldCol) {
                    newPicture.setRGB(newCol++, row, picture.getRGB(oldCol++, row));
                } else {
                    oldCol++;
                }
            }
        }
        picture = newPicture;
        energies = null;
    }

    private int[][] createColTo(double[][] energyTo) {
        int[][] colTo = new int[picture.width()][picture.height()];
        for (int col = 1; col < picture.width()-1; col++) {
            energyTo[col][1] = energies[col][1] + BOUNDARY_ENERGY;
            colTo[col][1] = col;
        }
        return colTo;
    }


    private int[][] createRowTo(double[][] energyTo) {
        int[][] rowTo = new int[picture.width()][picture.height()];
        for (int row = 1; row < picture.height()-1; row++) {
            energyTo[1][row] = energies[1][row] + BOUNDARY_ENERGY;
            rowTo[1][row] = row;
        }
        return rowTo;
    }


    private int[] defaultSeam(int size) {
        int[] seam = new int[size];
        Arrays.fill(seam, 0);
        return seam;
    }

    private Picture copyPic(Picture oldPic) {
        Picture newPic = new Picture(oldPic.width(), oldPic.height());
        for (int i = 0; i < oldPic.width(); i++) {
            for (int j = 0; j < oldPic.height(); j++) {
                newPic.setRGB(i, j, oldPic.getRGB(i, j));
            }
        }
        return newPic;
    }

    private void checkSeam(int[] seam, boolean isHorizontal) {
        if (seam == null) {
            throw new IllegalArgumentException("seam is null");
        }

        if (isHorizontal && seam.length != picture.width()) {
            throw new IllegalArgumentException("seam's length is wrong");
        }

        if (!isHorizontal && seam.length != picture.height()) {
            throw new IllegalArgumentException("seam's length is wrong");
        }

        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i+1]) > 1) {
                throw new IllegalArgumentException("seam's has wrong enry: "+seam[i]+", "+ seam[i+1]);
            }
        }

    }

    private double[][] createEnergyTo() {
        double[][] energyTo = new double[picture.width()][ picture.height()];
        for (int col = 0; col < picture.width(); col++) {
            Arrays.fill(energyTo[col], Double.POSITIVE_INFINITY);
        }
        return energyTo;
    }

    private void createEnergies() {
        if (energies != null) {
            return;
        }
        energies = new double[picture.width()][picture.height()];
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                energies[col][row] = energy(col, row);
            }
        }
    }

    private double calGradient(Color a, Color b) {
        return Math.pow(b.getRed() - a.getRed(), 2)
        + Math.pow(b.getGreen() - a.getGreen(), 2)
        + Math.pow(b.getBlue() - a.getBlue(), 2);
    }

    private boolean onBorder(int x, int y) {
        return x == 0 || x == picture.width()-1 || y == 0 || y == picture.height() -1;
    }

    private void checkIndex(int width, int height) {
        if (width < 0 || width >= picture.width()) {
            throw new IllegalArgumentException("width overflow");
        }
        if (height < 0 || height >= picture.height()) {
            throw new IllegalArgumentException("height overflow");
        }
    }
}
