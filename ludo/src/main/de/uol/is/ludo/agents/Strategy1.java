package de.uol.is.ludo.agents;

import de.uol.is.ludo.Board;
import de.uol.is.ludo.Field;
import de.uol.is.ludo.ToyFigure;

import java.util.ArrayList;

public class Strategy1 extends Agent {

    public Strategy1(String name, String color, Field startPos, Board board) {
        super(name, color, startPos, board);
        this.strategy = "Strategy1";
    }

    @Override
    protected ToyFigure chooseFigure() {
        return null;
    }

    @Override
    public void addToyFigure(ToyFigure toyFigure) {

    }
}