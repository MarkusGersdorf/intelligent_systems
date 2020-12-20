package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IField;
import de.uol.is.ludo.IPawn;
import sim.engine.SimState;
import sim.engine.Steppable;

import java.util.ArrayList;

/**
 * Every agent or strategy must inherit from this class.
 * n this class the basic logic is implemented, which are necessary for the game Mensch ärgere dich nicht.
 * It is about the fact that with a matching number of dice may be rolled again.
 * Which figure is drawn thereby decides each agent for itself.
 */
public abstract class Agent implements Steppable {

    protected String name;
    protected IPawn.player color;
    protected String strategy;
    protected IBoard board;

    /**
     * @param name  agent name
     * @param color agent color
     * @param board playing field
     */
    public Agent(String name, IPawn.player color, IBoard board) {
        this.name = name;
        this.color = color;
        this.board = board;
    }

    /**
     * This method is called every time it is the agent's turn.
     * All the logic of the agent is contained here.
     * <p>
     * A distinction is made between two states.
     * If all 4 figures are in the start area, then the player may roll the dice 3 times. Otherwise, he may only roll once, unless a 6 is rolled.
     *
     * @param simState Simulation status
     */
    @Override
    public void step(SimState simState) {
        int num;

        if (get_remaining_number_of_pawn() == 4) {
            // If a 6 is rolled, move the figure to the start square. Then try to move the figure again.
            for (int i = 0; i < 3; i++) {
                num = board.roll();
                if (num == 6) {
                    IPawn pawn = get_pawn_from_house();
                    board.set_pawn_into_game(pawn);
                    board.move_pawn(pawn, board.roll());
                    break;
                }
            }
        } else {
            do {
                num = board.roll();
                if (num == 6) {
                    ArrayList<IPawn> pawns = board.get_my_pawns(color);
                    for (IPawn pawn : pawns) {
                        if (pawn.get_field().get_field_type() == IField.field_type.ENTRY) {
                            if (!board.set_pawn_into_game(pawn)) {
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

    /**
     * Count the remaining figures in the starting area
     *
     * @return number of figures
     */
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

    /**
     * Select a figure from the start area
     *
     * @return null if there is no figure in start area
     */
    protected IPawn get_pawn_from_house() {
        ArrayList<IPawn> pawns = board.get_my_pawns(color);
        for (IPawn pawn : pawns) {
            if (pawn.get_field().get_field_id() == -1) {
                return pawn;
            }
        }
        return null;
    }

    /**
     * A list in which the game pieces are returned sorted.
     * The figures are returned sorted by priority.
     * The figure with the highest priority is tried to move first.
     * If this is not possible, the next one is used.
     *
     * @return a list in which the game pieces are returned sorted
     */
    protected abstract ArrayList<IPawn> chooseFigure();

}
