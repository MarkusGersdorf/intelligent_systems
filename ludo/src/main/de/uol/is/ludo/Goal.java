package de.uol.is.ludo;

import java.util.ArrayList;

/**
 * This class represents a goal
 *
 * @author Thomas Cwil
 * @author Joosten Steenhusen
 */

public class Goal extends Field
{
    /**
     * Goal has a individual id as a normal field
     * @param id id of field
     */
    public Goal(int id)
    {
        super(id);
    }

    /**
     * Get fieldtype
     * @return enum fieldtype
     */
    public field_type get_field_type()
    {
        return field_type.GOAL;
    }
}
