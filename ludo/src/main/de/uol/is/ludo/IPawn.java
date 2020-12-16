package de.uol.is.ludo;

public interface IPawn
{
    public enum player
    {
        RED, BLUE, YELLOW, BLACK;
    }

    Field get_field();

    player get_player();

    int get_starting_pos();

    void set_player_id(int i);

    void set_field(Field f);

    void set_starting_pos(int i);
}
