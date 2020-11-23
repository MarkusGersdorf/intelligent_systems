package de.uol.is.ludo;

import java.util.ArrayList;

/**
 * Represent the fields of the board
 */
public class Field {

    private final int[] coordinates;
    private ArrayList<Agent> agents_on_the_field;
    private Field follw_field = null;
    private boolean intersection;
    private String intersection_color;
    private ArrayList<Field> gateway_fields;

    public Field(int[] coordinates, boolean intersection) {
        this.coordinates = coordinates;
        this.intersection = intersection;
    }

    /***************************
     ***** Getter & Setter *****
     ***************************/

    public void setFollw_field(Field follw_field) {
        this.follw_field = follw_field;
    }

    public ArrayList<Agent> getAgents_on_the_field() {
        return agents_on_the_field;
    }

    public Field getFollw_field() {
        return follw_field;
    }

    public void addGatewayField(int x, int y) {

    }

    public void addGatewayField(Field field) {
        gateway_fields.add(field);
    }

    public void setIntersectionColor(String color) {
        intersection_color = color;
    }
}