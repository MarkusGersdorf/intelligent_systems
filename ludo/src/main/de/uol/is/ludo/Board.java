package de.uol.is.ludo;

import de.uol.is.ludo.agents.Agent;
import sim.engine.SimState;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents the board and will be used by the agents
 *
 * @author Thomas Cwil
 * @author Joosten Steenhusen
 * @version 0.1
 */

public class Board extends SimState implements IBoard {
    private final Die die = new Die();
    private final Field[] fields = new Field[56];
    private final Entry entry = new Entry();
    private final Pawn[] pawns = new Pawn[16];
    private boolean game_over = false;
    private Agent[] agents;
    private Gui gui;
    private boolean output = false;

    public Board(long seed) {
        super(seed);
        initialize_board();
    }

    /**
     * This method will reset the board (e.g. when starting a new game)
     */
    private void initialize_board() {
        for (int i = 0; i < 40; i++) {
            Field f = new Field(i);
            fields[i] = f;
        }

        for (int i = 40; i < 56; i++) {
            Goal g = new Goal(i);
            fields[i] = g;
        }

        for (int i = 0; i < 16; i++) {
            Pawn p = new Pawn(i, (i / 4), this.entry);
            pawns[i] = p;
        }

        for (Pawn pawn : pawns) {
            // Set every pawn on entry
            this.entry.set_pawn(pawn);
            //System.out.println("Set Pawn on Entry");
        }
    }

    /**
     * @deprecated
     */
    @Override
    public void reset() {
        initialize_board();
        game_over = false;
        output = false;
    }

    /**
     * Method simulates a die
     *
     * @return integer between 1-6
     */
    @Override
    public int roll() {
        return die.roll();
    }

    /**
     * Main method of board, which moves the pawns on the board
     *
     * @param pawn  given pawn from agent
     * @param steps given steps from agent (die)
     * @return boolean, if movement was successful
     */
    @Override
    public boolean move_pawn(IPawn pawn, int steps) {
        check_game_over();
        if(pawn.get_field().get_field_type() == IField.field_type.ENTRY) {
            return false;
        }
        if (game_over) {
            return false;
        }
        if (pawn.get_field().get_field_type() == IField.field_type.GOAL) {
            if (pawn.get_field().get_field_id() == (39 + (pawn.get_player_id() + 1) * 4)) {
                return false;
            } else if ((pawn.get_field().get_field_id() + steps) <= get_limit_goal(pawn)) {
                fields[pawn.get_field().get_field_id() + steps].set_pawn(pawn);
                fields[pawn.get_field().get_field_id()].remove_pawn();
                pawn.set_field(fields[(pawn.get_field().get_field_id() + steps)]);
                return true;
            } else {
                return false;
            }
        }

        pawn.add_moved_steps(steps);
        boolean move_into_goal = (pawn.get_moved_steps() >= 40);

        if (move_into_goal) {
            return check_goal_movement(pawn, steps);
        }
        // Check, if target field is empty
        else if (fields[(pawn.get_field().get_field_id() + steps) % 40].get_pawn() == null) {
            fields[(pawn.get_field().get_field_id() + steps) % 40].set_pawn(pawn);
            fields[(pawn.get_field().get_field_id())].remove_pawn();
            pawn.set_field(fields[(pawn.get_field().get_field_id() + steps) % 40]);
            return true;
        }
        // Check, if both pawns are from same player
        else if (fields[(pawn.get_field().get_field_id() + steps) % 40].get_pawn().get_player() != fields[pawn.get_field().get_field_id()].get_pawn().get_player()) {
            entry.move_pawn_into_entry(fields[(pawn.get_field().get_field_id() + steps) % 40].get_pawn());
            fields[(pawn.get_field().get_field_id() + steps) % 40].remove_pawn();
            fields[(pawn.get_field().get_field_id() + steps) % 40].set_pawn(pawn);
            fields[(pawn.get_field().get_field_id())].remove_pawn();
            pawn.set_field(fields[(pawn.get_field().get_field_id() + steps) % 40]);
            return true;
        }
        // Both pawns are from same player and cannot move further into goal
        else {
            // System.out.println("Cannot move");
            return false;
        }
    }

