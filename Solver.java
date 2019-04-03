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
        StdOut.println("Starting with the following board: ");
        StdOut.println(initial.toString());

        Board twin = initial.twin();
        StdOut.println("Starting with the following twin board: ");
        StdOut.println(twin.toString());

        class Node {
            private Board parent;
            private Board current;
            private int numMoves;

            public Node(Board argParent, Board argCurrent, int argNumMoves) {
                parent = argParent;
                current = argCurrent;
                numMoves = argNumMoves;
            }

            public String toString() {
                String parentString;
                if (parent == null) {
                    parentString = "NULL\n";
                }
                else {
                    parentString = parent.toString();
                }
                return ("\nparent:\n" + parentString + "\ncurrent:\n" + current.toString()
                        + "\nnumMoves: " + numMoves + "\n");
            }
        }

        StdOut.println("Starting with the following node: ");
        Node firstNode = new Node(null, initial, 0);
        StdOut.println(firstNode.toString());


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
