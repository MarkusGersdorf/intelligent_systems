package de.uol.is.ludo;

/**
 * Interface for Pawn
 *
 * @author Thomas Cwil
 * @author Joosten Steenhusen
 */
public interface IPawn
{
    int getFieldId();

    Field get_field();

    player get_player();

    int get_starting_pos();

    int get_moved_steps();

    int get_player_id();

    int get_id();

    void set_field(Field f);

    void set_starting_pos(int i);

    void add_moved_steps(int i);

    void sub_moved_steps(int i);

    void set_moved_steps(int i);

    void set_player_id(int i);

    void set_id(int id);

    enum player {
        RED, BLUE, YELLOW, BLACK
    }
}
