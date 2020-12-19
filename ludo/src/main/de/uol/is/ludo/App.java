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

// TODO: Magic Numbers entfernen / erklären 

public class App 
{
    private static final IBoard board = new Board();
    private static int rolled;
    private static final ArrayList<IPawn> red_pawns = board.get_my_pawns(IPawn.player.RED);
    private static final ArrayList<IPawn> blue_pawns = board.get_my_pawns(IPawn.player.BLUE);
    private static final ArrayList<IPawn> yellow_pawns = board.get_my_pawns(IPawn.player.YELLOW);
    private static final ArrayList<IPawn> black_pawns = board.get_my_pawns(IPawn.player.BLACK);

    public static void main( String[] args )
    {
        for (IPawn p : blue_pawns)
        {
            board.set_pawn_into_game(p);
            for (int i = 0; i < 150; i++)
            {
                board.move_pawn(p, board.roll());
            }
            System.out.println(p.get_field().get_field_id());
        }
    }

    private static IPawn get_pawn_from_entry(ArrayList<IPawn> pawns)
    {
        for (IPawn p : pawns)
        {
            if(p.get_field().get_field_type() == IField.field_type.ENTRY)
            {
                return p;
            }
        }
        return null;
    }
}
