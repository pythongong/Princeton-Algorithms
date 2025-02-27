package assign10;

/**
 * This class implements the key component in the Burrows–Wheeler transform, known as the circular suffix array, 
 * which describes the abstraction of a sorted array of the n circular suffixes of a string of length n. 
 */

public class CircularSuffixArray {

    // cutoff to insertion sort
    private static final int CUTOFF =  15;   

    // extended ASCII alphabet size
    private static final int R = 256;   

    private final char[] text;

    private final int[] indexes;

    private final int len;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("null or empty string to CircularSuffixArray()");
        }
        len = s.length();
        text = s.toCharArray();
        indexes = new int[len];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        int[] aux = new int[len];
        msd(0, len-1, 0, aux);
    }

    // MSD sort from indexes[lo] to indexes[hi], starting at the dth byte
    // sort from indexes[lo] to indexes[hi], starting at the dth character
    private void msd(int lo, int hi, int d, int[] aux) {

        // cutoff to insertion sort for small subarrays
        if ((hi - lo) < CUTOFF)  {
            insertion(lo, hi, d);
            return;
        }

        // compute frequency counts
        int[] count = new int[R+2];
        
        for (int i = lo; i <= hi; i++) {
            count[charAt(indexes[i], d)+2]++;
        }

        for (int r = 0; r < R+1; r++) {
            count[r+1] += count[r];
        }

        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(indexes[i], d)+1]++] = indexes[i];
        }

        for (int i = lo; i <= hi; i++) {
            indexes[i] = aux[i-lo];
        }

        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++) {
            msd(lo + count[r], lo + count[r+1] - 1, d+1, aux);
        }
    }


    // insertion sort indexes[lo..hi], starting at dth character
    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(indexes[j], indexes[j-1],d); j--) {
                exch(j, j-1);
            }
        }
    }

    private boolean less(int i, int j, int d) {
        for (; d < len; d++) {
            int v = charAt(i, d), w = charAt(j, d);
            if (v < w) {
                return true;
            }
            if (v > w) {
                return false;
            }
        }
        return false;
    }

    // exchange indexes[i] and indexes[j]
    private void exch(int i, int j) {
        int temp = indexes[i];
       indexes[i] = indexes[j];
       indexes[j] = temp;
    }


    // If d equals the length of the text, the chars of text must are the same, like "AAAA..."
    // In ths case, we return -1
    private int charAt(int i, int d) {
        return d == len ? -1 : text[(i+d) % len];
    }

    // length of s
    public int length() {
        return len;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= indexes.length) {
            throw new IllegalArgumentException("index to index() is out of bound");
        }
        return indexes[i];
    }

    public static void main(String[] args) {
        CircularSuffixArray array = new CircularSuffixArray("ABRACADABRA!");
        System.out.println(array.length()+ ": " + (array.length() == 12));
        System.out.println(array.index(11) + ": " + (array.index(11) == 2));
    }

}
