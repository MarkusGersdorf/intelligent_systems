package de.uol.is.ludo;

import java.util.ArrayList;

public class Board implements IBoard
{
    private Die die = new Die();
    private Field[] fields = new Field[56];
    private Entry entry = new Entry();
    private Pawn[] pawns = new Pawn[16];
    private boolean game_over = false;

    private int winner_id;

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
            Pawn p = new Pawn(i, (i/4), this.entry);
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
    public void reset()
    {
        initialize_board();
        game_over = false;
    }

    @Override
    public int roll()
    {
        return die.roll();
    }

    @Override
    public boolean move_pawn(IPawn pawn, int steps)
    {
        check_game_over();
        if(game_over)
        {
            return false;
        }
        if(pawn.get_field().get_field_type() == IField.field_type.GOAL)
        {
            if(pawn.get_field().get_field_id() == (39 + (pawn.get_player_id() + 1) * 4))
            {
                return false;
            }
            else if((pawn.get_field().get_field_id() + steps) <= get_limit_goal(pawn))
            {
                fields[pawn.get_field().get_field_id() + steps].set_pawn(pawn);
                fields[pawn.get_field().get_field_id()].remove_pawn();
                pawn.set_field(fields[(pawn.get_field().get_field_id() + steps)]);
                return true;
            }
            else
            {
                return false;
            }
        }

        pawn.add_moved_steps(steps);
        boolean move_into_goal = (pawn.get_moved_steps() >= 40);

        if(move_into_goal)
        {
            return check_goal_movement(pawn, steps);
        }
        // Check, if target field is empty
        else if(fields[(pawn.get_field().get_field_id() + steps) % 40].get_pawn() == null)
        {
            fields[(pawn.get_field().get_field_id() + steps) % 40].set_pawn(pawn);
            // TODO: System.out.println(pawn.get_field().get_field_id());
            fields[(pawn.get_field().get_field_id())].remove_pawn();
            pawn.set_field(fields[(pawn.get_field().get_field_id() + steps) % 40]);
            return true;
        }
        // Check, if both pawns are from same player
        else if (fields[(pawn.get_field().get_field_id() + steps) % 40].get_pawn().get_player() != fields[pawn.get_field().get_field_id()].get_pawn().get_player())
        {
            entry.move_pawn_into_entry(fields[(pawn.get_field().get_field_id() + steps) % 40].get_pawn());
            fields[(pawn.get_field().get_field_id() + steps) % 40].remove_pawn();
            fields[(pawn.get_field().get_field_id() + steps) % 40].set_pawn(pawn);
            fields[(pawn.get_field().get_field_id())].remove_pawn();
            pawn.set_field(fields[(pawn.get_field().get_field_id() + steps) % 40]);
            return true;
        }
        // Both pawns are from same player and cannot move further into goal
        else
        {
            System.out.println("Cannot move");
            return false;
        }
    }

    @Override
    public boolean set_pawn_into_game(IPawn pawn)
    {
        check_game_over();
        if(game_over)
        {
            return false;
        }
        if(pawn.get_field().get_field_id() == -1)
        {
            fields[pawn.get_starting_pos()].set_pawn(pawn);
            entry.remove_pawn_from_entry(pawn);
            pawn.set_field(fields[pawn.get_starting_pos()]);
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

    private boolean check_goal_movement(IPawn pawn, int steps)
    {
        int left_steps = pawn.get_moved_steps() - 39;
        pawn.set_moved_steps(39);

        if(left_steps > 4 || fields[39 + (pawn.get_player_id() * 4) + left_steps].get_pawn() != null)
        {
            pawn.set_moved_steps((pawn.get_moved_steps() + left_steps) - steps);
            return false;
        }

        fields[(pawn.get_field().get_field_id()) % 40].remove_pawn();
        pawn.set_field(fields[39 + (pawn.get_player_id() * 4) + left_steps]);
        fields[39 + (pawn.get_player_id() * 4) + left_steps].set_pawn(pawn);

        return true;
    }

    private void check_game_over()
    {
        if(fields[40].get_pawn() != null && fields[41].get_pawn() != null && fields[42].get_pawn() != null && fields[43].get_pawn() != null)
        {
            winner_id = 0;
            game_over = true;
        }
        if(fields[44].get_pawn() != null && fields[45].get_pawn() != null && fields[46].get_pawn() != null && fields[47].get_pawn() != null)
        {
            winner_id = 1;
            game_over = true;
        }
        if(fields[48].get_pawn() != null && fields[49].get_pawn() != null && fields[50].get_pawn() != null && fields[51].get_pawn() != null)
        {
            winner_id = 2;
            game_over = true;
        }
        if(fields[52].get_pawn() != null && fields[53].get_pawn() != null && fields[54].get_pawn() != null && fields[55].get_pawn() != null)
        {
            winner_id = 3;
            game_over = true;
        }
    }

    public boolean game_over()
    {
        return game_over;
    }

    private int get_limit_goal(IPawn pawn)
    {
        int lower_limit = (40 + pawn.get_player_id() * 4);

        if(fields[lower_limit].get_pawn() == null || fields[lower_limit].get_pawn().get_id() == pawn.get_id())
        {
            if(fields[lower_limit + 1].get_pawn() == null || fields[lower_limit + 1].get_pawn().get_id() == pawn.get_id())
            {
                if(fields[lower_limit + 2].get_pawn() == null || fields[lower_limit + 2].get_pawn().get_id() == pawn.get_id())
                {
                    if(fields[lower_limit + 3].get_pawn() == null || fields[lower_limit + 3].get_pawn().get_id() == pawn.get_id())
                    {
                        return (lower_limit + 3);
                    }
                    else
                    {
                        return (lower_limit + 2);
                    }
                }
                else
                {
                    return (lower_limit + 1);
                }
            }
            else
            {
                return (lower_limit);
            }
        }
        else
        {
            return 0;
        }
    }
}