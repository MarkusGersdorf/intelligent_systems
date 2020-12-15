package de.uol.is.ludo;

import java.util.Random;

public class Die
{
    private final Random die = new Random();

    public int roll()
    {
        return die.nextInt(6);
    }
}
