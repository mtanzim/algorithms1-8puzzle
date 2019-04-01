/* *****************************************************************************
 *  Name: Board
 *  Date: Apr 1, 2019
 *  Description: Board class for Puzzle8
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


    //https://kodejava.org/how-do-i-implement-equals-and-hashcode-method-using-java-util-objects/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        Board that = (Board) obj;

        if (that.dimensions() != n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.tiles[i][j] != tiles[i][j]) return false;
            }
        }

        return true;


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

    public Iterable<Board> neighbors() {
        List<Board> nList = new ArrayList<Board>();
        int zero_x = -1;
        int zero_y = -1;
        // int[] zero_pos = { -1, -1 };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zero_x = i;
                    zero_y = j;
                    break;
                }
            }
            if (zero_x + zero_y != -2) break;
        }

        // generate possible swap combinations, max 4
        // pos 0 is x, pos 1 is y
        List<int[]> combos = new ArrayList<int[]>();

        int[] left = { zero_x - 1, zero_y };
        int[] right = { zero_x + 1, zero_y };
        int[] bottom = { zero_x, zero_y - 1 };
        int[] top = { zero_x, zero_y + 1 };

        combos.add(left);
        combos.add(right);
        combos.add(bottom);
        combos.add(top);

        // StdOut.println("Found zero at " + zero_x + ", " + zero_y);
        for (int[] combo : combos) {
            // check for valid entries in the board only
            if (combo[0] < n && combo[1] < n && combo[0] > -1 && combo[1] > -1) {
                // StdOut.println(combo[0] + "," + combo[1]);
                int[][] boardCopy = deepCopyBoard(tiles);
                boardCopy[zero_x][zero_y] = tiles[combo[0]][combo[1]];
                boardCopy[combo[0]][combo[1]] = tiles[zero_x][zero_y];
                nList.add(new Board(boardCopy));
            }
        }

        /*for (Board nBoard : nList) {
            StdOut.println(nBoard.toString());
        }*/


        class Neighbors implements Iterable<Board> {
            @Override
            public Iterator<Board> iterator() {
                return nList.iterator();
            }
        }
        return new Neighbors();
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
            StdOut.println("TEST EQUALS");
            StdOut.println("with twin: " + board.equals(board.twin()));
            assert (!board.equals(board.twin()));
            StdOut.println("with self: " + board.equals(board));
            assert (board.equals(board));
            StdOut.println("with another copy of self: " + board.equals(new Board(tiles)));
            assert (board.equals(new Board(tiles)));
            int temp = tiles[0][0];
            tiles[0][0] = -4;
            assert (!board.equals(new Board(tiles)));
            // change tiles back
            tiles[0][0] = temp;
            StdOut.println("with modified tiles reverted: " + board.equals(new Board(tiles)));
            assert (board.equals(new Board(tiles)));
            StdOut.println("\n\nTESTING NEIGHBORS\n\nORIG");
            StdOut.println(board.toString());
            StdOut.println("NEIGHBORS");
            for (Board neighbor : board.neighbors()) {
                StdOut.println(neighbor.toString());

            }
            // board.neighbors();

            // Solver solver = new Solver(initial);
            // StdOut.println(filename + ": " + solver.moves());
        }
    }
}
