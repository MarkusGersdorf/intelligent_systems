package de.uol.is.ludo;

public class Field implements IField
{
    private Pawn pawn;

    public Field()
    {

    }

    @Override
    public void set_pawn(Pawn p) {

    }

    @Override
    public Pawn get_pawn() {
        return null;
    }

    @Override
    public field_type get_field_type() {
        return field_type.FIELD;
    }
}