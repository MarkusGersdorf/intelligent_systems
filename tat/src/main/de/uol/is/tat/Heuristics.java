package de.uol.is.tat;

import java.util.ArrayList;

/**
 * Class for the heuristics
 *
 * Gestern ausprobiert: buildTent() als boolean um zu prüfen, ob auch ein Feld geetzt worden ist
 */
public class Heuristics {

    public void mostConstrainedVariable(IField[][] field) {
        ArrayList<TentsTreesObject> trees = new ArrayList<>();
        int buildTents = 0;
        int currentRemainingOptionsLevel = 1;
        int treesWithHigherRemainingOptionsLevel = 0;

        for (int col = 1; col < field.length; col++) {
            for (int row = 1; row < field[col].length; row++) {
                if (field[col][row].get_field_type() == IField.field_type.TREE) {
                    trees.add(new TentsTreesObject(col, row, checkRemainingOptions(col, row, field), false));
                }
            }
        }

        while(buildTents < trees.size()) {
            for(TentsTreesObject object : trees) {
                if(object.remainingOptions.size() == currentRemainingOptionsLevel && !object.tentBuild) {
                    buildTent(object, field);
                    buildTents++;
                    System.out.println("Build Tent " + buildTents);
                    object.tentBuild = true;
                    reCalculateRemainingOptions(trees, field);
                    currentRemainingOptionsLevel = 0;
                } else {
                    treesWithHigherRemainingOptionsLevel++;
                }
            }

            if(treesWithHigherRemainingOptionsLevel == trees.size()) {
                currentRemainingOptionsLevel++;
            }

            treesWithHigherRemainingOptionsLevel = 0;
        }
    }

    private void buildTent(TentsTreesObject object, IField[][] field) {
        for(ArrayList<Integer> arrayList : object.remainingOptions) {
            if(field[arrayList.get(0)][arrayList.get(1)].get_field_type() == IField.field_type.EMPTY) {
                field[arrayList.get(0)][arrayList.get(1)].set_field_type(IField.field_type.TENT);
                field[arrayList.get(0)][0].set_border_limit(field[arrayList.get(0)][0].get_border_limit() - 1);
                field[0][arrayList.get(1)].set_border_limit(field[0][arrayList.get(1)].get_border_limit() - 1);
                break;
            }
        }
    }

    private void reCalculateRemainingOptions(ArrayList<TentsTreesObject> arrayList, IField[][] field) {
        for(TentsTreesObject object : arrayList) {
            if(!object.tentBuild) {
                object.updateRemainingOptions(checkRemainingOptions(object.col, object.row, field));
            }
        }
    }


    private ArrayList<ArrayList<Integer>> checkRemainingOptions(int col, int row, IField[][] field) {
        ArrayList<ArrayList<Integer>> remainingOptions = new ArrayList<>();

        // Prüfe alle Felder um den Baum

        if(checkCoordinatesForConditions(col + 1, row, field)) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(col + 1);
            arrayList.add(row);
            remainingOptions.add(arrayList);
        }

        if(checkCoordinatesForConditions(col - 1, row, field)) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(col - 1);
            arrayList.add(row);
            remainingOptions.add(arrayList);
        }

        if(checkCoordinatesForConditions(col, row + 1, field)) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(col);
            arrayList.add(row + 1);
            remainingOptions.add(arrayList);
        }

        if(checkCoordinatesForConditions(col, row - 1, field)) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(col);
            arrayList.add(row - 1);
            remainingOptions.add(arrayList);
        }
        return remainingOptions;
    }

    private boolean checkCoordinatesForConditions(int col, int row, IField[][] field) {

        // Prüfe, ob der Platz frei ist

        if (field[col][row].get_field_type() != IField.field_type.EMPTY) {
            return false;
        }

        // Prüfe, ob in der Reihe/Spalte noch Zelte gesetzt werden dürfen

        if(field[col][0].get_border_limit() == 0 || field[0][row].get_border_limit() == 0) {
            return false;
        }

        // Prüfe, ob angrenzent noch ein Zelt ist

        if (field[col + 1][row].get_field_type() == IField.field_type.TENT) {
            return false;
        }
        if (field[col - 1][row].get_field_type() == IField.field_type.TENT) {
            return false;
        }
        if (field[col][row + 1].get_field_type() == IField.field_type.TENT) {
            return false;
        }
        if (field[col][row - 1].get_field_type() == IField.field_type.TENT) {
            return false;
        }

        return true;
    }
}
