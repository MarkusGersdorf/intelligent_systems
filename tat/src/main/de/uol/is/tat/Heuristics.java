package de.uol.is.tat;

import java.util.ArrayList;
import java.util.Random;

public class Heuristics {
    Random rand = new Random();
    ArrayList<TentsTreesObject> trees = new ArrayList<>();
    ArrayList<TentsTreesObject> progress = new ArrayList<TentsTreesObject>();
    IField[][] field;
    int peak = 0;
    int buildTents = 0;

    public boolean mostConstrainedVariable(IField[][] matrix, int fieldnumber) {
        this.field = matrix;
        int currentRemainingOptionsLevel = 1;
        for (int col = 1; col < field.length; col++) {
            for (int row = 1; row < field[col].length; row++) {
                if (field[col][row].getFieldType() == IField.field_type.TREE) {
                    TentsTreesObject tentsTreesObject = new TentsTreesObject(col, row);
                    tentsTreesObject.updateRemainingOptions(checkRemainingOptions(tentsTreesObject));
                    trees.add(tentsTreesObject);
                }
            }
        }

        while (!oneTentForEachTree()) {
            for (TentsTreesObject object : trees) {
                if (object.remainingOptions.size() == currentRemainingOptionsLevel && !object.isTentBuild()) {
                    if (buildTent(object)) {
                        reCalculateRemainingOptions();
                        currentRemainingOptionsLevel = 0;
                        break;
                    } else {
                        throw new RuntimeException();
                    }
                }
            }


            int counter = 0;
            int counter2 = 0;
            for (TentsTreesObject object : trees) {
                counter2++;
                if (object.isTentBuild()) {
                    counter++;
                }
            }
            if (peak < counter) {
                peak = counter;
            }
            System.out.println("Aktuell läuft die " + fieldnumber + ". CSV-Datei. Bäume mit Zelt: " + counter + " (" + counter2 + ") --> " + peak);

            if (currentRemainingOptionsLevel == 4) {
                currentRemainingOptionsLevel = 0;
            } else {
                currentRemainingOptionsLevel++;
            }
        }
        return true;
    }

    /**
     * Check for trees without an tent an recalculate their options.
     */
    private void reCalculateRemainingOptions() {
        for (TentsTreesObject object : trees) {
            if (!object.isTentBuild()) {
                object.remainingOptions = checkRemainingOptions(object);
            }
        }
    }

    /**
     * @param object
     * @return
     */
    private boolean buildTent(TentsTreesObject object) {
        if (object.remainingOptions.size() == 0) {
            deleteBlockingTent(object);
        }
        if (object.remainingOptions.size() == 1) {
            for (ArrayList<Integer> arrayList : object.remainingOptions) {
                object.setTent(arrayList);
                field[arrayList.get(0)][arrayList.get(1)].setFieldType(IField.field_type.TENT);
                decreaseBorderLimit(arrayList.get(0), arrayList.get(1));
                progress.add(object);
                return true;
            }
        } else {
            // Get random remaining options from the remainingOptions-List
            int randomNum = rand.nextInt((object.remainingOptions.size() - 1) + 1);
            ArrayList<Integer> arrayList = object.remainingOptions.get(randomNum);
            object.setTent(arrayList);
            field[arrayList.get(0)][arrayList.get(1)].setFieldType(IField.field_type.TENT);
            decreaseBorderLimit(arrayList.get(0), arrayList.get(1));
            return true;
        }
        return false;
    }


    private void deleteBlockingTent(TentsTreesObject object) {
        ArrayList<ArrayList<Integer>> arrayList = checkRemainingOptions(object);
        boolean cleanUp = false;
        while (arrayList.size() == 0) {
            while (!cleanUp) {
                int randomNum = rand.nextInt((4 - 1) + 1) + 1;
                switch (randomNum) {
                    case 1:
                        cleanUp = cleanUp(object.col + 1, object.row);
                        break;
                    case 2:
                        cleanUp = cleanUp(object.col - 1, object.row);
                        break;
                    case 3:
                        cleanUp = cleanUp(object.col, object.row + 1);
                        break;
                    case 4:
                        cleanUp = cleanUp(object.col, object.row - 1);
                        break;
                }
            }
        }
        object.remainingOptions = arrayList;
    }

