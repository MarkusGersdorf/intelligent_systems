package de.uol.is.ludo;

import de.uol.is.ludo.agents.Agent;
import de.uol.is.ludo.agents.Strategy1;
import de.uol.is.ludo.agents.Strategy2;
import sim.engine.SimState;

import java.util.ArrayList;

/**
 * PlayerID
 * 0: player.RED;
 * 1: player.BLUE;
 * 2: player.YELLOW;
 * 3: player.BLACK;
 * <p>
 * FieldID
 * -1: Entry
 * 0 - 39: Fields
 * 40 - 43: Goal (RED)
 * 44 - 47: Goal (BLUE)
 * 48 - 51: Goal (YELLOW)
 * 52 - 55: Goal (BLACK)
 */

// TODO: Magic Numbers entfernen / erklären 

public class App extends SimState {
    private static final IBoard board = new Board(System.currentTimeMillis());
    private static int rolled;
    private static final ArrayList<IPawn> red_pawns = board.get_my_pawns(IPawn.player.RED);
    private static final ArrayList<IPawn> blue_pawns = board.get_my_pawns(IPawn.player.BLUE);
    private static final ArrayList<IPawn> yellow_pawns = board.get_my_pawns(IPawn.player.YELLOW);
    private static final ArrayList<IPawn> black_pawns = board.get_my_pawns(IPawn.player.BLACK);

    public App(long seed) {
        super(seed);
    }

    public void start() {
        super.start();
        initialize_agents();
        for (int i = 0; i < 4; i++) {
            schedule.scheduleRepeating(board.get_agent()[i]);
        }
    }

    public static void main(String[] args) {
        doLoop(App.class, args);
        System.exit(0);
    }

    private static IPawn get_pawn_from_entry(ArrayList<IPawn> pawns) {
        for (IPawn p : pawns) {
            if (p.get_field().get_field_type() == IField.field_type.ENTRY) {
                return p;
            }
        }
        return null;
    }

    private static void initialize_agents() {
        Agent[] agents = new Agent[4];
        for (int i = 0; i < 4; i++) {
            Agent new_agent;
            if (i % 2 == 0) {
                new_agent = new Strategy1("Strategy " + i, get_player_color(i), board);
            } else {
                new_agent = new Strategy2("Strategy " + i, get_player_color(i), board);
            }
            agents[i] = new_agent;
        }
        board.set_agents(agents);
    }

    private static IPawn.player get_player_color(int player_id) {
        switch (player_id) {
            case 0:
                return IPawn.player.RED;
            case 1:
                return IPawn.player.BLUE;
            case 2:
                return IPawn.player.YELLOW;
            case 3:
                return IPawn.player.BLACK;
            default:
                return null;
        }

    }
}
