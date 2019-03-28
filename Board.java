/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

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
        String retVal = n + "\n ";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                retVal += tiles[i][j] + " ";
            }
            retVal += "\n ";
        }

        return retVal;
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
            // Solver solver = new Solver(initial);
            // StdOut.println(filename + ": " + solver.moves());
        }
    }
}
