package de.uol.is.ludo;

import java.util.Random;

/**
 * This class represents a simple die, which can be rolled by agents or players
 *
 * @author Thomas Cwil
 * @author Joosten Steenhusen
 */

public class Die
{
    private final Random die = new Random();

    /**
     * Roll a die
     * @return int between 1-6
     */
    public int roll()
    {
        return (die.nextInt(6) + 1);
    }
}
