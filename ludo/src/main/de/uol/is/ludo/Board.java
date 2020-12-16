package de.uol.is.ludo;


import de.uol.is.ludo.agents.Agent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * RULES
 * PlayerID
 *             case 0:
 *                 return player.RED;
 *             case 1:
 *                 return player.BLUE;
 *             case 2:
 *                 return player.YELLOW;
 *             case 3:
 *                 return player.BLACK;
 */

public class Board implements IBoard
{
    private Die die = new Die();
    private Field[] fields = new Field[56];
    private Entry entry = new Entry();
    private Goal[][] goal = new Goal[4][4];
    private Pawn[] pawns = new Pawn[16];

    public Board()
    {
        initialize_board();
    }

    private void initialize_board()
    {
        for (int i = 0; i < 40; i++)
        {
            Field f = new Field(i);
            fields[i] = f;
        }

        for (int i = 40; i < 56; i++)
        {
            Goal g = new Goal(i);
            fields[i] = g;
        }

        for (int i = 0; i < 16; i++)
        {
            Pawn p = new Pawn((i/4), this.entry);
            pawns[i] = p;
        }

        for (Pawn pawn : pawns)
        {
            // Set every pawn on entry
            this.entry.set_pawn(pawn);
            //System.out.println("Set Pawn on Entry");
        }
    }

    @Override
    public int roll()
    {
        return die.roll();
    }

    @Override
    public boolean move_pawn(IPawn pawn, int steps)
    {
        // Check, if target field is empty
        if(fields[(pawn.get_field().get_field_id() + steps)].get_pawn() == null)
        {
            fields[(pawn.get_field().get_field_id() + steps)].set_pawn(pawn);
            fields[(pawn.get_field().get_field_id())].remove_pawn();
            return true;
        }
        // Check, if both pawns are from same player
        else if (fields[(pawn.get_field().get_field_id() + steps)].get_pawn().get_player() != fields[(pawn.get_field().get_field_id())].get_pawn().get_player())
        {
            fields[(pawn.get_field().get_field_id() + steps)].kick_pawn(pawn);
            fields[(pawn.get_field().get_field_id())].remove_pawn();
            return true;
        }
        // Both pawns are from same player
        else
        {
            return false;
        }
    }

    @Override
    public boolean set_pawn_into_game(IPawn pawn)
    {
        if(pawn.get_field().get_field_id() == -1)
        {
            fields[pawn.get_starting_pos()].set_pawn(pawn);
            entry.remove_pawn_from_entry(pawn);
            pawn.set_field(fields[pawn.get_starting_pos()]);
            System.out.println("Set Pawn on Field");
            return true;
        }
        return false;
    }

    @Override
    public Field[] get_fields()
    {
        return fields;
    }

    @Override
    public Entry get_entry()
    {
        return this.entry;
    }

    @Override
    public ArrayList<IPawn> get_all_pawns()
    {
        ArrayList<IPawn> all_pawns = new ArrayList<>();
        ArrayList<IPawn> entry_pawns = new ArrayList<>();
        for (IField f : fields)
        {
            if (f.get_pawn() != null)
            {
                all_pawns.add(f.get_pawn());
            }
        }
        for (IPawn p : entry_pawns)
        {
            all_pawns.add(p);
        }
        return null;
    }

    @Override
    public ArrayList<IPawn> get_my_pawns(IPawn.player player)
    {
        ArrayList<IPawn> my_pawns = new ArrayList<>();
        ArrayList<IPawn> entry_pawns = new ArrayList<>();
        for (IField f : fields)
        {
            if (f.get_pawn() != null)
            {
                if (f.get_pawn().get_player() == player)
                {
                    my_pawns.add(f.get_pawn());
                }
            }
        }
        entry_pawns = entry.get_pawns();
        for (IPawn p : entry_pawns)
        {
            if (p.get_player() == player)
            {
                my_pawns.add(p);
            }
        }
        return my_pawns;
    }
}