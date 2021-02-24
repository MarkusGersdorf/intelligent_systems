package de.uol.is.ludo;

public class Pawn implements IPawn
{
    private int id;
    private int player_id;
    private Field field;
    private int starting_pos;
    private int moved_steps;

    public Pawn(int id, int p_id, Field f)
    {
        this.id = id;
        player_id = p_id;
        field = f;
        switch (player_id)
        {
            case 0:
                set_starting_pos(0);
                break;
            case 1:
                set_starting_pos(10);
                break;
            case 2:
                set_starting_pos(20);
                break;
            case 3:
                set_starting_pos(30);
                break;
            default:
                break;
        }
        moved_steps = 0;
    }

    @Override
    public Field get_field() {
        return field;
    }

    // TODO: Auch in Konstruktor packen, starting_pos muss damit verknüpft werden, evtl. starting_pos als Variable?
    public player get_player()
    {
        switch (player_id)
        {
            case 0:
                return player.RED;
            case 1:
                return player.BLUE;
            case 2:
                return player.YELLOW;
            case 3:
                return player.BLACK;
            default:
                return null;
        }
    }

    @Override
    public int get_starting_pos()
    {
        return this.starting_pos;
    }

    @Override
    public int get_moved_steps() { return this.moved_steps; }

    @Override
    public int get_player_id()
    {
        return this.player_id;
    }

    @Override
    public int get_id()
    {
        return this.id;
    }

    @Override
    public void set_field(Field f)
    {
        this.field = f;
    }

    @Override
    public void set_starting_pos(int i)
    {
        this.starting_pos = i;
    }

    @Override
    public void add_moved_steps(int i)
    {
        this.moved_steps += i;
    }

    @Override
    public void sub_moved_steps(int i) { this.moved_steps -= i; }

    @Override
    public void set_moved_steps(int i)
    {
        this.moved_steps = i;
    }

    @Override
    public void set_player_id(int i) {
        this.player_id = i;
    }

    @Override
    public void set_id(int id) {
        this.id = id;
    }

    @Override
    public int getFieldId() {
        return field.get_field_id();
    }
}