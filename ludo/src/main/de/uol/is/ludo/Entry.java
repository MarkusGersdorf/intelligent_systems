package de.uol.is.ludo;

import java.util.ArrayList;

public class Entry extends Field
{
    private ArrayList<Pawn> pawns = new ArrayList<>();

    public void set_pawn(Pawn p)
    {
        pawns.add(p);
    }

    public boolean remove_pawn_from_entry(Pawn p)
    {
        if(pawns.contains(p))
        {
            pawns.remove(p);
            return true;
        }
        return false;
    }

    public field_type get_field_type()
    {
        return field_type.ENTRY;
    }
}
