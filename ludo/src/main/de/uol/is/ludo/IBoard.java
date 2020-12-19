package de.uol.is.ludo;

import java.util.ArrayList;

public interface IBoard
{
    int roll();

    boolean move_pawn(Pawn pawn, int steps);

    boolean set_pawn_into_game(Pawn pawn);

    ArrayList<Field> get_field_list();

    int getRemainingNumberOfPawn();
}
