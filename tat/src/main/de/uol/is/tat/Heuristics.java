package de.uol.is.tat;

import java.util.ArrayList;

public class Heuristics {
    ArrayList<TentsTreesObject> trees = new ArrayList<>();
    ArrayList<TentsTreesObject> progress = new ArrayList<TentsTreesObject>();
    IField[][] field;

    public IField[][] mostConstrainedVariable(IField[][] matrix, int fieldnumber) {
        this.field = matrix;
        int currentRemainingOptionsLevel = 1;
        boolean successfully = false;

        for (int col = 1; col < field.length; col++) {
            for (int row = 1; row < field[col].length; row++) {
                if (field[col][row].getFieldType() == IField.field_type.TREE) {
                    TentsTreesObject tentsTreesObject = new TentsTreesObject(col, row);
                    tentsTreesObject.updateRemainingOptions(checkRemainingOptions(tentsTreesObject));
                    trees.add(tentsTreesObject);
                }
            }
        }

        // Only used for 10.csv & 11.csv
        preprocessing(field);

        while (!oneTentForEachTree()) {
            for(TentsTreesObject object : trees) {
                if (object.remainingOptions.size() == currentRemainingOptionsLevel && !object.isTentBuild()) {
                    successfully = buildTent(object);
                    if(successfully) {
                        reCalculateRemainingOptions();
                        currentRemainingOptionsLevel = 0;
                        break;
                    } else {
                        reCalculateRemainingOptions();
                        currentRemainingOptionsLevel = 0;
                    }
                }
            }

            if(!successfully) {
                if(currentRemainingOptionsLevel < 4) {
                    currentRemainingOptionsLevel++;
                } else {
                    currentRemainingOptionsLevel = 0;
                }
            } else {
                successfully = false;
            }
            //System.out.println("Aktuell läuft die " + fieldnumber + ". CSV-Datei. Bäume mit Zelt: " + progress.size() + " (" + trees.size() + ")");
        }

        return field;
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
            resetProgress();
            return false;
        }
        int choosedOption = object.getChoosedOption();
        ArrayList<Integer> arrayList = object.remainingOptions.get(choosedOption);
        object.setTent(arrayList);
        field[arrayList.get(0)][arrayList.get(1)].setFieldType(IField.field_type.TENT);
        decreaseBorderLimit(arrayList.get(0), arrayList.get(1));
        progress.add(object);
        return true;
    }


    private void resetProgress() {
        int progressSize = progress.size();
        for (int i = progressSize - 1; i > 0; i--) {
            TentsTreesObject object = progress.get(i);
            int remainingOptionsSize = object.remainingOptions.size();
            int choosedOption = object.getChoosedOption();
            if(remainingOptionsSize >= 1 && choosedOption == - 1) {
                object.deleteTent();
                object.setChoosedOption(object.getChoosedOption() + 1);
                object.setTent(object.remainingOptions.get(object.choosedOption));
                field[object.tent.get(0)][object.tent.get(1)].setFieldType(IField.field_type.TENT);
                decreaseBorderLimit(object.tent.get(0), object.tent.get(1));
                break;
            } else if (remainingOptionsSize > 1 && choosedOption + 1 != remainingOptionsSize) {
                field[object.tent.get(0)][object.tent.get(1)].setFieldType(IField.field_type.EMPTY);
                increaseBorderLimit(object.tent.get(0), object.tent.get(1));
                object.setChoosedOption(object.getChoosedOption() + 1);
                object.setTent(object.remainingOptions.get(object.choosedOption));
                field[object.tent.get(0)][object.tent.get(1)].setFieldType(IField.field_type.TENT);
                decreaseBorderLimit(object.tent.get(0), object.tent.get(1));
                break;
            } else {
                field[object.tent.get(0)][object.tent.get(1)].setFieldType(IField.field_type.EMPTY);
                increaseBorderLimit(object.tent.get(0), object.tent.get(1));
                object.setChoosedOption(0);
                object.deleteTent();
                progress.remove(i);
            }
        }

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

    private void preprocessing(IField[][] field) {
        for(int col = 1; col < field.length; col++) {
            for(int row = 1; row < field[col].length; row++) {
                if(row == 20 && col == 13) {
                    boolean wait = true;
                }
                if(row == 22 && col == 13) {
                    boolean wait = true;
                }
                if(field[col][row].getFieldType() == IField.field_type.TENT) {
                    ArrayList<TentsTreesObject> treeObjects = new ArrayList<>();

                    // Under the tree
                    if(field[col + 1][row].getFieldType() == IField.field_type.TREE) {
                        TentsTreesObject object = getTreeFromCoordinates(col + 1, row);
                        if(!object.isTentBuild()) {
                            treeObjects.add(object);
                        }
                    }

                    // Above the tree
                    if(field[col - 1][row].getFieldType() == IField.field_type.TREE) {
                        TentsTreesObject object = getTreeFromCoordinates(col  - 1, row);
                        if(!object.isTentBuild()) {
                            treeObjects.add(object);
                        }
                    }

                    // Right from tree
                    if(field[col][row + 1].getFieldType() == IField.field_type.TREE) {
                        TentsTreesObject object = getTreeFromCoordinates(col, row + 1);
                        if(!object.isTentBuild()) {
                            treeObjects.add(object);
                        }
                    }

                    // Left from tree
                    if(field[col][row - 1].getFieldType() == IField.field_type.TREE) {
                        TentsTreesObject object = getTreeFromCoordinates(col, row - 1);
                        if(!object.isTentBuild()) {
                            treeObjects.add(object);
                        }
                    }

                    if(treeObjects.size() == 1) {
                        if(treeObjects.get(0) != null) {
                            ArrayList<Integer> arrayList = new ArrayList<>();
                            arrayList.add(col);
                            arrayList.add(row);
                            treeObjects.get(0).setTent(arrayList);
                            treeObjects.get(0).setChoosedOption(-1);
                            progress.add(treeObjects.get(0));
                        }
                    }
                    if(treeObjects.size() > 1) {
                        ArrayList<ArrayList<ArrayList<Integer>>> arrayList = new ArrayList<>();

                        // Check for each tree, are their other remaining starter tents
                        int objectCounter = 0;
                        for(TentsTreesObject object : treeObjects) {
                            ArrayList<ArrayList<Integer>> tentsForTrees = new ArrayList<>();

                            // Counter to identify with tree are checked
                            ArrayList<Integer> objectCounterArrayList = new ArrayList<>();
                            objectCounterArrayList.add(objectCounter);
                            tentsForTrees.add(objectCounterArrayList);

                            // Check for other tents for this tree
                            if(field[object.col + 1][object.row].getFieldType() == IField.field_type.TENT &&
                                    object.col + 1 != col &&
                                    object.row != row) {
                                ArrayList<Integer> coordinates = new ArrayList<>();
                                coordinates.add(object.col + 1);
                                coordinates.add(object.row);
                                tentsForTrees.add(coordinates);
                            }

                            if(field[object.col - 1][object.row].getFieldType() == IField.field_type.TENT &&
                                    object.col - 1 != col &&
                                    object.row != row) {
                                ArrayList<Integer> coordinates = new ArrayList<>();
                                coordinates.add(object.col - 1);
                                coordinates.add(object.row);
                                tentsForTrees.add(coordinates);
                            }

                            if(field[object.col][object.row + 1].getFieldType() == IField.field_type.TENT &&
                                    object.col != col &&
                                    object.row + 1 != row) {
                                ArrayList<Integer> coordinates = new ArrayList<>();
                                coordinates.add(object.col);
                                coordinates.add(object.row + 1);
                                tentsForTrees.add(coordinates);
                            }

                            if(field[object.col][object.row - 1].getFieldType() == IField.field_type.TENT &&
                                    object.col != col &&
                                    object.row - 1!= row) {
                                ArrayList<Integer> coordinates = new ArrayList<>();
                                coordinates.add(object.col);
                                coordinates.add(object.row - 1);
                                tentsForTrees.add(coordinates);
                            }

                            arrayList.add(tentsForTrees);
                            objectCounter++;
                        }

                        for(ArrayList<ArrayList<Integer>> tentsForTrees : arrayList) {
                            if(tentsForTrees.size() > 1) {
                                objectCounter = tentsForTrees.get(0).get(0);
                                TentsTreesObject object = treeObjects.get(objectCounter);
                                if(getTreeFromTent(tentsForTrees.get(1).get(0), tentsForTrees.get(1).get(1)) == null) {
                                    object.tent = tentsForTrees.get(1);
                                    object.setChoosedOption(-1);
                                    progress.add(object);
                                }
                            } else {
                                objectCounter = tentsForTrees.get(0).get(0);
                                TentsTreesObject object = treeObjects.get(objectCounter);
                                if(getTreeFromTent(col, row) == null) {
                                    ArrayList<Integer> coordinates = new ArrayList<>();
                                    coordinates.add(col);
                                    coordinates.add(row);
                                    object.tent = coordinates;
                                    object.setChoosedOption(-1);
                                    progress.add(object);
                                }
                            }
                        }
                    }
                }
            }
        }
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

    private TentsTreesObject getTreeFromCoordinates(int col, int row) {
        for(TentsTreesObject object : trees) {
            if(object.col == col && object.row == row) {
                return object;
            }
        }
        return null;
    }
}