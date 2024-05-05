package assign9;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

public class BoggleSolver {

    private static final int[] DIR = {-1, 0, 1}; 

    private final TST<Integer> words;

    private boolean[][] marked;

    private Set<String> validWords;

    private int boardWidth;

    private int boardHeight;

    private Map<String, Boolean> prefixMap;


    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        words = new TST<>();
        for (int i = 0; i < dictionary.length; i++) {
            words.put(dictionary[i], i);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException("board is null");
        }
        boardWidth = board.cols();
        boardHeight = board.rows();
        validWords = new HashSet<>(boardHeight*boardWidth);
        prefixMap = new HashMap<>(boardHeight*boardWidth);
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                StringBuilder word = new StringBuilder(boardHeight*boardWidth);
                marked = new boolean[boardHeight][boardWidth];
                dfs(i, j, board, word);
            }
        }
        return validWords;
    }

    private void dfs(int row, int col, BoggleBoard board, StringBuilder word) {
        marked[row][col] = true;
        char c = board.getLetter(row, col);
        word.append(c);
        if (c == 'Q') {
            word.append('U');
        }
        String wordVal = word.toString();
        if (isInvalid(wordVal)) {
            rollBack(word, c, row, col);
            return;
        }

        if (word.length() >= 3 && words.contains(wordVal)) {
            validWords.add(wordVal);
        }
        
        for (int i = 0; i < DIR.length; i++) {
            for (int j = 0; j < DIR.length; j++) {
                int newRow = row + DIR[i], newCol = col + DIR[j];
                if (inBoard(newRow, newCol) && !marked[newRow][newCol]) {
                    dfs(newRow, newCol, board, word);
                }
            }
        }
        rollBack(word, c, row, col);
    }

    
    private void rollBack(StringBuilder word, char c, int row, int col) {
        word.deleteCharAt(word.length()-1);
        if (c == 'Q') {
            word.deleteCharAt(word.length()-1);
        }
        marked[row][col] = false;
    }

    private boolean isInvalid(String word) {
        Boolean isInvalid = prefixMap.get(word);
        if (isInvalid != null) {
            return isInvalid;
        }
        Iterable<String> keysWithPrefix = words.keysWithPrefix(word);
        for (String key : keysWithPrefix) {
            if (key != null) {
                prefixMap.put(word, Boolean.FALSE);
                return false;
            }
        }
        prefixMap.put(word, Boolean.TRUE);
        return true;
    }

    private boolean inBoard(int row, int col) {
        return row >= 0 && row < boardHeight && col >= 0 && col < boardWidth;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!words.contains(word) || word.length() < 3) {
            return 0;
        }
        int len = word.length();

        if (len == 3 || len == 4) {
            return 1;
        }
        switch (len) {
            case 5: return 2;

            case 6: return 3;

            case 7: return 5;

            default: return 11;
        }

    }

    public static void main(String[] args) {
        In in = new In("..\\boggle\\dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("..\\boggle\\board-estrangers.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }


}
