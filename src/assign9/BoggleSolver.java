package assign9;

import java.util.HashSet;
import java.util.Set;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

    private static final int A = 65;

    private static final int R = 26;

    private static final int[] DIR = {-1, 0, 1}; 

    private static final char U = 'U';

    private static final char Q = 'Q';

    // root of trie
    private Node root;    

    private boolean[][] marked;

    private BoggleBoard tempBoard;

    private StringBuilder wordBuilder;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null || dictionary.length == 0) {
            throw new IllegalArgumentException("string to BoggleSolver() is null");
        }
        for (int i = 0; i < dictionary.length; i++) {
            addToTrie(dictionary[i]);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException("board to getAllValidWords() is null");
        }
        int maxWordLen = board.cols()*board.rows();
        Set<String> validWords = new HashSet<>(maxWordLen);
        tempBoard = board;
        marked = new boolean[board.rows()][board.cols()];
        wordBuilder = new StringBuilder(maxWordLen);
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                dfs(i, j, root, validWords);
            }
        }
        // avoid memory leak
        tempBoard = null;
        marked = null;
        wordBuilder = null;
        return validWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!isInTrie(word) || word.length() < 3) {
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

    // 26-way trie node
    private static final class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }


    private void dfs(int row, int col, Node parent, Set<String> validWords) {
        char c = tempBoard.getLetter(row, col);
        Node child = nextNode(c, parent);
        if (child == null) {
            return;
        }
        marked[row][col] = true;
        wordBuilder.append(c);
        if (c == Q) {
            wordBuilder.append(U);
        }
        if (wordBuilder.length() >= 3 && child.isString) {
            validWords.add(wordBuilder.toString());
        }
        for (int i = 0; i < DIR.length; i++) {
            for (int j = 0; j < DIR.length; j++) {
                if (i == 1 && j == 1) {
                    continue;
                }
                int newRow = row + DIR[i], newCol = col + DIR[j];
                if (inBoard(newRow, newCol) && !marked[newRow][newCol]) {
                    dfs(newRow, newCol, child, validWords);
                }
            }
        }
        rollBack(c, row, col);
    }

    private Node nextNode(char c, Node parent) {
        Node child = parent.next[nodeIndex(c)];
        if (c == Q && child != null) {
            child = child.next[nodeIndex(U)];
        }
        return child;

    }

    private void rollBack(char c, int row, int col) {
        wordBuilder.deleteCharAt(wordBuilder.length()-1);
        if (c == Q) {
            wordBuilder.deleteCharAt(wordBuilder.length()-1);
        }
        marked[row][col] = false;
    }
    
    private boolean inBoard(int row, int col) {
        return row >= 0 && row < tempBoard.rows() && col >= 0 && col < tempBoard.cols();
    }

    private static int nodeIndex(char c) {
        int index = c - A;
        if (index < 0 || index >= R) {
            throw new IllegalArgumentException("index is overflow");
        }
        return index;
    }

    private void addToTrie(String key) {
        if (key == null) {
            throw new IllegalArgumentException("string to addToTrie() is null");
        }
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            x.isString = true;
        }
        else {
            char c = key.charAt(d);
            int index = nodeIndex(c);
            x.next[index] = add(x.next[index], key, d+1);
        }
        return x;
    }

    private boolean isInTrie(String key) {
        if (key == null) {
            throw new IllegalArgumentException("string to isInTrie() is null");
        }
        Node node = get(root, key, 0);
        return node == null ? false : node.isString;
    }
        
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[nodeIndex(c)], key, d+1);
    }

    public static void main(String[] args) {
        In in = new In("..\\boggle\\dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("..\\boggle\\board-q.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }


}
