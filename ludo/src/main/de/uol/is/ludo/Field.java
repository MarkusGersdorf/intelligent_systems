package de.uol.is.ludo;

public class Field implements IField
{
    private IPawn pawn;
    private int field_id;

    public Field(int id)
    {
        field_id = id;
    }

    public void kick_pawn(IPawn p)
    {
        pawn = p;
    }

    @Override
    public boolean set_pawn(IPawn p)
    {
        if (pawn == null)
        {
            pawn = p;
            return true;
        }
        return false;
    }

    @Override
    public void remove_pawn()
    {
        pawn = null;
    }

    @Override
    public IPawn get_pawn()
    {
        return pawn;
    }

    @Override
    public field_type get_field_type() {
        return field_type.FIELD;
    }

    @Override
    public int get_field_id() {
        return field_id;
    }
}