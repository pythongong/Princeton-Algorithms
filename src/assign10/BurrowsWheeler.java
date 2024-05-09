package assign10;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    // extended ASCII alphabet size
    private static final int R = 256;  
    
     // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        String s = BinaryStdIn.readString();

        CircularSuffixArray suffixArray = new CircularSuffixArray(s);
        char[] t = new char[suffixArray.length()];
        int first = -1;
        
        for (int i = 0; i < t.length; i++) {
            int index = suffixArray.index(i) - 1;
            t[i] = s.charAt(index == -1 ? s.length() - 1 : index);
            if (index == -1) {
                first = i;
            }
        }
        BinaryStdOut.write(first);
        for (char i : t) {
            BinaryStdOut.write(i);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        if (BinaryStdIn.isEmpty()) {
            throw new IllegalArgumentException("input to inverseTransform() is empty");
        }

        int first = BinaryStdIn.readInt();
        char[] suffixes = BinaryStdIn.readString().toCharArray();
        int len = suffixes.length;
        int[] next = new int[len];
        int[] count = new int[R+1];

        // Use key-index counting to get next[]
        for (int i = 0; i < len; i++) {
            count[suffixes[i] + 1]++;
        }

        for (int i = 0; i < R; i++) {
            count[i+1] += count[i];
        }

        for (int i = 0; i < len; i++) {
            next[count[suffixes[i]]++] = i;
        }

        // Output the origin string.
        for (int i = 0; i < len; first = next[first], i++) {
            BinaryStdOut.write(suffixes[next[first]]);
        }
        
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }

        if (args[0].equals("+")) {
            inverseTransform();
        }
    }
}
