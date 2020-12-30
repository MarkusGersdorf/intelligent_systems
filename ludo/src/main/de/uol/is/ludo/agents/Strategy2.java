package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IPawn;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * This class implements a strategy to play Ludo.
 * The character that is the most advanced is always moved.
 */
public class Strategy2 extends Agent {

    /**
     * @param name  agent name
     * @param color agent color
     * @param board playing field
     */
    public Strategy2(String name, IPawn.player color, IBoard board) {
        super(name, color, board);
        this.strategy = "Strategy2";
    }

    /**
     * First look for figures that are on the initial positions.
     * These must always be moved first.
     * Otherwise, look for the figure that is furthest forward and try to move it first.
     *
     * @return a list in which the game pieces are returned sorted
     */
    @Override
    protected ArrayList<IPawn> chooseFigure(ArrayList<IPawn> pawns) {
        // pawns.sort(Comparator.comparing(IPawn::getFieldId).reversed()); // sort pawns by field id
        pawns.sort(Comparator.comparing(IPawn::getFieldId)); // sort pawns by field id
        optionList.addAll(pawns); // Add all other characters to the result, sorted by game progress
        return optionList;
    }
}