import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[] grid;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;
    private final int width;
    private int openSites;
    private final int top;
    private final int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n should be more than 0");
        grid = new boolean[n * n];
        int count = n * n;
        top = n * n;
        bottom = n * n + 1;
        width = n;

        // adds 2 extra chunks to keep track of top and bottom
        uf = new WeightedQuickUnionUF(count + 2);

        // creates another uf object to prevent backwashing
        uf2 = new WeightedQuickUnionUF(count + 1);

        for (int i = 0; i < n * n; i++) {
            grid[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col))
            return;
        if (row == 1) {
            uf.union(xytoID(row, col), top);
            uf2.union(xytoID(row, col), top);
        }
        if (row == width) {
            uf.union(xytoID(row, col), bottom);
        }
        uniteNeighbors(row, col);
        grid[xytoID(row, col)] = true;
        openSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        if (grid[xytoID(row, col)])
            return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf2.find(xytoID(row, col)) == uf2.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    private int xytoID(int x, int y) {
        return width * (x - 1) + y - 1;
    }

    private void validate(int x, int y) {
        if (x > width || y > width || x < 1 || y < 1) {
            throw new IllegalArgumentException("The arguments are outside of the range");
        }
    }

    private void uniteNeighbors(int x, int y) {
        int id = xytoID(x, y);
        int neighborId;
        if (x + 1 <= width && isOpen(x + 1, y)) { // right neighbor
            neighborId = xytoID(x + 1, y);
            uf.union(neighborId, id);
            uf2.union(neighborId, id);
        }
        if (x - 1 >= 1 && isOpen(x - 1, y)) { // left neighbor
            neighborId = xytoID(x - 1, y);
            uf.union(neighborId, id);
            uf2.union(neighborId, id);
        }
        if (y + 1 <= width && isOpen(x, y + 1)) { // top neighbor
            neighborId = xytoID(x, y + 1);
            uf.union(neighborId, id);
            uf2.union(neighborId, id);
        }
        if (y - 1 >= 1 && isOpen(x, y - 1)) { // bottom neighbor
            neighborId = xytoID(x, y - 1);
            uf.union(neighborId, id);
            uf2.union(neighborId, id);
        }
    }
}
