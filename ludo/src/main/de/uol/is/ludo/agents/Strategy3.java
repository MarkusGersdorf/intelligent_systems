package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IPawn;

import java.util.ArrayList;

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

    protected ArrayList<IPawn> sortPawnsSortedByCriticalStatus(ArrayList<IPawn> pawns) {
        ArrayList<IPawn> resultList = new ArrayList<>();

        for (IPawn pawn : pawns) {
            int startFiled = Math.max(pawn.get_field().get_field_id() - 6, 0);
            int endFiled = Math.min(pawn.get_field().get_field_id() - 6, 56);
            for (int i = startFiled; i < endFiled; i++) {
                if (board.get_fields()[i].get_pawn() != null) {
                    if (board.get_fields()[i].get_pawn() != pawn) {
                        resultList.add(pawn);
                    }
                }

            }
        }

        pawns.removeAll(resultList);
        resultList.addAll(pawns);

        return resultList;
    }

}