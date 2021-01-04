package de.uol.is.ludo;

import de.uol.is.ludo.agents.*;
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
    Gui gui;

    public App(long seed) {
        super(seed);
    }

    public void start(Gui gui) {
        this.gui = gui;
        board.set_gui(gui);
        initialize_agents();
        while (!board.game_over()) {
            for (int j = 0; j < 4; j++) {
                board.get_agent()[j].step(this);
            }
        }
    }

    private static IPawn get_pawn_from_entry(ArrayList<IPawn> pawns) {
        for (IPawn p : pawns) {
            if (p.get_field().get_field_type() == IField.field_type.ENTRY) {
                return p;
            }
        }
        return null;
    }

    private void initialize_agents() {
        Agent[] agents = new Agent[4];
        agents[0] = new Strategy1("Strategy " + 1, get_player_color(0), board);
        gui.add_to_console("Agent " + get_player_color(0) + " was inizialized with strategy" + 1);
        agents[1] = new Strategy2("Strategy " + 2, get_player_color(1), board);
        gui.add_to_console("Agent " + get_player_color(1) + " was inizialized with strategy" + 2);
        agents[2] = new Strategy3("Strategy " + 3, get_player_color(2), board);
        gui.add_to_console("Agent " + get_player_color(2) + " was inizialized with strategy" + 3);
        agents[3] = new Strategy4("Strategy " + 4, get_player_color(3), board);
        gui.add_to_console("Agent " + get_player_color(3) + " was inizialized with strategy" + 4);
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

    public void reset_board() {
        board.reset();
    }
}
