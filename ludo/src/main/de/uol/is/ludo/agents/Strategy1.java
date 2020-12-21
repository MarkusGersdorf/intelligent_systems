package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IField;
import de.uol.is.ludo.IPawn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class implements a strategy to play Ludo.
 * Randomly move a piece on the board.
 */
public class Strategy1 extends Agent {

    /**
     * @param name  agent name
     * @param color agent color
     * @param board playing field
     */
    public Strategy1(String name, IPawn.player color, IBoard board) {
        super(name, color, board);
        this.strategy = "Strategy1";
    }

    /**
     * First look for figures that are on the initial positions.
     * These must always be moved first.
     * Otherwise, randomly choose a figure to move.
     *
     * @return a list in which the game pieces are returned sorted
     */
    @Override
    protected ArrayList<IPawn> chooseFigure() {
        ArrayList<IPawn> optionList = new ArrayList<>();
        ArrayList<IPawn> pawns = new ArrayList<>(board.get_my_pawns(color)); // Get a copy of all figures of this player
        pawns.removeIf(x -> x.get_field().get_field_type() == IField.field_type.ENTRY); // delete all figures that are in the entry

        // If a piece is on the start square, move it first.
        for (IPawn pawn : pawns) {
            if (pawn.get_starting_pos() == pawn.get_field().get_field_id()) {
                optionList.add(pawn);
                break;
            }
        }

        pawns.removeIf(x -> x.get_starting_pos() == x.get_field().get_field_id()); // Delete all the pieces that are on the starting field
        Collections.shuffle(pawns, new Random()); // Generate a random ranking
        optionList.addAll(pawns); // Add all other characters to the result, sorted by game progress
        return optionList;
    }
}