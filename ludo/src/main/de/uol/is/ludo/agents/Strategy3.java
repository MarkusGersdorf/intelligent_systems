package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IPawn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Strategy3 extends Agent {

    /**
     * @param name  agent name
     * @param color agent color
     * @param board playing field
     */
    public Strategy3(String name, IPawn.player color, IBoard board) {
        super(name, color, board);
        this.strategy = "Strategy3";
    }

    /**
     * @return a list in which the game pieces are returned sorted
     */
    @Override
    protected ArrayList<IPawn> chooseFigure(ArrayList<IPawn> pawns) {
        optionList.addAll(sortPawnsSortedByCriticalStatus(new ArrayList<>(pawns)));
        return optionList;
    }

    /**
     * The pieces that can be captured by an opponent in the next turn should be moved first.
     * If no piece can be captured, then any piece is moved.
     *
     * @param pawns Figures that can be moved
     * @return a list in which the game pieces are returned sorted
     */
    protected ArrayList<IPawn> sortPawnsSortedByCriticalStatus(ArrayList<IPawn> pawns) {
        ArrayList<IPawn> resultList = new ArrayList<>(); // new list to store results

        // for each piece, check if an opponent piece can capture that piece on the next turn.
        for (IPawn pawn : pawns) {
            int startFiled = Math.max(pawn.get_field().get_field_id() - 6, 0);
            int endFiled = Math.min(pawn.get_field().get_field_id() - 6, 56);
            for (int i = startFiled; i < endFiled; i++) {
                if (board.get_fields()[i].get_pawn() != null) {
                    if (board.get_fields()[i].get_pawn() != pawn) {
                        resultList.add(pawn); // this figure can be beaten by an opponent
                    }
                }

            }
        }

        pawns.removeAll(resultList); // Delete game pieces that are already included in the result list
        Collections.shuffle(pawns, new Random()); // Generate a random ranking
        resultList.addAll(pawns); // Add all other characters to the result, sorted by game progress

        return resultList;
    }

}