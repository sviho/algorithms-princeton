import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {

    private int moves;
    private final boolean solved;
    private SearchNode solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Argument can't be null");

        MinPQ<SearchNode> priorityQueue = new MinPQ<>();
        priorityQueue.insert(new SearchNode(initial, 0, null));

        SearchNode curNode;
        Board curBoard;

        while (true) {
            curNode = priorityQueue.delMin();
            curBoard = curNode.getBoard();
            moves = curNode.getMoves();

            if (curNode.getManhattan() == 2 && curBoard.twin().isGoal()) {
                solved = false;
                break;
            }

            if (curBoard.isGoal()) {
                solved = true;
                solution = curNode;
                break;
            }

            Iterable<Board> neighbors = curBoard.neighbors();

            for (Board b : neighbors) {
                if (curNode.getPrev() == null || !b.equals(curNode.getPrev().getBoard())) {
                    priorityQueue.insert(new SearchNode(b, moves + 1, curNode));
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solved;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (!solved) return -1;
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!solved) return null;
        ArrayList<Board> steps = new ArrayList<>();
        SearchNode node = solution;
        while (node != null) {
            steps.add(0, node.getBoard());
            node = node.getPrev();
        }
        return steps;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board cur;
        private final int moves;
        private final SearchNode prev;
        private final int manhattan;

        public SearchNode(Board cur, int moves, SearchNode prev) {
            this.cur = cur;
            this.moves = moves;
            this.prev = prev;
            manhattan = cur.manhattan();
        }

        @Override
        public int compareTo(SearchNode node) {
            return this.priority() - node.priority();
        }

        private int priority() {
            return manhattan + moves;
        }

        public Board getBoard() {
            return cur;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPrev() {
            return prev;
        }

        public int getManhattan() {
            return manhattan;
        }
    }

    // test client
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
