package de.uol.is.ludo.agents;

import de.uol.is.ludo.Board;
import de.uol.is.ludo.Field;
import de.uol.is.ludo.Pawn;
import sim.engine.SimState;

public class Strategy2 extends Agent {

    public Strategy2(String name, String color, Field startPos, Board board) {
        super(name, color, startPos, board);
        this.strategy = "Strategy2";
    }

    @Override
    protected Pawn chooseFigure() {
        return null;
    }

    @Override
    public void addToyFigure(Pawn pawn) {

    }

    @Override
    public void step(SimState simState) {

    }
}