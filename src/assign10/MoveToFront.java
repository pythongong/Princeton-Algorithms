package assign10;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * This class implements the move-to-front encoding that maintains an ordered sequence of the characters 
 * in the alphabet by repeatedly reading a character from the input message; 
 * printing the position in the sequence in which that character appears; 
 * and moving that character to the front of the sequence. 
 */
public class MoveToFront {

    // extended ASCII alphabet size
    private static final int R = 256;  


    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] alphabet = new int[R];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = i;
        }


        // read the input
        int readChar = -1;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write((char) (alphabet[c] & 0xFFFF));
            if (readChar != -1 && c == readChar) {
                continue;
            }
            readChar = c;
            shiftIndex(alphabet, c);
        }

        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] alphabet = new char[R];
        for (char i = 0; i < alphabet.length; i++) {
            alphabet[i] = i;
        }

        int readChar = -1;
        while (!BinaryStdIn.isEmpty()) {
            char index = BinaryStdIn.readChar();
            BinaryStdOut.write(alphabet[index]);
            if (readChar != -1 && alphabet[index] == readChar) {
                continue;
            }
            readChar = alphabet[index];
            shiftChars(alphabet, index);
        }
        BinaryStdOut.close();

    }
    private static void shiftChars(char[] alphabet, int index) {
        char temp = alphabet[index];
        for (int i = index; i > 0; i--) {
            alphabet[i] = alphabet[i-1];
        }
        alphabet[0] = temp;
    }

    private static void shiftIndex(int[] alphabet, char c) {
        int order = alphabet[c];
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] < order) {
                alphabet[i]++; 
            }
        }
        alphabet[c] = 0;
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }

        if (args[0].equals("+")) {
            decode();
        }
    }

}
