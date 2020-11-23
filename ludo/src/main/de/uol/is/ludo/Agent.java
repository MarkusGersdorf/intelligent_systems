package de.uol.is.ludo;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Agent implements Steppable {

    private String playerColor;
    private Field startPos;
    private int base;

    public Agent(String playerColor, Field startPos) {
        this.playerColor = playerColor;
        this.startPos = startPos;
        this.base = 4;
    }

    @Override
    public void step(SimState simState) {
        if (base != 4) {

        } else {

        }
    }
}