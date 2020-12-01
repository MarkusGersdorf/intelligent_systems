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

    private static void initBoard() {

        int[] xFields = {0, 1, 2, 3, 4, 4, 4, 4, 4, 5, 6, 6, 6, 6, 6, 7, 8, 9, 10, 10, 10, 9, 8, 7, 6, 6, 6, 6, 6, 5, 4, 4, 4, 4, 4, 3, 2, 1, 0, 0};
        int[] yFields = {4, 4, 4, 4, 4, 3, 2, 1, 0, 0, 0, 1, 2, 3, 4, 4, 4, 4, 4, 5, 6, 6, 6, 6, 6, 7, 8, 9, 10, 10, 10, 9, 8, 7, 6, 6, 6, 6, 6, 5};

        // Create all normal fields for the ObjectGrid
        for (int i = 0; i < xFields.length; i++) {

            int[] coordinates = {xFields[i], yFields[i]};

            if (coordinates.equals(new int[]{0, 5})) {

                Field newField = new Field(coordinates, true);
                newField.setIntersectionColor("RED");
                int[] xGatewayFields = {1, 2, 3, 4};
                int[] yGatewayFields = {5, 5, 5, 5};
                for (int j = 0; j < xGatewayFields.length; j++) {
                    newField.addGatewayField(new Field(new int[]{xGatewayFields[j], yGatewayFields[j]}, false));
                }
                board.set(xFields[i], yFields[i], newField);
            } else if (coordinates.equals(new int[]{5, 0})) {

                Field newField = new Field(coordinates, true);
                newField.setIntersectionColor("BLUE");
                int[] xGatewayFields = {5, 5, 5, 5};
                int[] yGatewayFields = {1, 2, 3, 4};
                for (int j = 0; j < xGatewayFields.length; j++) {
                    newField.addGatewayField(new Field(new int[]{xGatewayFields[j], yGatewayFields[j]}, false));
                }
                board.set(xFields[i], yFields[i], newField);
            } else if (coordinates.equals(new int[]{10, 5})) {

                Field newField = new Field(coordinates, true);
                newField.setIntersectionColor("GREEN");
                int[] xGatewayFields = {9, 8, 7, 6};
                int[] yGatewayFields = {5, 5, 5, 5};
                for (int j = 0; j < xGatewayFields.length; j++) {
                    newField.addGatewayField(new Field(new int[]{xGatewayFields[j], yGatewayFields[j]}, false));
                }
                board.set(xFields[i], yFields[i], newField);
            } else if (coordinates.equals(new int[]{5, 10})) {

                Field newField = new Field(coordinates, true);
                newField.setIntersectionColor("YELLOW");
                int[] xGatewayFields = {5, 5, 5, 5};
                int[] yGatewayFields = {9, 8, 7, 6};
                for (int j = 0; j < xGatewayFields.length; j++) {
                    newField.addGatewayField(new Field(new int[]{xGatewayFields[j], yGatewayFields[j]}, false));
                }
                board.set(xFields[i], yFields[i], newField);
            } else {
                Field newField = new Field(coordinates, false);
                board.set(xFields[i], yFields[i], newField);
            }
        }

        for(int i = 0; i < xFields.length -1; i++) {
            Field field = (Field) board.get(xFields[i], yFields[i]);
            field.setFollwField((Field) board.get(xFields[i+1], yFields[i+1]));
        }

        Field field = (Field) board.get(xFields[xFields.length - 1], yFields[xFields.length - 1]);
        field.setFollwField((Field) board.get(xFields[0], yFields[0]));
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
