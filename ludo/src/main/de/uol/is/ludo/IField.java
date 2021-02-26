package de.uol.is.ludo;

/**
 * Interface for Field
 *
 * @author Thomas Cwil
 * @author Joosten Steenhusen
 */
public interface IField
{
    enum field_type
    {
        ENTRY, GOAL, FIELD
    }

    boolean set_pawn(IPawn p);

    void remove_pawn();

    IPawn get_pawn();

    field_type get_field_type();

    int get_field_id();
}
