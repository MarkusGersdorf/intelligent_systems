package de.uol.is.ludo.agents;

import de.uol.is.ludo.IBoard;
import de.uol.is.ludo.IField;
import de.uol.is.ludo.IPawn;

import java.util.ArrayList;

public class Strategy1 extends Agent {

    public Strategy1(String name, IPawn.player color, IBoard board) {
        super(name, color, board);
        this.strategy = "Strategy1";
    }

    @Override
    protected ArrayList<IPawn> chooseFigure() {
        ArrayList<IPawn> optionList = new ArrayList<>();
        ArrayList<IPawn> pawns = new ArrayList<>(board.get_my_pawns(color));
        pawns.removeIf(x->x.get_field().get_field_type() == IField.field_type.ENTRY);

        for(IPawn pawn : pawns) {
            if(pawn.get_starting_pos() == pawn.get_field().get_field_id()) {
                optionList.add(pawn);
                break;
            }
        }

        pawns.removeIf(x->x.get_starting_pos() == x.get_field().get_field_id());

        optionList.addAll(pawns);

        return optionList;
    }
}