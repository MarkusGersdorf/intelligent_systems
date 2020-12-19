package de.uol.is.ludo.agents;

import de.uol.is.ludo.Board;
import de.uol.is.ludo.Field;
import de.uol.is.ludo.IPawn;
import de.uol.is.ludo.Pawn;

import java.util.ArrayList;

public class Strategy1 extends Agent {

    public Strategy1(String name, IPawn.player color, Field startPos, Board board) {
        super(name, color, startPos, board);
        this.strategy = "Strategy1";
    }

    @Override
    protected ArrayList<Pawn> chooseFigure() {
        ArrayList<Pawn> optionList = new ArrayList<>();
        ArrayList<Field> fields = board.get_field_list();
        for(Field field : fields) {
            if(field.get_pawn() != null) {
                if(String.valueOf(field.get_pawn().get_player()).equals(color)) {
                    optionList.add(field.get_pawn());
                }
            }
        }
        return optionList;
    }

    @Override
    public void addToyFigure(Pawn pawn) {

    }
}