package de.uol.is.ludo.agents;

import de.uol.is.ludo.Board;
import de.uol.is.ludo.Field;
import de.uol.is.ludo.ToyFigure;
import sim.engine.Steppable;

public abstract class Agent implements Steppable {

    protected String name;
    protected String color;
    protected ToyFigure[] figures;
    protected Field startPos;
    protected String strategy;
    protected Board board;

    public Agent(String name, String color, Field startPos, Board board) {
        this.name = name;
        this.color = color;
        this.startPos = startPos;
        this.board = board;
    }

    protected int dice() {
        return ((int) (Math.random()*(6 - 1))) + 1;
    }

    protected abstract ToyFigure chooseFigure();
}
