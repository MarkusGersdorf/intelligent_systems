package de.uol.is.tat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the heuristics
 */
public class Heuristics {

    ArrayList<TentsTreesObject> trees = new ArrayList<>();
    int buildTents = 0;

    public void mostConstrainedVariable(IField[][] field) {
        int currentRemainingOptionsLevel = 1;
        int treesWithHigherRemainingOptionsLevel = 0;

        for (int col = 1; col < field.length; col++) {
            for (int row = 1; row < field[col].length; row++) {
                if (field[col][row].get_field_type() == IField.field_type.TREE) {
                    trees.add(new TentsTreesObject(col, row, checkRemainingOptions(col, row, field)));
                }
            }
        }

        while(!allTreeHaveATent(trees)) {
            for(TentsTreesObject object : trees) {
                if(object.remainingOptions.size() == currentRemainingOptionsLevel && !object.isTentBuild()) {
                    if(buildTent(object, field)){
                        buildTents++;
                        System.out.println("Build Tent " + buildTents);
                        reCalculateRemainingOptions(trees, field);
                        currentRemainingOptionsLevel = 0;
                        treesWithHigherRemainingOptionsLevel = 0;
                    }
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

    private boolean buildTent(TentsTreesObject object, IField[][] field) {
        if(object.remainingOptions.size() == 0) {
            checkRemainingOptions(object.col, object.row, field);
        }
        for(ArrayList<Integer> arrayList : object.remainingOptions) {
            if(field[arrayList.get(0)][arrayList.get(1)].get_field_type() == IField.field_type.EMPTY) {
                field[arrayList.get(0)][arrayList.get(1)].set_field_type(IField.field_type.TENT);
                field[arrayList.get(0)][0].set_border_limit(field[arrayList.get(0)][0].get_border_limit() - 1);
                field[0][arrayList.get(1)].set_border_limit(field[0][arrayList.get(1)].get_border_limit() - 1);
                object.setTent(arrayList);
                return true;
            }
        }
        return false;
    }

    private void reCalculateRemainingOptions(ArrayList<TentsTreesObject> arrayList, IField[][] field) {
        for(TentsTreesObject object : arrayList) {
            if(!object.isTentBuild()) {
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

        if(remainingOptions.size() == 0) {
            boolean found = false;
            while(!found) {
                ArrayList<Integer> arrayList;
                Random rand = new Random();
                int randomNum = rand.nextInt((4 - 1) + 1) + 1;
                TentsTreesObject object = getTreeFromCoordinates(col, row);
                switch (randomNum) {
                    case 1:
                        found = deleteTent(col + 1, row, field);
                        if(found) {
                            ArrayList<Integer> coordinates = new ArrayList<>();
                            coordinates.add(col + 1);
                            coordinates.add(row);
                            object.setTent(coordinates);
                            field[col + 1][row].set_field_type(IField.field_type.TENT);
                            field[col + 1][0].set_border_limit(field[col + 1][0].get_border_limit() - 1);
                            field[0][row].set_border_limit(field[0][row].get_border_limit() - 1);
                            buildTents++;
                        }
                        break;
                    case 2:
                        found = deleteTent(col - 1, row, field);
                        if(found) {
                            ArrayList<Integer> coordinates = new ArrayList<>();
                            coordinates.add(col - 1);
                            coordinates.add(row);
                            object.setTent(coordinates);
                            field[col - 1][row].set_field_type(IField.field_type.TENT);
                            field[col - 1][0].set_border_limit(field[col - 1][0].get_border_limit() - 1);
                            field[0][row].set_border_limit(field[0][row].get_border_limit() - 1);
                            buildTents++;
                        }
                        break;
                    case 3:
                        found = deleteTent(col,row + 1, field);
                        if(found) {
                            ArrayList<Integer> coordinates = new ArrayList<>();
                            coordinates.add(col);
                            coordinates.add(row + 1);
                            object.setTent(coordinates);
                            field[col][row + 1].set_field_type(IField.field_type.TENT);
                            field[0][row + 1].set_border_limit(field[0][row + 1].get_border_limit() - 1);
                            field[col][0].set_border_limit(field[col][0].get_border_limit() - 1);
                            buildTents++;
                        }
                        break;
                    case 4:
                        found = deleteTent(col,row - 1, field);
                        if(found) {
                            ArrayList<Integer> coordinates = new ArrayList<>();
                            coordinates.add(col);
                            coordinates.add(row - 1);
                            object.setTent(coordinates);
                            field[col][row - 1].set_field_type(IField.field_type.TENT);
                            field[0][row - 1].set_border_limit(field[0][row - 1].get_border_limit() - 1);
                            field[col][0].set_border_limit(field[col][0].get_border_limit() - 1);
                            buildTents++;
                        }
                        break;
                }
            }
        }
        return remainingOptions;
    }

    private TentsTreesObject getTreeFromCoordinates(int col, int row) {
        for(TentsTreesObject object : trees) {
            if(object.col == col && object.row == row) {
                return object;
            }
        }
        System.out.println("NullPointer");
        return null;
    }

    private boolean deleteTent(int col, int row, IField[][] field) {
        if(field[col][0].get_border_limit() == 0) {
            for(int i = 0; i < field[col].length; i++) {
                if(field[col][i].get_field_type() == IField.field_type.TENT) {
                    for(TentsTreesObject object : trees) {
                        if(object.isTentBuild()) {
                            if(object.tent.get(0) == col && object.tent.get(1) == i) {
                                field[col][i].set_field_type(IField.field_type.EMPTY);
                                field[col][0].set_border_limit(field[col][0].get_border_limit() + 1);
                                field[0][i].set_border_limit(field[0][i].get_border_limit() + 1);
                                object.deleteTent();
                                buildTents--;
                                return true;
                            }
                        }
                    }
                }
            }
        }

        if(field[0][row].get_border_limit() == 0) {
            for(int i = 0; i < field.length; i++) {
                if(field[i][row].get_field_type() == IField.field_type.TENT) {
                    for(TentsTreesObject object : trees) {
                        if(object.isTentBuild()) {
                            if (object.tent.get(0) == i && object.tent.get(1) == row) {
                                field[i][row].set_field_type(IField.field_type.EMPTY);
                                field[i][0].set_border_limit(field[i][0].get_border_limit() + 1);
                                field[0][row].set_border_limit(field[0][row].get_border_limit() + 1);
                                object.deleteTent();
                                buildTents--;
                                return true;
                            }
                        }
                    }

                }
            }
        }

        boolean wait = true;
        return false;
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

    private boolean allTreeHaveATent(ArrayList<TentsTreesObject> trees) {
        for(TentsTreesObject object : trees) {
            if(!object.isTentBuild()) {
                return false;
            }
        }
        return true;
    }
}
