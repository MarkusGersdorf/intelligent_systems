package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IField;
import de.uol.is.ludo.IPawn;
import de.uol.is.ludo.Pawn;
import sim.engine.SimState;
import sim.engine.Steppable;

import java.util.ArrayList;

public abstract class Agent implements Steppable {

    protected String name;
    protected IPawn.player color;
    protected String strategy;
    protected IBoard board;
    protected int moves;
    protected int rounds;

    public Agent(String name, IPawn.player color, IBoard board) {
        this.name = name;
        this.color = color;
        this.board = board;
    }

    @Override
    public void step(SimState simState) {
        rounds += 1;
        if (get_remaining_number_of_pawn() == 4) {
            int num = -1;

            for (int i = 0; i < 3; i++) {
                num = board.roll();
                moves += num;
                if (num == 6) {
                    IPawn pawn = get_pawn_from_house();
                    board.set_pawn_into_game(pawn);
                    num = board.roll();
                    moves += num;
                    board.move_pawn(pawn, num);
                    break;
                }
            }
        } else {
            int num;
            do {
                num = board.roll();
                moves += num;
                if (num == 6) {
                    ArrayList<IPawn> pawns = board.get_my_pawns(color);
                    for (IPawn pawn : pawns) {
                        if (pawn.get_field().get_field_type() == IField.field_type.ENTRY) {
                            if(!board.set_pawn_into_game(pawn)) {
                                ArrayList<IPawn> optionList = chooseFigure();
                                for (IPawn option_pawn : optionList) {
                                    if (board.move_pawn(option_pawn, num)) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    ArrayList<IPawn> optionList = chooseFigure();
                    for (IPawn pawn : optionList) {
                        if (board.move_pawn(pawn, num)) {
                            break;
                        }
                    }
                }
            } while (num == 6);
        }
    }

    protected int get_remaining_number_of_pawn() {
        ArrayList<IPawn> pawns = board.get_my_pawns(color);
        int counter_entries = 0;
        for (IPawn pawn : pawns) {
            if (pawn.get_field().get_field_type() == IField.field_type.ENTRY) {
                counter_entries++;
            }
        }
        return counter_entries;
    }

    protected IPawn get_pawn_from_house() {
        ArrayList<IPawn> pawns = board.get_my_pawns(color);
        for (IPawn pawn : pawns) {
            if (pawn.get_field().get_field_id() == -1) {
                return pawn;
            }
        }
        return null;
    }

    protected abstract ArrayList<IPawn> chooseFigure();

    public IPawn.player getPlayerColor() {
        return color;
    }

    public abstract void addToyFigure(Pawn pawn);

    public String get_strategy() {
        return strategy;
    }

    public int get_moves() {
        return moves;
    }

    public int get_rounds() {
        return rounds;
    }
}
