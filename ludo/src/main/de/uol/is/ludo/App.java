package de.uol.is.ludo;

import java.util.ArrayList;

/**
 * PlayerID
 *              0: player.RED;
 *              1: player.BLUE;
 *              2: player.YELLOW;
 *              3: player.BLACK;
 *
 *  FieldID
 *              -1: Entry
 *              0 - 39: Fields
 *              40 - 43: Goal (RED)
 *              44 - 47: Goal (BLUE)
 *              48 - 51: Goal (YELLOW)
 *              52 - 55: Goal (BLACK)
 */

public class App 
{
    private static final IBoard board = new Board();
    private static final IPawn.player my_color = IPawn.player.BLUE;
    private static final ArrayList<IPawn> my_pawns = board.get_my_pawns(my_color);

    private static IField[] fields = board.get_fields();

    public static void main( String[] args )
    {
        for(IPawn p : my_pawns)
        {
            board.set_pawn_into_game(p);
            for (int i = 0; i < 1000; i++)
            {
                board.move_pawn(p, board.roll());
            }
            System.out.println(p.get_field().get_field_id());
        }
    }
}
