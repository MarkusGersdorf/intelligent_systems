package de.uol.is.ludo;

import sim.engine.*;
import de.uol.is.ludo.IBoard;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * RULES
 * PlayerID
 *             case 0:
 *                 return player.RED;
 *             case 1:
 *                 return player.BLUE;
 *             case 2:
 *                 return player.YELLOW;
 *             case 3:
 *                 return player.BLACK;
 */

public class App 
{
    private static IBoard board = new Board();
    private static Field[] fields;
    private static Entry entry = new Entry();
    private static ArrayList<IPawn> all_pawns = new ArrayList<>();
    private static ArrayList<IPawn> my_pawns = new ArrayList<>();
    private static IPawn selected_pawn;
    private static IPawn.player my_color = IPawn.player.YELLOW;

    public static void main( String[] args )
    {
        my_pawns = board.get_my_pawns(my_color);

        board.set_pawn_into_game(my_pawns.get(0));

        for (int i = 0; i < 100; i++)
        {
            System.out.println(my_pawns.get(0).get_field().get_field_id());
            board.move_pawn(my_pawns.get(0), board.roll());
        }
    }
}
