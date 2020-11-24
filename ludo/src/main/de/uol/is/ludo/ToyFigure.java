package de.uol.is.ludo;

public class ToyFigure {

    private String id;
    private String color;

    public ToyFigure(String color, int number) {
        this.id = color + number;
        this.color = color;
    }

    /***************************
     ***** Getter & Setter *****
     ***************************/

    public String getColor() {
        return color;
    }
}