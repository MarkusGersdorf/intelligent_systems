package de.uol.is.ludo;


import de.uol.is.ludo.agents.Agent;

import java.util.ArrayList;
import java.util.Collections;


public class Board implements IBoard
{
    private final static Logic logic = new Logic();
    private final Die die = new Die();
    private final ArrayList<Field> field_list = new ArrayList<>();
    private final Field[] fields = new Field[40];
    private final Entry entry = new Entry();
    private final Goal[][] goal = new Goal[4][4];
    private final Pawn[] pawns = new Pawn[16];

    public Board()
    {
        initialize_board();
    }

    private void initialize_board()
    {
        Collections.addAll(field_list, fields);
        for (Pawn pawn : pawns)
        {
            // Set every pawn on entry
            entry.set_pawn(pawn);
        }
    }

    @Override
    public int roll()
    {
        return die.roll();
    }

    @Override
    public boolean move_pawn(Pawn pawn, int steps) {
        return false;
    }

    @Override
    public boolean set_pawn_into_game(Pawn pawn) {
        return false;
    }

    @Override
    public ArrayList<Field> get_field_list() {
        return null;
    }
}