    private boolean cleanUp(int col, int row) {

        boolean successfully = false;

        if (field[col][row].getFieldType() == IField.field_type.BORDER) {
            return false;
        }

        if (field[col][row].getFieldType() == IField.field_type.TREE) {
            return false;
        }

        if (field[col][row].getFieldType() == IField.field_type.BLOCKED) {
            return false;
        }

        if (colBorderLimit(col) == 0) {
            for (int i = 0; i < field[col].length; i++) {
                if (field[col][i].getFieldType() == IField.field_type.TENT) {
                    TentsTreesObject tree = getTreeFromTent(col, i);
                    field[col][i].setFieldType(IField.field_type.EMPTY);
                    increaseBorderLimit(col, i);
                    tree.deleteTent();
                    buildTents--;
                    successfully = true;
                }

            }
        }

        if (rowBorderLimit(row) == 0) {
            for (int i = 0; i < field.length; i++) {
                if (field[i][row].getFieldType() == IField.field_type.TENT) {
                    TentsTreesObject tree = getTreeFromTent(i, row);
                    field[i][row].setFieldType(IField.field_type.EMPTY);
                    increaseBorderLimit(i, row);
                    tree.deleteTent();
                    buildTents--;
                    successfully = true;
                }
            }
        }

        if (field[col + 1][row].getFieldType() == IField.field_type.TENT) {
            TentsTreesObject tree = getTreeFromTent(col + 1, row);
            field[col + 1][row].setFieldType(IField.field_type.EMPTY);
            increaseBorderLimit(col + 1, row);
            tree.deleteTent();
            buildTents--;
            successfully = true;
        }

        if (field[col - 1][row].getFieldType() == IField.field_type.TENT) {
            TentsTreesObject tree = getTreeFromTent(col - 1, row);
            field[col - 1][row].setFieldType(IField.field_type.EMPTY);
            increaseBorderLimit(col - 1, row);
            tree.deleteTent();
            buildTents--;
            successfully = true;
        }

        if (field[col][row + 1].getFieldType() == IField.field_type.TENT) {
            TentsTreesObject tree = getTreeFromTent(col, row + 1);
            field[col][row + 1].setFieldType(IField.field_type.EMPTY);
            increaseBorderLimit(col, row + 1);
            tree.deleteTent();
            buildTents--;
            successfully = true;
        }

        if (field[col][row - 1].getFieldType() == IField.field_type.TENT) {
            TentsTreesObject tree = getTreeFromTent(col, row - 1);
            field[col][row - 1].setFieldType(IField.field_type.EMPTY);
            increaseBorderLimit(col, row - 1);
            tree.deleteTent();
            buildTents--;
            successfully = true;
        }

        return successfully;
    }

    private ArrayList<ArrayList<Integer>> checkRemainingOptions(TentsTreesObject tentsTreesObject) {
        ArrayList<ArrayList<Integer>> remainingOptions = new ArrayList<>();

        // Under the tree
        if (checkConditionsForCoordinates(tentsTreesObject.col + 1, tentsTreesObject.row)) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(tentsTreesObject.col + 1);
            arrayList.add(tentsTreesObject.row);
            remainingOptions.add(arrayList);
        }

        // Above the tree

        if (checkConditionsForCoordinates(tentsTreesObject.col - 1, tentsTreesObject.row)) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(tentsTreesObject.col - 1);
            arrayList.add(tentsTreesObject.row);
            remainingOptions.add(arrayList);
        }

        // Right from tree

        if (checkConditionsForCoordinates(tentsTreesObject.col, tentsTreesObject.row + 1)) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(tentsTreesObject.col);
            arrayList.add(tentsTreesObject.row + 1);
            remainingOptions.add(arrayList);
        }

        // Left from tree

        if (checkConditionsForCoordinates(tentsTreesObject.col, tentsTreesObject.row - 1)) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(tentsTreesObject.col);
            arrayList.add(tentsTreesObject.row - 1);
            remainingOptions.add(arrayList);
        }

        return remainingOptions;
    }

    private boolean checkConditionsForCoordinates(int col, int row) {

        if (field[col][row].getFieldType() != IField.field_type.EMPTY) {
            return false;
        }

        if (colBorderLimit(col) == 0 || rowBorderLimit(row) == 0) {
            return false;
        }

        if (field[col + 1][row].getFieldType() == IField.field_type.TENT) {
            return false;
        }

        if (field[col - 1][row].getFieldType() == IField.field_type.TENT) {
            return false;
        }

        if (field[col][row + 1].getFieldType() == IField.field_type.TENT) {
            return false;
        }

        if (field[col][row - 1].getFieldType() == IField.field_type.TENT) {
            return false;
        }

        return true;
    }

    /**
     * Help methods
     */

    private boolean oneTentForEachTree() {
        for (TentsTreesObject object : trees) {
            if (!object.isTentBuild()) {
                return false;
            }
        }
        return true;
    }

    private int colBorderLimit(int col) {
        return field[col][0].getBorderLimit();
    }

    private int rowBorderLimit(int row) {
        return field[0][row].getBorderLimit();
    }

    private void decreaseBorderLimit(int col, int row) {
        field[col][0].setBorderLimit(field[col][0].getBorderLimit() - 1);
        field[0][row].setBorderLimit(field[0][row].getBorderLimit() - 1);
    }

    private void increaseBorderLimit(int col, int row) {
        field[col][0].setBorderLimit(field[col][0].getBorderLimit() + 1);
        field[0][row].setBorderLimit(field[0][row].getBorderLimit() + 1);
    }

    private TentsTreesObject getTreeFromTent(int col, int row) {
        for (TentsTreesObject object : trees) {
            if (object.isTentBuild()) {
                if (object.tent.get(0) == col && object.tent.get(1) == row) {
                    return object;
                }
            }
        }
        return null;
    }
}