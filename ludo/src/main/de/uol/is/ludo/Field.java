package de.uol.is.ludo;

import java.util.ArrayList;

/**
 * Represent the fields of the board
 */
public class Field {

    private final int[] coordinates;
    private ArrayList<ToyFigure> toyFiguresOnTheField;
    private Field follwField = null;
    private boolean intersection;
    private String intersectionColor;
    private ArrayList<Field> gatewayFields;
    private boolean startPosition;

    public Field(int[] coordinates, boolean intersection) {
        this.coordinates = coordinates;
        this.intersection = intersection;
    }

    public void kick() {
        for(int i = 0; i < toyFiguresOnTheField.size(); i++) {
            ToyFigure toyFigure = toyFiguresOnTheField.get(i);
            toyFiguresOnTheField.remove(toyFigure);
            for(int j = 0; j < 4; j++) {
                Agent agent = Mdga.players.get(j);
                if(agent.getPlayerColor() == toyFigure.getColor()) {
                    agent.addToyFigure(toyFigure);
                }
            }
        }
    }

    public void setAgentsOnTheField(ToyFigure toyFigure) {
        toyFiguresOnTheField.add(toyFigure);
    }

    /***************************
     ***** Getter & Setter *****
     ***************************/

    public void setFollwField(Field follwField) {
        this.follwField = follwField;
    }

    public ArrayList<ToyFigure> getToyFiguresOnTheField() {
        return toyFiguresOnTheField;
    }

    public Field getFollwField() {
        return follwField;
    }

    public void addGatewayField(Field field) {
        gatewayFields.add(field);
    }

    public void setIntersectionColor(String color) {
        intersectionColor = color;
    }

    public int[] getCoordinates() {
        return coordinates;
    }
}