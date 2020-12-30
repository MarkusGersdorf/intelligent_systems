package de.uol.is.ludo;

import de.uol.is.ludo.agents.Agent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Gui extends Application {

    // Entries
    @FXML
    Circle entry_red_1;
    @FXML
    Circle entry_red_2;
    @FXML
    Circle entry_red_3;
    @FXML
    Circle entry_red_4;

    @FXML
    Circle entry_blue_1;
    @FXML
    Circle entry_blue_2;
    @FXML
    Circle entry_blue_3;
    @FXML
    Circle entry_blue_4;

    @FXML
    Circle entry_yellow_1;
    @FXML
    Circle entry_yellow_2;
    @FXML
    Circle entry_yellow_3;
    @FXML
    Circle entry_yellow_4;

    @FXML
    Circle entry_black_1;
    @FXML
    Circle entry_black_2;
    @FXML
    Circle entry_black_3;
    @FXML
    Circle entry_black_4;

    // Goal
    @FXML
    Circle goal_red_1;
    @FXML
    Circle goal_red_2;
    @FXML
    Circle goal_red_3;
    @FXML
    Circle goal_red_4;

    @FXML
    Circle goal_blue_1;
    @FXML
    Circle goal_blue_2;
    @FXML
    Circle goal_blue_3;
    @FXML
    Circle goal_blue_4;

    @FXML
    Circle goal_yellow_1;
    @FXML
    Circle goal_yellow_2;
    @FXML
    Circle goal_yellow_3;
    @FXML
    Circle goal_yellow_4;

    @FXML
    Circle goal_black_1;
    @FXML
    Circle goal_black_2;
    @FXML
    Circle goal_black_3;
    @FXML
    Circle goal_black_4;

    // Fields
    @FXML
    Circle field_1;
    @FXML
    Circle field_2;
    @FXML
    Circle field_3;
    @FXML
    Circle field_4;
    @FXML
    Circle field_5;
    @FXML
    Circle field_6;
    @FXML
    Circle field_7;
    @FXML
    Circle field_8;
    @FXML
    Circle field_9;
    @FXML
    Circle field_10;
    @FXML
    Circle field_11;
    @FXML
    Circle field_12;
    @FXML
    Circle field_13;
    @FXML
    Circle field_14;
    @FXML
    Circle field_15;
    @FXML
    Circle field_16;
    @FXML
    Circle field_17;
    @FXML
    Circle field_18;
    @FXML
    Circle field_19;
    @FXML
    Circle field_20;
    @FXML
    Circle field_21;
    @FXML
    Circle field_22;
    @FXML
    Circle field_23;
    @FXML
    Circle field_24;
    @FXML
    Circle field_25;
    @FXML
    Circle field_26;
    @FXML
    Circle field_27;
    @FXML
    Circle field_28;
    @FXML
    Circle field_29;
    @FXML
    Circle field_30;
    @FXML
    Circle field_31;
    @FXML
    Circle field_32;
    @FXML
    Circle field_33;
    @FXML
    Circle field_34;
    @FXML
    Circle field_35;
    @FXML
    Circle field_36;
    @FXML
    Circle field_37;
    @FXML
    Circle field_38;
    @FXML
    Circle field_39;
    @FXML
    Circle field_40;

    @FXML
    private TextArea console;

    @FXML
    private TextArea runtime_information;

    @FXML
    private TextField multiplier;

    private HashMap<String, Circle> circleHashMap = null;
    private static String[] args;
    private final App app = new App(System.currentTimeMillis());
    private double replay_rate;
    private double win_counter_red;
    private double win_counter_blue;
    private double win_counter_yellow;
    private double win_counter_black;
    private double round_counter = 0;

    public static void main(String[] args) {
        Gui.args = args;
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui_source/board.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(Gui.class.getResourceAsStream("gui_source/pawn.png")));
        stage.setTitle("Mensch ärger dich nicht!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void on_play_button() throws IOException {
        if (circleHashMap == null) {
            circleHashMap = init_gui();
        }

        replay_rate = Integer.parseInt(multiplier.getText());
        reset_win_rates();
        reset_visualization();
        for (int i = 0; i < replay_rate; i++) {
            System.out.println(i);
            app.start(this);
            app.reset_board();
        }
    }

    private void set_visualization(ArrayList<IPawn> pawns, String winner, Agent[] agents) {
        int counter_goal_red = 1;
        int counter_goal_blue = 1;
        int counter_goal_yellow = 1;
        int counter_goal_black = 1;
        int counter_entry_red = 1;
        int counter_entry_blue = 1;
        int counter_entry_yellow = 1;
        int counter_entry_black = 1;

        for (IPawn pawn : pawns) {
            if (pawn.get_player() == IPawn.player.RED) {
                if (pawn.get_field().get_field_type() == IField.field_type.FIELD) {
                    mark_circle("field_" + (pawn.get_field().get_field_id() + 1), "RED");
                } else if (pawn.get_field().get_field_type() == IField.field_type.GOAL) {
                    mark_circle("goal_red_" + counter_goal_red, "RED");
                    counter_goal_red++;
                } else if (pawn.get_field().get_field_type() == IField.field_type.ENTRY) {
                    mark_circle("entry_red_" + counter_entry_red, "RED");
                    counter_entry_red++;
                }
            } else if (pawn.get_player() == IPawn.player.BLUE) {
                if (pawn.get_field().get_field_type() == IField.field_type.FIELD) {
                    mark_circle("field_" + (pawn.get_field().get_field_id() + 1), "BLUE");
                } else if (pawn.get_field().get_field_type() == IField.field_type.GOAL) {
                    mark_circle("goal_blue_" + counter_goal_blue, "BLUE");
                    counter_goal_blue++;
                } else if (pawn.get_field().get_field_type() == IField.field_type.ENTRY) {
                    mark_circle("entry_blue_" + counter_entry_blue, "BLUE");
                    counter_entry_blue++;
                }
            } else if (pawn.get_player() == IPawn.player.YELLOW) {
                if (pawn.get_field().get_field_type() == IField.field_type.FIELD) {
                    mark_circle("field_" + (pawn.get_field().get_field_id() + 1), "YELLOW");
                } else if (pawn.get_field().get_field_type() == IField.field_type.GOAL) {
                    mark_circle("goal_yellow_" + counter_goal_yellow, "YELLOW");
                    counter_goal_yellow++;
                } else if (pawn.get_field().get_field_type() == IField.field_type.ENTRY) {
                    mark_circle("entry_yellow_" + counter_entry_yellow, "YELLOW");
                    counter_entry_yellow++;
                }
            } else if (pawn.get_player() == IPawn.player.BLACK) {
                if (pawn.get_field().get_field_type() == IField.field_type.FIELD) {
                    mark_circle("field_" + (pawn.get_field().get_field_id() + 1), "BLACK");
                } else if (pawn.get_field().get_field_type() == IField.field_type.GOAL) {
                    mark_circle("goal_black_" + counter_goal_black, "BLACK");
                    counter_goal_black++;
                } else if (pawn.get_field().get_field_type() == IField.field_type.ENTRY) {
                    mark_circle("entry_black_" + counter_entry_black, "BLACK");
                    counter_entry_black++;
                }
            }

            String general_information =
                    "Win-Rates: \n \n" +
                            " - Win-Rate RED: " + ((win_counter_red / replay_rate) * 100.0) + "%" + "\n" +
                            " - Win-Rate BLUE: " + ((win_counter_blue / replay_rate) * 100.0) + "%" + "\n" +
                            " - Win-Rate YELLOW: " + ((win_counter_yellow / replay_rate) * 100.0) + "%" + "\n" +
                            " - Win-Rate BLACK: " + ((win_counter_black / replay_rate) * 100.0) + "%" + "\n" +

                            "General Information: \n \n" +
                            " - Winner: " + winner + "\n" +
                            " - Mason Time: " + app.schedule.getTime() + "\n" +
                            " - Mason Steps: " + app.schedule.getSteps() + "\n" +
                            " - Mason Schedule Complete: " + app.schedule.scheduleComplete() + "\n" +
                            " - Mason Hashcode: " + app.schedule.hashCode() + "\n" +
                            " - Mason Is Sealed: " + app.schedule.isSealed() + "\n";

            for (Agent agent : agents) {
                String agent_information = "\n Agent " + agent.getColor() + "(ID = " + get_player_id(agent.getColor()) + "): \n \n" +
                        " - Used Strategy: " + agent.getStrategy() + "\n" +
                        " - Rounds played by " + agent.getColor() + ": " + agent.get_rounds() + "\n" +
                        " - Total number of dice from " + agent.getColor() + ": " + agent.get_moves() + "\n" +
                        " - Pawns in goal: " + get_pawns(agent.getColor(), IField.field_type.GOAL, pawns) + "\n" +
                        " - Pawns in house: " + get_pawns(agent.getColor(), IField.field_type.ENTRY, pawns) + "\n" +
                        " - Pawns on fields: " + get_pawns(agent.getColor(), IField.field_type.FIELD, pawns) + "\n";
                general_information = general_information + agent_information;
            }

            runtime_information.setText(general_information);
        }
    }

    @FXML
    public void on_exit_button() {
        Platform.exit();
    }

    public void add_to_console(String output) {
        console.appendText(output + "\n");
    }

    private void reset_visualization() {
        console.clear();
        round_counter = 0;
        for (Circle circle : circleHashMap.values()) {
            circle.setFill(Paint.valueOf("WHITE"));
        }
    }

    public void reset_win_rates() {
        win_counter_red = 0;
        win_counter_blue = 0;
        win_counter_yellow = 0;
        win_counter_black = 0;
    }

    public void calc_visualization(ArrayList<IPawn> pawns, String winner, Agent[] agents) {
        switch (winner) {
            case "RED":
                win_counter_red += 1;
                break;
            case "BLUE":
                win_counter_blue += 1;
                break;
            case "YELLOW":
                win_counter_yellow += 1;
                break;
            case "BLACK":
                win_counter_black += 1;
                break;
        }
        round_counter++;
        if (round_counter == replay_rate) {
            set_visualization(pawns, winner, agents);
        }
    }

    private int get_pawns(IPawn.player player, IField.field_type type, ArrayList<IPawn> pawns) {
        int counter = 0;
        for (IPawn pawn : pawns) {
            if (pawn.get_field().get_field_type() == type && pawn.get_player() == player) {
                counter += 1;
            }
        }
        return counter;
    }

    private int get_player_id(IPawn.player player) {
        switch (player) {
            case RED:
                return 0;
            case BLUE:
                return 1;
            case YELLOW:
                return 2;
            case BLACK:
                return 3;
        }
        return 0;
    }

    private void mark_circle(String string, String color) {
        Circle circle = circleHashMap.get(string);
        circle.setFill(Paint.valueOf(color));
    }

    private HashMap<String, Circle> init_gui() {
        HashMap<String, Circle> map = new HashMap<>();
        map.put("entry_red_1", entry_red_1);
        map.put("entry_red_2", entry_red_2);
        map.put("entry_red_3", entry_red_3);
        map.put("entry_red_4", entry_red_4);
        map.put("entry_blue_1", entry_blue_1);
        map.put("entry_blue_2", entry_blue_2);
        map.put("entry_blue_3", entry_blue_3);
        map.put("entry_blue_4", entry_blue_4);
        map.put("entry_yellow_1", entry_yellow_1);
        map.put("entry_yellow_2", entry_yellow_2);
        map.put("entry_yellow_3", entry_yellow_3);
        map.put("entry_yellow_4", entry_yellow_4);
        map.put("entry_black_1", entry_black_1);
        map.put("entry_black_2", entry_black_2);
        map.put("entry_black_3", entry_black_3);
        map.put("entry_black_4", entry_black_4);
        map.put("goal_red_1", goal_red_1);
        map.put("goal_red_2", goal_red_2);
        map.put("goal_red_3", goal_red_3);
        map.put("goal_red_4", goal_red_4);
        map.put("goal_blue_1", goal_blue_1);
        map.put("goal_blue_2", goal_blue_2);
        map.put("goal_blue_3", goal_blue_3);
        map.put("goal_blue_4", goal_blue_4);
        map.put("goal_yellow_1", goal_yellow_1);
        map.put("goal_yellow_2", goal_yellow_2);
        map.put("goal_yellow_3", goal_yellow_3);
        map.put("goal_yellow_4", goal_yellow_4);
        map.put("goal_black_1", goal_black_1);
        map.put("goal_black_2", goal_black_2);
        map.put("goal_black_3", goal_black_3);
        map.put("goal_black_4", goal_black_4);
        map.put("field_1", field_1);
        map.put("field_2", field_2);
        map.put("field_3", field_3);
        map.put("field_4", field_4);
        map.put("field_5", field_5);
        map.put("field_6", field_6);
        map.put("field_7", field_7);
        map.put("field_8", field_8);
        map.put("field_9", field_9);
        map.put("field_10", field_10);
        map.put("field_11", field_11);
        map.put("field_12", field_12);
        map.put("field_13", field_13);
        map.put("field_14", field_14);
        map.put("field_15", field_15);
        map.put("field_16", field_16);
        map.put("field_17", field_17);
        map.put("field_18", field_18);
        map.put("field_19", field_19);
        map.put("field_20", field_20);
        map.put("field_21", field_21);
        map.put("field_22", field_22);
        map.put("field_23", field_23);
        map.put("field_24", field_24);
        map.put("field_25", field_25);
        map.put("field_26", field_26);
        map.put("field_27", field_27);
        map.put("field_28", field_28);
        map.put("field_29", field_29);
        map.put("field_30", field_30);
        map.put("field_31", field_31);
        map.put("field_32", field_32);
        map.put("field_33", field_33);
        map.put("field_34", field_34);
        map.put("field_35", field_35);
        map.put("field_36", field_36);
        map.put("field_37", field_37);
        map.put("field_38", field_38);
        map.put("field_39", field_39);
        map.put("field_40", field_40);
        return map;
    }

}
