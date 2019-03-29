/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

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

    // @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("SBSC_USE_STRINGBUFFER_CONCATENATION")
    public String toString() {
        StringBuilder s = new StringBuilder(n + "\n ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(tiles[i][j]);
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
                // StdOut.println(
                //         "Testing i: " + i + " j: " + j + " value: " + tiles[i][j] + " k: " + k);
                if (tiles[i][j] != k) return false;
                k++;
            }
        }
        return true;
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
            // Solver solver = new Solver(initial);
            // StdOut.println(filename + ": " + solver.moves());
        }
    }
}
