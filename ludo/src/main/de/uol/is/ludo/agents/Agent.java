package de.uol.is.ludo.agents;

import de.uol.is.ludo.Board;
import de.uol.is.ludo.Field;
import de.uol.is.ludo.Pawn;
import sim.engine.SimState;
import sim.engine.Steppable;

public abstract class Agent implements Steppable {

    protected String name;
    protected String color;
    protected Pawn[] figures;
    protected Field startPos;
    protected String strategy;
    protected Board board;

    public Agent(String name, String color, Field startPos, Board board) {
        this.name = name;
        this.color = color;
        this.startPos = startPos;
        this.board = board;
    }

    @Override
    public void step(SimState simState) {

        if (board.getPosition(this).size() == 0) {
            int num = -1;

            for (int i = 0; i < 3; i++) {
                num = dice();
                if (num == 6) {
                    board.moveFigure(figures[0], startPos);
                    figures[0] = null;
                    break;
                }
            }
        }

        if (board.getPosition(this).size() != 0) {
            int num;
            do {
                num = dice();
                Pawn figure = chooseFigure();
                board.moveFigure(figure, num);
            } while (num == 6);
        }
    }

    protected int dice() {
        return ((int) (Math.random() * (6 - 1))) + 1;
    }

    protected abstract Pawn chooseFigure();

    public String getPlayerColor() {
        return "null";
    }

    public abstract void addToyFigure(Pawn pawn);
}