    /**
     * This method sets given pawn into the board
     *
     * @param pawn given by agent
     * @return true if successful
     */
    @Override
    public boolean set_pawn_into_game(IPawn pawn) {
        check_game_over();
        if (game_over) {
            return false;
        }
        if (pawn.get_field().get_field_id() == -1 && fields[pawn.get_starting_pos()].get_pawn() == null) {
            fields[pawn.get_starting_pos()].set_pawn(pawn);
            entry.remove_pawn_from_entry(pawn);
            pawn.set_field(fields[pawn.get_starting_pos()]);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Getter
     *
     * @return Array of Field
     */
    @Override
    public Field[] get_fields() {
        return fields;
    }

    /**
     * Getter
     *
     * @return Entry
     */
    @Override
    public Entry get_entry() {
        return this.entry;
    }

    /**
     * Get all pawns from all players
     *
     * @return ArrayList of IPawn
     */
    @Override
    public ArrayList<IPawn> get_all_pawns() {
        ArrayList<IPawn> all_pawns = new ArrayList<>();
        Collections.addAll(all_pawns, pawns);
        return all_pawns;
    }

    /**
     * Get only my pawns
     *
     * @param player the specific player
     * @return ArrayList of IPawn
     */
    @Override
    public ArrayList<IPawn> get_my_pawns(IPawn.player player) {
        ArrayList<IPawn> my_pawns = new ArrayList<>();
        ArrayList<IPawn> entry_pawns;
        for (IField f : fields) {
            if (f.get_pawn() != null) {
                if (f.get_pawn().get_player() == player) {
                    my_pawns.add(f.get_pawn());
                }
            }
        }
        entry_pawns = entry.get_pawns();
        for (IPawn p : entry_pawns) {
            if (p.get_player() == player) {
                my_pawns.add(p);
            }
        }
        return my_pawns;
    }

    /**
     * Checks if pawn can be moved into goal
     *
     * @param pawn  given by agent
     * @param steps given by agent
     * @return true, if successful
     */
    private boolean check_goal_movement(IPawn pawn, int steps) {
        int left_steps = pawn.get_moved_steps() - 39;
        pawn.set_moved_steps(39);

        if (left_steps > 4 || fields[39 + (pawn.get_player_id() * 4) + left_steps].get_pawn() != null) {
            pawn.set_moved_steps((pawn.get_moved_steps() + left_steps) - steps);
            return false;
        }

        fields[(pawn.get_field().get_field_id()) % 40].remove_pawn();
        pawn.set_field(fields[39 + (pawn.get_player_id() * 4) + left_steps]);
        fields[39 + (pawn.get_player_id() * 4) + left_steps].set_pawn(pawn);

        return true;
    }

    /**
     * Routine, which checks if game is over, setting winner_id (player_id) and game_over (boolean)
     */
    private void check_game_over() {
        String winner = null;
        for (int i = 0; i < agents.length; i++) {
            int counter = 0;
            for (IPawn pawn : get_all_pawns()) {
                if (pawn.get_player_id() == i && pawn.get_field().get_field_type() == IField.field_type.GOAL) {
                    counter++;
                }
            }
            if (counter == 4) {
                game_over = true;
                switch (i) {
                    case 0:
                        winner = "RED";
                        break;
                    case 1:
                        winner = "BLUE";
                        break;
                    case 2:
                        winner = "YELLOW";
                        break;
                    case 3:
                        winner = "BLACK";
                        break;
                    default:
                        winner = "Nobody";
                        break;
                }
                break;
            }
        }

        if (game_over && !output) {
            output = true;
            gui.calc_visualization(get_all_pawns(), winner, agents);
        }
    }

    /**
     * Getter
     *
     * @return game_over
     */
    public boolean game_over() {
        return game_over;
    }

    /**
     * Checks, how far given pawn can move into goal
     *
     * @param pawn given by agent
     * @return integer between 1-4
     */
    private int get_limit_goal(IPawn pawn) {
        int lower_limit = (40 + pawn.get_player_id() * 4);

        if (fields[lower_limit].get_pawn() == null || fields[lower_limit].get_pawn().get_id() == pawn.get_id()) {
            if (fields[lower_limit + 1].get_pawn() == null || fields[lower_limit + 1].get_pawn().get_id() == pawn.get_id()) {
                if (fields[lower_limit + 2].get_pawn() == null || fields[lower_limit + 2].get_pawn().get_id() == pawn.get_id()) {
                    if (fields[lower_limit + 3].get_pawn() == null || fields[lower_limit + 3].get_pawn().get_id() == pawn.get_id()) {
                        return (lower_limit + 3);
                    } else {
                        return (lower_limit + 2);
                    }
                } else {
                    return (lower_limit + 1);
                }
            } else {
                return (lower_limit);
            }
        } else {
            return 0;
        }
    }

    public void set_agents(Agent[] agents) {
        this.agents = agents;
    }

    public Agent[] get_agent() {
        return agents;
    }

    @Override
    public void set_gui(Gui gui) {
        this.gui = gui;
    }
}