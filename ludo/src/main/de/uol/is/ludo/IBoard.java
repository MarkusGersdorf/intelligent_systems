package de.uol.is.ludo;

import de.uol.is.ludo.agents.Agent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface IBoard
{
    void reset();

    int roll();

    boolean move_pawn(IPawn pawn, int steps);

    boolean set_pawn_into_game(IPawn pawn);

    boolean game_over();

    Field[] get_fields();

    Entry get_entry();

    ArrayList<IPawn> get_all_pawns();

    ArrayList<IPawn> get_my_pawns(IPawn.player player);

    void set_agents(Agent[] agents);

    Agent[] get_agent();
}
