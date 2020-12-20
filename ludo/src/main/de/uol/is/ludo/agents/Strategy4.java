package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IPawn;

import java.util.ArrayList;

public class Strategy4 extends Agent {

    /**
     * @param name  agent name
     * @param color agent color
     * @param board playing field
     */
    public Strategy4(String name, IPawn.player color, IBoard board) {
        super(name, color, board);
        this.strategy = "Strategy4";
    }

    /**
     * @return a list in which the game pieces are returned sorted
     */
    @Override
    protected ArrayList<IPawn> chooseFigure() {
        return null;
    }

}