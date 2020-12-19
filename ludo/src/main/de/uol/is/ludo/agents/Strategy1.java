package de.uol.is.ludo.agents;

import de.uol.is.ludo.Board;
import de.uol.is.ludo.Field;
import de.uol.is.ludo.IPawn;
import de.uol.is.ludo.Pawn;

public class Strategy1 extends Agent {

    public Strategy1(String name, IPawn.player color, Field startPos, Board board) {
        super(name, color, startPos, board);
        this.strategy = "Strategy1";
    }

    @Override
    protected Pawn chooseFigure() {
        return null;
    }

    @Override
    public void addToyFigure(Pawn pawn) {

    }
}