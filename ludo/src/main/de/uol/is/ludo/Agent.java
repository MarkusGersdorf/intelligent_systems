package de.uol.is.ludo;

import de.uol.is.ludo.strategies.Strategie;
import sim.engine.SimState;
import sim.engine.Steppable;

import java.util.ArrayList;

/**
 * This is the intelligent agent and also represents the player
 */
public class Agent implements Steppable {
    private String color;
    private ArrayList<ToyFigure> toyFigures;
    private Field startPos;
    private Strategie strategie;

    public Agent(String color, Field startPos) {

    }

    @Override
    public void step(SimState simState) {

    }

    /**
     * Generate a random number between 1 and 6. Represent a game dice.
     *
     * @return A Integer between 1 and 6.
     */
    public static int dice(){
        return ((int) (Math.random()*(6 - 1))) + 1;
    }
}
