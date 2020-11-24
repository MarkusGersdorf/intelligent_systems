package de.uol.is.ludo;

import sim.engine.SimState;
import sim.engine.Steppable;

import java.util.ArrayList;

public class Agent implements Steppable {

    private String playerColor;
    private Field startPos;
    private ArrayList<ToyFigure> toyFigureList = new ArrayList<ToyFigure>();

    public Agent(String playerColor, Field startPos) {
        this.playerColor = playerColor;
        this.startPos = startPos;
        for(int i = 0; i < 4; i++) {
            toyFigureList.add(new ToyFigure(playerColor, i));
        }
    }

    @Override
    public void step(SimState simState) {

        // If the agent has no pieces on the field, he can roll 3 times. If he throws a 6, a piece is placed on the
        // starting position and may then roll again to move.
        if (toyFigureList.size() < 4) {

            boolean outlet = false;

            for(int i = 0; i < 3; i++) {
                if(dice() == 6) {
                    ToyFigure first = toyFigureList.get(0);
                    toyFigureList.remove(first);
                    outlet = true;
                    Field field = (Field) Mdga.getBoard().get(startPos.getCoordinates()[0], startPos.getCoordinates()[1]);
                    if(field.getToyFiguresOnTheField().size() > 0) {
                        field.kick();
                        field.setAgentsOnTheField(first);
                    };
                    break;
                }
            }

            if(outlet) {
                int diceNumber = dice();
                for(int i = 0; i < diceNumber; i++) {
                    goThrough();
                }
            }

        } else {

        }
    }

    public void goThrough() {
        
    }

    /**
     * Generate a random number between 1 and 6. Represent a game dice.
     *
     * @return A Integer between 1 and 6.
     */
    public static int dice(){
        return ((int) (Math.random()*(6 - 1))) + 1;
    }

    /***************************
     ***** Getter & Setter *****
     ***************************/

    public String getPlayerColor() {
        return playerColor;
    }

    public void addToyFigure(ToyFigure toyFigure) {
        toyFigureList.add(toyFigure);
    }
}