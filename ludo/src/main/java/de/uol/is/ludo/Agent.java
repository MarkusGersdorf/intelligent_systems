package de.uol.is.ludo;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Agent implements Steppable {

    private String player_color;
    private Field start_pos;
    private int base;

    public Agent(String player_color, Field start_pos) {
        this.player_color = player_color;
        this.start_pos = start_pos;
        this.base = 4;
    }

    @Override
    public void step(SimState simState) {
        if(base != 4) {

        } else {

        }
    }
}