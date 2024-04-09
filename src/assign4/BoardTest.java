package assign4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BoardTest {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            int n =  Integer.parseInt(reader.readLine());
            int[][] tiles = new int[n][n];
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                // Process each line of the file
                if (line.isEmpty()) {
                    continue;
                }
                line = line.trim();
                String[] split = line.split("\\s+");
                for (int j = 0; j < split.length; j++) {
                    tiles[i][j] = Integer.parseInt(split[j]);
                }
                i++;
            }
            testBoard(tiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testBoard(int[][] tiles) {
        Board board = new Board(tiles);
        System.out.println(board.toString());
        System.out.println("isGoal: " + board.isGoal());
        System.out.println("hamming: " +  board.hamming());
        System.out.println("manhattan: "+board.manhattan());
        Iterable<Board> neighbors = board.neighbors();
        for (Board neighBoard : neighbors) {
            System.out.println("neighbour: "+ neighBoard.toString());
        }
        System.out.println("twin:"+ board.twin().toString());
    }
}
