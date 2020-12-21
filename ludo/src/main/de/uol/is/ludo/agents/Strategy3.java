package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IPawn;

import java.util.ArrayList;

public class Strategy3 extends Agent {

    /**
     * @param name  agent name
     * @param color agent color
     * @param board playing field
     */
    public Strategy3(String name, IPawn.player color, IBoard board) {
        super(name, color, board);
        this.strategy = "Strategy3";
    }

    /**
     * @return a list in which the game pieces are returned sorted
     */
    @Override
    protected ArrayList<IPawn> chooseFigure(ArrayList<IPawn> pawns) {
        return null;
    }

}