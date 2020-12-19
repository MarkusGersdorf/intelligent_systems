package de.uol.is.ludo;

import de.uol.is.ludo.agents.Agent;
import de.uol.is.ludo.agents.Strategy1;
import sim.engine.*;
import sim.field.grid.ObjectGrid2D;
import java.util.ArrayList;

/**     0 1 2 3 4 5 6 7 8 9 10
 *      red               blue
 * 0  { x x     x x x     x x
 * 1    x x     x - x     x x
 * 2            x - x
 * 3            x - x
 * 4    > x x x x - x x x x x
 * 5    x - - - -   - - - - x
 * 6    x x x x x - x x x x x
 * 7            x - x
 * 8            x - x
 * 9   x x      x - x     x x
 * 10  x x      x x x     x x }
 *     yellow             green
 *
 * No fields in the agent base, but represented by separate list
 *
 * Man, don’t get angry -> MDGA
 */
public class Mdga extends SimState {

    private static int numberOfPlayers = 4;
    public static ArrayList<Agent> players = new ArrayList<Agent>();
    public static String[] playerColors = {"RED", "YELLOW", "BLUE", "GREEN"};
    private static ObjectGrid2D board = new ObjectGrid2D(11, 11);

    // Produce a new SimState with empty Schedule(Zeitplan) and a random number as the given seed.
    public Mdga(long seed) {
        super(seed);
    }

    public static void main(String[] args) {
        SimState mdga = new Mdga(System.currentTimeMillis()); // Create an Instance of the board
        initBoard();
        initAgents();
        mdga.start();
    }

    public void start() {
        super.start();
    }

    private static void initAgents() {

        Field[] startPosistions = {
                (Field) board.get(0, 4),
                (Field) board.get(6, 0),
                (Field) board.get(10, 6),
                (Field) board.get(4, 10),
        };

        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Strategy1("Strategy1", "RED", startPosistions[0], new Board()));
        }
    }

    private static void initBoard()
    {

    }

    /***************************
     ***** Getter & Setter *****
     ***************************/

    public static ObjectGrid2D getBoard() {
        return board;
    }

    public static void setBoard(ObjectGrid2D board) {
        Mdga.board = board;
    }

}
