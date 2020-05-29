import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {

    private final int[][] grid;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException("Argument can't be null");
        grid = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, grid[i], 0, tiles.length);
        }
        n = grid.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", grid[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0 && grid[i][j] != (i * n) + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int curN = grid[i][j];
                if (curN != 0) {
                    if (curN <= n) {
                        sum += i;
                        sum += Math.abs(j - (curN - 1));
                    } else if (curN % n == 0) {
                        sum += Math.abs(i - (curN / n - 1));
                        sum += Math.abs(j - (n - 1));
                    } else {
                        sum += Math.abs(i - (curN / n));
                        sum += Math.abs(j - (curN % n - 1));
                    }
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != that.grid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> boards = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    if (i != 0) {
                        boards.add(new Board(exch(i, j, i - 1, j)));
                    }
                    if (i != (n - 1)) {
                        boards.add(new Board(exch(i, j, i + 1, j)));
                    }
                    if (j != 0) {
                        boards.add(new Board(exch(i, j, i, j - 1)));
                    }
                    if (j != (n - 1)) {
                        boards.add(new Board(exch(i, j, i, j + 1)));
                    }
                    break;
                }
            }
        }
        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (grid[i][j] != 0 && grid[i][j + 1] != 0) {
                    return new Board(exch(i, j, i, j + 1));
                }
            }
        }
        return null;
    }

    private int[][] exch(int i, int j, int i1, int j1) {
        int[][] arr = new int[n][n];

        for (int k = 0; k < grid.length; k++) {
            System.arraycopy(grid[k], 0, arr[k], 0, grid.length);
        }

        int tmp = arr[i][j];
        arr[i][j] = arr[i1][j1];
        arr[i1][j1] = tmp;
        return arr;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board newBoard = new Board(tiles);
        StdOut.print(newBoard.hamming()); // 0
        StdOut.print(newBoard.manhattan()); // 0
        StdOut.print(newBoard.dimension()); // 3
        StdOut.print(newBoard.twin());
        StdOut.print(newBoard.isGoal()); // true
        StdOut.print(newBoard.neighbors()); // 2 neighbors
    }
}
