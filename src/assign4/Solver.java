package assign4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

// find a solution to the initial board (using the A* algorithm)
public class Solver {

    private Stack<Board> pathBoards;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("board can't be null");
        }
        solve(initial);
    }

    private void solve(Board initial) {
        MinPQ<SearchNode> originPq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();
        Stack<Board> tempBoards = new Stack<>();
        originPq.insert(new SearchNode(initial, tempBoards.size(), null));
        twinPq.insert(new SearchNode(initial.twin(), tempBoards.size(), null));
        while (!originPq.isEmpty() && !twinPq.isEmpty()) {
            SearchNode minNode = originPq.delMin();
            if (minNode.board.isGoal()) {
                pathBoards = new Stack<>();
                while (minNode != null) {
                    pathBoards.push(minNode.board);
                    minNode = minNode.previous;
                }
                return;
            }
            addNeighbors(originPq, minNode);
            SearchNode twinMin = twinPq.delMin();
            if (twinMin.board.isGoal()) {
                return;
            }
            addNeighbors(twinPq, twinMin);
        }
    }


    private void addNeighbors(MinPQ<SearchNode> minPQ, SearchNode minNode) {
        Iterable<Board> neighbors = minNode.board.neighbors();
        int moves = minNode.moves + 1;
        SearchNode previousNode = minNode.previous;
        for (Board board : neighbors) {
            if (previousNode != null && board.equals(previousNode.board)) {
                continue;
            }
            minPQ.insert(new SearchNode(board, moves, minNode));
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return pathBoards != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (pathBoards == null) {
            return -1;
        }
        return pathBoards.size()-1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return pathBoards;
    }

   

    private static final class SearchNode implements Comparable<SearchNode> {

        private Board board;

        private int moves;

        private SearchNode previous;

        private int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode node) {
            int priority1 = this.priority;
            int priority2 = node.priority;
            if (priority1 < priority2) {
                return -1;
            }
            if (priority1 > priority2) {
                return 1;
            }
            return 0;
        }
        
    }



    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
