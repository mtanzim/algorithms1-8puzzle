/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private int minMoves = -1;
    private boolean isSolvable = false;
    // private List<Board> bList = new ArrayList<Board>();

    public Solver(Board initial) {

        class Node {
            private Board parent;
            private Board current;
            private int numMoves;
            private int priority;

            public Node(Board argParent, Board argCurrent, int argNumMoves) {
                parent = argParent;
                current = argCurrent;
                numMoves = argNumMoves;
                priority = current.manhattan() + numMoves;
            }

            public int getPriority() {
                return priority;
            }

            public Board getCurrent() {
                return current;
            }

            public Board getParent() {
                return parent;
            }

            public int getNumMoves() {
                return numMoves;
            }

            public String toString(boolean isDebug) {
                String parentString;
                // boolean isDebug = false;

                if (parent == null) {
                    parentString = "NULL\n";
                }
                else {
                    parentString = parent.toString();
                }
                if (isDebug)
                    return ("\nparent:\n" + parentString + "\ncurrent:\n" + current.toString()
                            + "\nnumMoves: " + numMoves + "\n");
                else return ("priority = " + priority + "\nmoves = " + numMoves + "\nmanhattan = "
                        + current.manhattan() + "\n" + current.toString());
            }


        }


        // public Comparator<Node> nodeOrder () {
        class NodeComparator implements Comparator<Node> {
            public int compare(Node A, Node B) {
                // StdOut.println("Comparing Nodes:");
                int priorityA = A.getPriority();
                int priorityB = B.getPriority();
                // StdOut.println("A: " + priorityA);
                // StdOut.println(A.toString(false));
                // StdOut.println("B: " + priorityB);
                // StdOut.println(B.toString(false));
                if (priorityA < priorityB) {
                    // StdOut.println(" A IS SMALLER");
                    return -1;
                }
                if (priorityA == priorityB) {
                    // StdOut.println("They are equal!");
                    return 0;
                }
                else {
                    // StdOut.println(" A IS LARGER ");
                    return 1;
                }


            }
        }
        // return new byPriority();
        // }


        if (initial == null) throw new IllegalArgumentException("Please supply valid board");

        Node firstNode = new Node(null, initial, 0);
        // Node firstTwin = new Node(null, initial.twin(), 0);
        // StdOut.println(
        //         "Starting with the following node with priority : " + firstNode.getPriority());
        // StdOut.println(firstNode.toString(false));
        // StdOut.println(
        //         "Starting with the following twin with priority : " + firstTwin.getPriority());
        // StdOut.println(firstTwin.toString(true));


        MinPQ<Node> gamePQ = new MinPQ(new NodeComparator());
        gamePQ.insert(firstNode);
        Node curDeq = gamePQ.delMin();
        // Node curNode = firstNode;
        // moves = 0;
        int curMove = curDeq.getNumMoves();
        while (!curDeq.getCurrent().isGoal()) {
            if (curMove > 4000) break;
            // StdOut.println("PQ Size: " + gamePQ.size());
            // StdOut.println("XXXXXXXXXX Currently processing in numMoves: " + moves + " XXXXXXXXXX");
            // StdOut.println(curDeq.toString(false));
            int i = 0;
            // StdOut.println("===============NEIGHBORS====================");
            for (Board neighbor : curDeq.getCurrent().neighbors()) {
                if (neighbor.equals(curDeq.getParent())) continue;
                Node curChildNode = new Node(curDeq.getCurrent(), neighbor, curMove + 1);
                // StdOut.println("Neighbor: " + i++);
                // StdOut.println(curChildNode.toString(false));
                gamePQ.insert(curChildNode);
            }
            // StdOut.println("===============NEIGHBORS END=================");
            // StdOut.println("PQ Size: " + gamePQ.size());
            // moves++;
            // StdOut.println("===============CURRENT PQ MIN=================");
            // StdOut.println(gamePQ.min().toString(false));
            curDeq = gamePQ.delMin();
            curMove = curDeq.getNumMoves();
            // StdOut.println("===========================================");


        }
        StdOut.println("SOLVED GAME\n" + curDeq.toString(false));
        minMoves = curDeq.getNumMoves();

    }

    public int moves() {
        return minMoves;
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
