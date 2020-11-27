package de.uol.is.ludo.agents;

import de.uol.is.ludo.Board;
import de.uol.is.ludo.Field;
import de.uol.is.ludo.ToyFigure;
import sim.engine.SimState;

public class Strategy1 extends Agent {

    public Strategy1(String name, String color, Field startPos, Board board) {
        super(name, color, startPos, board);
        this.strategy = "Strategy1";
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
                ToyFigure figure = chooseFigure();
                board.moveFigure(figure, num);
            } while (num == 6);
        }
    }

    @Override
    protected ToyFigure chooseFigure() {

        return null;
    }
}