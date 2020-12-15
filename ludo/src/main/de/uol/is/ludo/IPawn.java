package de.uol.is.ludo;

public interface IPawn
{
    public enum player
    {
        RED, BLUE, YELLOW, BLACK;
    }

    Field get_field();

    player get_player();
}
