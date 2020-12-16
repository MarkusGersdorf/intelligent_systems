package de.uol.is.ludo;

import java.util.ArrayList;

public class Entry extends Field
{
    private ArrayList<IPawn> pawns = new ArrayList<>();

    public Entry()
    {
        super(-1);
    }

    @Override
    public boolean set_pawn(IPawn p)
    {
        pawns.add(p);
        return true;
    }

    public boolean remove_pawn_from_entry(IPawn p)
    {
        if(pawns.contains(p))
        {
            pawns.remove(p);
            return true;
        }
        return false;
    }

    @Override
    public field_type get_field_type()
    {
        return field_type.ENTRY;
    }

    public ArrayList<IPawn> get_pawns()
    {
        return pawns;
    }
}
