package de.uol.is.ludo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface IBoard
{
    int roll();

    boolean move_pawn(IPawn pawn, int steps);

    boolean set_pawn_into_game(IPawn pawn);

    Field[] get_fields();

    Entry get_entry();

    ArrayList<IPawn> get_all_pawns();

    ArrayList<IPawn> get_my_pawns(IPawn.player player);
}
