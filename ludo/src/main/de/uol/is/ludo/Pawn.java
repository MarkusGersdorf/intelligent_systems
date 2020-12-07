package de.uol.is.ludo;

public class Pawn implements IPawn
{
    private int player_id;
    private Field field;

    public Pawn(int id, Field f)
    {
        player_id = id;
        field = f;
    }

    @Override
    public Field get_field() {
        return field;
    }

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
}