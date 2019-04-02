/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private int moves = -1;
    private boolean isSolvable = false;
    private List<Board> bList = new ArrayList<Board>();

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Please supply valid board");
        StdOut.println(initial.toString());
    }

    public int moves() {
        return moves;
    }

    public boolean isSolvable() {
        return isSolvable;
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
            // Solver nullSolver = new Solver(null);
            Solver solver = new Solver(board);
            StdOut.println(solver.moves());
            StdOut.println(solver.isSolvable());
        }

    }
}
