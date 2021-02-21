package de.uol.is.tat;

import java.util.ArrayList;

/**
 * Within this class the used heuristics are implemented.
 *
 * @author Marcel Peplies
 */
public class Heuristics {
    ArrayList<TentsTreesObject> trees = new ArrayList<>();
    ArrayList<TentsTreesObject> progress = new ArrayList<TentsTreesObject>();
    IField[][] field;

    /**
     * This method is used to apply the heuristic "mostConstrainedVariable" to a matrix.
     *
     * @param matrix This is the initial matrix in which the heuristic is to be applied.
     * @return A completely revised solution is returned as a matrix.
     */
    public IField[][] mostConstrainedVariable(IField[][] matrix) {
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

        while (!oneTentForEachTree()) {
            for (TentsTreesObject object : trees) {
                if (object.remainingOptions.size() == currentRemainingOptionsLevel && !object.isTentBuild()) {
                    successfully = buildTent(object);
                    if (successfully) {
                        reCalculateRemainingOptions();
                        currentRemainingOptionsLevel = 0;
                        break;
                    } else {
                        reCalculateRemainingOptions();
                        currentRemainingOptionsLevel = 0;
                    }
                }
            }

            if (!successfully) {
                if (currentRemainingOptionsLevel < 4) {
                    currentRemainingOptionsLevel++;
                } else {
                    currentRemainingOptionsLevel = 0;
                }
            } else {
                successfully = false;
            }
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
     * This method tries to set a tent for the passed TentsTreesObject. If no possible options are available, the
     * process for resetting operations is triggered (resetProgress). If there are still possible options available,
     * they are selected in order and the necessary adjustments such as reducing the boundary values are made.
     *
     * @param object A TentsTreesObject for which a tent is to be set.
     * @return Returns false if no tent could be set and returns true if it was possible.
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

    /**
     * If the case arises that there are no more possibilities to set a tent for a tree, this method is called. The
     * method is based on backpropagation. First, the last tent that has been set is pulled from the Progress-ArrayList.
     * There are 3 possibilities which are considered within the method.
     *
     * 1st possibility: It is still the initial option, which has been used first. Then the very first option from the
     * associated ArrayList of the possible options is selected again.
     *
     * 2nd possibility: It is no longer the initial solution. It is looked how many solutions have been used and the
     * next possibility is checked.
     *
     * 3rd possibility: There are no further possibilities that can be selected as an alternative. Then the marquee is
     * deleted and all associated values are reset. In the Progress-ArrayList the next element is used.
     *
     * The process runs until the actual marquee, which could not be set and why this method was called, can be set.
     */
    private void resetProgress() {
        int progressSize = progress.size();
        for (int i = progressSize - 1; i > 0; i--) {
            TentsTreesObject object = progress.get(i);
            int remainingOptionsSize = object.remainingOptions.size();
            int choosedOption = object.getChoosedOption();
            if (remainingOptionsSize >= 1 && choosedOption == -1) {
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

    /**
     * For the passed object, a check is made to see where else a tent can be placed on the tree.
     *
     * @param tentsTreesObject A TentsTreesObject for which the options are to be checked.
     * @return Returns an ArrayList with ArrayLists, each containing 2 values for the Col and Row values for the
     * possible options.
     */
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

    /**
     * This method checks for passed Col and Row values whether a marquee may be set at this point. The consideration
     * happens under consideration of the conditions.
     *
     * @param col Column value of a field on which a tent may be placed.
     * @param row Row value of a field on which a tent may be placed.
     * @return Returns a boolean value whether a tent could be set or not.
     */
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
     * This method checks if there is a tent for each tree.
     *
     * @return Returns a boolean value whenever each tree had an tent.
     */
    private boolean oneTentForEachTree() {
        for (TentsTreesObject object : trees) {
            if (!object.isTentBuild()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the margin value, how many values may still be placed within the column.
     *
     * @param col A column value in which column the edge value is needed.
     * @return Returns the number how many tents may still be placed in the column.
     */
    private int colBorderLimit(int col) {
        return field[col][0].getBorderLimit();
    }

    /**
     * Returns the margin value, how many values may still be placed within the row.
     *
     * @param row A row value in which row the edge value is needed.
     * @return Returns the number how many tents may still be placed in the row.
     */
    private int rowBorderLimit(int row) {
        return field[0][row].getBorderLimit();
    }

    /**
     * Gets a column value and a row value and decrements the associated margin values.
     *
     * @param col Column value
     * @param row Row value
     */
    private void decreaseBorderLimit(int col, int row) {
        field[col][0].setBorderLimit(field[col][0].getBorderLimit() - 1);
        field[0][row].setBorderLimit(field[0][row].getBorderLimit() - 1);
    }

    /**
     * Gets a column value and a row value and increase the associated margin values.
     *
     * @param col Column value
     * @param row Row value
     */
    private void increaseBorderLimit(int col, int row) {
        field[col][0].setBorderLimit(field[col][0].getBorderLimit() + 1);
        field[0][row].setBorderLimit(field[0][row].getBorderLimit() + 1);
    }
}