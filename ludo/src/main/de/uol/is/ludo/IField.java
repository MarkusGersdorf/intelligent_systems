package de.uol.is.ludo;

public interface IField
{
    enum field_type
    {
        ENTRY, GOAL, FIELD
    }

    void set_pawn(Pawn p);

    Pawn get_pawn();

    field_type get_field_type();
}
