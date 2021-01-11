package de.uol.is.ludo;

import de.uol.is.ludo.agents.Agent;

import java.util.ArrayList;

public interface IBoard
{
    int roll();

    boolean move_pawn(IPawn pawn, int steps);

    boolean set_pawn_into_game(IPawn pawn);

    Field[] get_fields();

    ArrayList<IPawn> get_my_pawns(IPawn.player player);

    void set_agents(Agent[] agents);

    Agent[] get_agent();
}
