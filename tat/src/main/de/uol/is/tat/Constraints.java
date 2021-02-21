package de.uol.is.tat;

/**
 * This class offers several functions to check if all necessary dependencies and rules are fulfilled.
 *
 * @author Joosten Steenhusen
 * @version 0.1
 */
public class Constraints implements IConstraints {

    /**
     * Check for this tree if a tent is available
     *
     * @param f current field
     * @return true when a neighbor is a tent
     */
    @Override
    public boolean oneTentPerTree(IField[][] f) {
        return f[0][1].getFieldType() == IField.field_type.TENT ||
                f[1][0].getFieldType() == IField.field_type.TENT ||
                f[1][2].getFieldType() == IField.field_type.TENT ||
                f[2][1].getFieldType() == IField.field_type.TENT;
    }

    /**
     * Check for this tent if a tree is available
     *
     * @param f current field
     * @return true when a neighbor is a tree
     */
    @Override
    public boolean oneTreePerTent(IField[][] f) {
        return f[0][1].getFieldType() == IField.field_type.TREE ||
                f[1][0].getFieldType() == IField.field_type.TREE ||
                f[1][2].getFieldType() == IField.field_type.TREE ||
                f[2][1].getFieldType() == IField.field_type.TREE;
    }

    /**
     * Is the number of tents in a row respected?
     *
     * @param f      field
     * @param fieldI position column
     * @param fieldJ position row
     * @return true if constraint fulfills
     */
    @Override
    public boolean onlyNTentsPerRow(IField[][] f, int fieldI, int fieldJ) {
        int row_limit = f[fieldI][0].getBorderLimit();
        int column_limit = f[0][fieldJ].getBorderLimit();

        int number_of_tents_row = 0;
        int number_of_tents_column = 0;

        for (int i = 0; i < f[0].length; i++) {
            if (f[fieldI][i].getFieldType() == IField.field_type.TENT) {
                number_of_tents_row++;
            }
        }

        for (IField[] iFields : f) {
            if (iFields[fieldJ].getFieldType() == IField.field_type.TENT) {
                number_of_tents_column++;
            }
        }

        return row_limit == number_of_tents_row && column_limit == number_of_tents_column;
    }

    /**
     * There is no tent available around this tree
     *
     * @param f current field
     * @return true when no tent available
     */
    @Override
    public boolean noTentAroundTent(IField[][] f) {

        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f.length; j++) {
                if (i == 1 && j == 1) {
                    continue;
                }
                if (f[i][j].getFieldType() == IField.field_type.TENT) {
                    return false;
                }
            }
        }
        return true;
    }
}
