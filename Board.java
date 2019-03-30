/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("UUF_UNUSED_FIELD")
// @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("UUF_UNUSED_FIELD")
public class Board {

    private int[][] tiles;
    private int[][] goalBoard;
    private int n;

    public Board(int[][] blocks) {
        tiles = blocks.clone();
        n = tiles.length;
    }

    public int dimensions() {
        return n;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(n + "\n ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
                s.append(" ");
            }
            s.append("\n ");
        }
        return s.toString();
    }

    public boolean isGoal() {
        int k = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (k == n * n) k = 0;
                if (tiles[i][j] != k) return false;
                k++;
            }
        }
        return true;
    }

    public Board twin() {

    }

    public int hamming() {
        int hamming = 0;
        int k = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // when you reached the last tile
                if (k == n * n) return hamming;
                if (tiles[i][j] != k) hamming++;
                k++;
            }
        }
        return hamming;

    }


    public static void main(String[] args) {
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            // solve the slider puzzle
            Board board = new Board(tiles);
            // StdOut.println(board.dimensions());
            StdOut.println(board.toString());
            StdOut.println(board.isGoal());
            StdOut.println(board.hamming());
            // Solver solver = new Solver(initial);
            // StdOut.println(filename + ": " + solver.moves());
        }
    }
}
