package de.uol.is.ludo.agents;

import de.uol.is.ludo.Board;
import de.uol.is.ludo.Field;
import de.uol.is.ludo.IPawn;
import de.uol.is.ludo.Pawn;
import sim.engine.SimState;
import sim.engine.Steppable;

import java.util.ArrayList;

public abstract class Agent implements Steppable {

    protected String name;
    protected IPawn.player color;
    protected Pawn[] figures;
    protected Field startPos;
    protected String strategy;
    protected Board board;

    public Agent(String name, IPawn.player color, Field startPos, Board board) {
        this.name = name;
        this.color = color;
        this.startPos = startPos;
        this.board = board;
    }

    @Override
    public void step(SimState simState) {

        if (board.getRemainingNumberOfPawn() == 4) {
            int num = -1;

            for (int i = 0; i < 3; i++) {
                num = board.roll();
                if (num == 6) {
                    board.set_pawn_into_game(figures[0]);
                    board.move_pawn(figures[0], board.roll());
                    figures[0] = null;
                    break;
                }
            }
        }

        if (board.getRemainingNumberOfPawn() != 4) {
            int num;
            do {
                num = board.roll();
                ArrayList<Pawn> optionList = chooseFigure();
                for (Pawn pawn : optionList) {
                    if (board.move_pawn(pawn, num)) {
                        break;
                    }
                }
            } while (num == 6);
        }
    }

    protected abstract ArrayList<Pawn> chooseFigure();

    public String getPlayerColor() {
        return "null";
    }

    public abstract void addToyFigure(Pawn pawn);
}
