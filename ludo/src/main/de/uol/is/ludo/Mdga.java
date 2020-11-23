package de.uol.is.ludo;

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

    private static int number_of_players = 4;
    private static ArrayList<Agent> players = new ArrayList<Agent>();
    public static String[] player_colors = {"RED", "YELLOW", "BLUE", "GREEN"};
    private static ObjectGrid2D board = new ObjectGrid2D(11, 11);

    // Produce a new SimState with empty Schedule(Zeitplan) and a random number as the given seed.
    public Mdga(long seed) {
        super(seed);
    }

    public static void main(String[] args) {
        SimState mdga = new Mdga(System.currentTimeMillis()); // Create an Instance of the board
        init_board();
        init_agents();
        mdga.start();
    }

    private static void init_agents() {

        Field[] start_posistions = {
                (Field) board.get(0, 4),
                (Field) board.get(6, 0),
                (Field) board.get(10, 6),
                (Field) board.get(4, 10),
        };

        for (int i = 0; i < number_of_players; i++) {
            players.add(new Agent(player_colors[i], start_posistions[i]));
        }
    }

    public void start() {
        super.start();
    }

    private static void init_board() {

        int[] x_fields = {0, 1, 2, 3, 4, 4, 4, 4, 4, 5, 6, 6, 6, 6, 6, 7, 8, 9, 10, 10, 10, 9, 8, 7, 6, 6, 6, 6, 6, 5, 4, 4, 4, 4, 4, 3, 2, 1, 0, 0};
        int[] y_fields = {4, 4, 4, 4, 4, 3, 2, 1, 0, 0, 0, 1, 2, 3, 4, 4, 4, 4, 4, 5, 6, 6, 6, 6, 6, 7, 8, 9, 10, 10, 10, 9, 8, 7, 6, 6, 6, 6, 6, 5};

        // Create all normal fields for the ObjectGrid
        for (int i = 0; i < x_fields.length; i++) {

            int[] coordinates = {x_fields[i], y_fields[i]};

            if (coordinates.equals(new int[]{0, 5})) {

                Field new_field = new Field(coordinates, true);
                new_field.setIntersectionColor("RED");
                int[] x_gateway_fields = {1, 2, 3, 4};
                int[] y_gateway_fields = {5, 5, 5, 5};
                for (int j = 0; j < x_gateway_fields.length; j++) {
                    new_field.addGatewayField(new Field(new int[]{x_gateway_fields[j], y_gateway_fields[j]}, false));
                }
                board.set(x_fields[i], y_fields[i], new_field);
            } else if (coordinates.equals(new int[]{5, 0})) {

                Field new_field = new Field(coordinates, true);
                new_field.setIntersectionColor("BLUE");
                int[] x_gateway_fields = {5, 5, 5, 5};
                int[] y_gateway_fields = {1, 2, 3, 4};
                for (int j = 0; j < x_gateway_fields.length; j++) {
                    new_field.addGatewayField(new Field(new int[]{x_gateway_fields[j], y_gateway_fields[j]}, false));
                }
                board.set(x_fields[i], y_fields[i], new_field);
            } else if (coordinates.equals(new int[]{10, 5})) {

                Field new_field = new Field(coordinates, true);
                new_field.setIntersectionColor("GREEN");
                int[] x_gateway_fields = {9, 8, 7, 6};
                int[] y_gateway_fields = {5, 5, 5, 5};
                for (int j = 0; j < x_gateway_fields.length; j++) {
                    new_field.addGatewayField(new Field(new int[]{x_gateway_fields[j], y_gateway_fields[j]}, false));
                }
                board.set(x_fields[i], y_fields[i], new_field);
            } else if (coordinates.equals(new int[]{5, 10})) {

                Field new_field = new Field(coordinates, true);
                new_field.setIntersectionColor("YELLOW");
                int[] x_gateway_fields = {5, 5, 5, 5};
                int[] y_gateway_fields = {9, 8, 7, 6};
                for (int j = 0; j < x_gateway_fields.length; j++) {
                    new_field.addGatewayField(new Field(new int[]{x_gateway_fields[j], y_gateway_fields[j]}, false));
                }
                board.set(x_fields[i], y_fields[i], new_field);
            } else {
                Field new_field = new Field(coordinates, false);
            }
        }

        for(int i = 0; i < x_fields.length -1; i++) {
            Field field = (Field) board.get(x_fields[i], y_fields[i]);
            field.setFollw_field((Field) board.get(x_fields[i+1], y_fields[i+1]));
        }

        Field field = (Field) board.get(x_fields[x_fields.length - 1], y_fields[x_fields.length - 1]);
        field.setFollw_field((Field) board.get(x_fields[0], y_fields[0]));
    }
}
