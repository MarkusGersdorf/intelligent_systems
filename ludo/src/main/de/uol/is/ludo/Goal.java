package de.uol.is.ludo;

import java.util.ArrayList;

public class Goal extends Field
{
    public Goal(int id)
    {
        super(id);
    }

    public field_type get_field_type()
    {
        return field_type.GOAL;
    }
}
