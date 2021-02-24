package de.uol.is.ludo;

import java.util.ArrayList;

/**
 * This class represents a single entry, where the pawns are starting.
 *
 * @author Thomas Cwil
 * @author Joosten Steenhusen
 */

public class Entry extends Field
{
    private ArrayList<IPawn> pawns = new ArrayList<>();

    /**
     * Entry has the id -1
     */
    public Entry()
    {
        super(-1);
    }

    /**
     * With this method an agent or a player can set a pawn into the entry. This is usually called when game started
     * @param p: IPawn
     * @return true
     */
    @Override
    public boolean set_pawn(IPawn p)
    {
        pawns.add(p);
        return true;
    }

    /**
     * Take a pawn from entry
     * @param p Pawn
     * @return true, if pawn was on entry
     */
    public boolean remove_pawn_from_entry(IPawn p)
    {
        if(pawns.contains(p))
        {
            pawns.remove(p);
            return true;
        }
        return false;
    }

    /**
     * Move a pawn into entry, when it was beaten by another pawn
     * @param p Pawn
     */
    public void move_pawn_into_entry(IPawn p)
    {
        pawns.add(p);
        p.set_field(this);
    }

    /**
     * Get field type
     * @return enum fieldtype
     */
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
