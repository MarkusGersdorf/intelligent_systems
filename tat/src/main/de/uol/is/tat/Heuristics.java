package de.uol.is.tat;

import java.util.ArrayList;

/**
 * Class for the heuristics
 */
public class Heuristics {

    public void mostConstrainedVariable(IField[][] field) {
        ArrayList<TentsTreesObject> trees = new ArrayList<>();
        int buildTents = 0;
        int currentRemainingOptionsLevel = 1;
        int treesWithHigherRemainingOptionsLevel = 0;

        for(int col = 1; col < field.length; col++) {
            for(int row = 1; row < field[col].length; row++) {
                if(field[col][row].get_field_type() == IField.field_type.TREE) {
                    trees.add(new TentsTreesObject(col, row, checkRemainingOptions(col, row, field), false));
                }
            }
        }

        while(buildTents < trees.size()) {
            for(TentsTreesObject object : trees) {
                if(object.remainingOptions == currentRemainingOptionsLevel) {
                    buildTent(object, field);
                    // Check all Trees, maybe the place was also an option for another tree
                    for(TentsTreesObject tree : trees) {
                        checkRemainingOptions(tree.col, tree.row, field);
                    }
                } else {
                    treesWithHigherRemainingOptionsLevel++;
                }
            }
        }


    }

    private void buildTent(TentsTreesObject object, IField[][] field) {

    }

    private int checkRemainingOptions(int col, int row, IField[][] field) {
        int remainingOptions = 0;

        if(field[col + 1][row].get_field_type() == IField.field_type.EMPTY && field[col + 1][0] > 0) {
            remainingOptions++;
        }
        if(field[col - 1][row].get_field_type() == IField.field_type.EMPTY) {
            remainingOptions++;
        }
        if(field[col][row + 1].get_field_type() == IField.field_type.EMPTY) {
            remainingOptions++;
        }
        if(field[col][row - 1].get_field_type() == IField.field_type.EMPTY) {
            remainingOptions++;
        }

        return remainingOptions;
    }
}
