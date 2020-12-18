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

// TODO: Check, wenn 6 gewürfelt, dann Figur ins Spielfeld (falls Figur im Entry vorhanden)
// TODO: 3er Regel
// TODO: Wenn Spielzug "false", muss der Agent was anderes machen können
// TODO: Warum Out of Array? -> Recherche

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
