package de.uol.is.ludo;

/**
 * This class represents a field from ludo
 *
 * @author Thomas Cwil
 * @author Joosten Steenhusen
 */

public class Field implements IField
{
    private IPawn pawn;
    private int field_id;

    public Field(int id)
    {
        field_id = id;
    }

    /**
     * This method will set a pawn onto the field
     * @param p Pawn
     * @return true if set
     */
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

    /**
     * Remove pawn from field
     */
    @Override
    public void remove_pawn()
    {
        pawn = null;
    }

    /**
     * Get pawn from field
     * @return IPawn
     */
    @Override
    public IPawn get_pawn()
    {
        return pawn;
    }

    /**
     * Get fieldtype
     * @return enum
     */
    @Override
    public field_type get_field_type() {
        return field_type.FIELD;
    }

    /**
     * Get field id
     * @return IField
     */
    @Override
    public int get_field_id() {
        return field_id;
    }
}