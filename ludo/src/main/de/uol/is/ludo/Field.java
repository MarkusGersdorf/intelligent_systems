package de.uol.is.ludo;

import java.util.ArrayList;

/**
 * Represent the fields of the board
 */
public class Field {

    private final int[] coordinates;
    private ArrayList<Agent> agentsOnTheField;
    private Field follwField = null;
    private boolean intersection;
    private String intersectionColor;
    private ArrayList<Field> gatewayFields;

    public Field(int[] coordinates, boolean intersection) {
        this.coordinates = coordinates;
        this.intersection = intersection;
    }

    /***************************
     ***** Getter & Setter *****
     ***************************/

    public void setFollwField(Field follwField) {
        this.follwField = follwField;
    }

    public ArrayList<Agent> getAgentsOnTheField() {
        return agentsOnTheField;
    }

    public Field getFollwField() {
        return follwField;
    }

    public void addGatewayField(int x, int y) {

    }

    public void addGatewayField(Field field) {
        gatewayFields.add(field);
    }

    public void setIntersectionColor(String color) {
        intersectionColor = color;
    }
}