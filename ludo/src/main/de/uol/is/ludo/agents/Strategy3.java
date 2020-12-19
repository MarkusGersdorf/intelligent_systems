package de.uol.is.ludo.agents;

import de.uol.is.ludo.*;
import sim.engine.SimState;

import java.util.ArrayList;

public class Strategy3 extends Agent {

    public Strategy3(String name, IPawn.player color, IBoard board) {
        super(name, color, board);
        this.strategy = "Strategy3";
    }

    @Override
    protected ArrayList<IPawn> chooseFigure() {
        return null;
    }

    @Override
    public void addToyFigure(Pawn pawn) {

    }

    @Override
    public void step(SimState simState) {

    }
}