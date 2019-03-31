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
        tiles = deepCopyBoard(blocks);
        n = tiles.length;
    }

    // shallow copy on multidimensional arrays with clone
    // thus each subarray needs its own clone
    // https://stackoverflow.com/questions/184710/what-is-the-difference-between-a-deep-copy-and-a-shallow-copy
    private int[][] deepCopyBoard(int[][] oldBoard) {
        int[][] newBoard = new int[oldBoard.length][];
        for (int i = 0; i < oldBoard.length; i++) {
            newBoard[i] = oldBoard[i].clone();
        }
        return newBoard;
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

        int x1 = -1;
        int y1 = -1;
        int x2 = -1;
        int y2 = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (x1 == -1 && y1 == -1) {
                    x1 = i;
                    y1 = j;
                    // StdOut.println("a is " + tiles[i][j] + " at: " + x1 + " " + y1);
                    continue;
                }
                x2 = i;
                y2 = j;
                // StdOut.println("b is " + tiles[i][j] + " at: " + x2 + " " + y2);
                break;
            }
            if (x2 > -1 && y2 > -1) break;
        }

        int[][] twin = deepCopyBoard(tiles);
        twin[x1][y1] = tiles[x2][y2]; // b
        twin[x2][y2] = tiles[x1][y1]; // a
        return new Board(twin);


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
            StdOut.println("TWIN");
            StdOut.println(board.twin().toString());
            StdOut.println("ORIG");
            StdOut.println(board.toString());
            // Solver solver = new Solver(initial);
            // StdOut.println(filename + ": " + solver.moves());
        }
    }
}
