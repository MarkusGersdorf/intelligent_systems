package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IPawn;

import java.util.ArrayList;

public class Strategy4 extends Agent {

    public Strategy4(String name, IPawn.player color, IBoard board) {
        super(name, color, board);
        this.strategy = "Strategy4";
    }

    @Override
    protected ArrayList<IPawn> chooseFigure() {
        return null;
    }

}