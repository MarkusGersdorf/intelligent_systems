package de.uol.is.ludo;


import de.uol.is.ludo.agents.Agent;

import java.util.ArrayList;


public class Board implements IBoard {

    private Die die = new Die();
    private ArrayList<Field> field_list = new ArrayList<>();
    private Field[] fields = new Field[40];
    private Entry entry = new Entry();
    private Goal[][] goal = new Goal[4][4];
    private Pawn[] pawns = new Pawn[16];

    @Override
    public int roll() {
        return 0;
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