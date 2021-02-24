package de.uol.is.tat;

import java.io.File;
import java.util.ArrayList;

/**
 * This class is the main class of Task 2 (Tents & Trees). The program is started within this class.
 *
 * (V,D,C) = (
 * V = mxn Matrix (ArrayList<IField[][]>fields<IField[][]>,
 * D = {Border, Blocked, Tree, Tent, Free},
 *
 * C = (
 * 1: Im Umfeld von Tent und Tree darf nur jeweils einmal der andere vorkommen
 * 2: Tent muss horizontal oder vertikal am Baum liegen
 * 3: Zwischen Tent und Baum dürfen 0 Felder sein
 * 4: n Tents in einer Reihe
 * 5: Wenn Field = Tent, dann drumherum kein Tent
 * )
 * )
 *
 * @author Thomas Cwil
 * @author Joosten Steenhusen
 */
public class App {
    private static final IConstraints cons = new Constraints();
    private static final ICSVReader reader = new CSVReader();
    private static final String[] path = new String[10];
    private static ArrayList<IField[][]> initFields = new ArrayList<>();
    private static ArrayList<IField[][]> fields = new ArrayList<>();
    private static boolean constraintConform = true;

    /**
     * Main method
     */
    public static void main(String[] args) {
        int fieldNumber = 0;
        String projectPath = new File("").getAbsolutePath();
        String csvPath = projectPath.concat("\\tat\\src\\main\\resources\\");

        for (int i = 0; i < 10; i++) {
            path[i] = csvPath.concat("tents_trees_" + i + ".csv");
            fields.add(reader.convertCsvToFields(path[i]));
            initFields.add(reader.convertCsvToFields(path[i]));
        }

        for (IField[][] field : fields) {
            constraintConform = false;
            Heuristics heuristics = new Heuristics();
            IField[][] finish = heuristics.mostConstrainedVariable(field);
            overwriteBoarders(initFields.get(fieldNumber), finish);
            checkConstraints(finish);

            if (constraintConform) {
                System.out.println("Field " + fieldNumber + " \tErfüllt alle Constraints!");
            } else {
                System.out.println("Field " + fieldNumber + " \tErfüllt nicht alle Constraints!");
            }

            fieldsToConsole(field);
            fieldNumber++;
        }
    }

    /**
     * Initially, the data is read from the CSV files and transferred to a matrix, including the boundary values to
     * know how many tents may still be added to the respective horizontal or vertical row. However, within the
     * heuristics these values are changed. However, in order to be able to check the decisions made afterwards, the
     * initial boundary values must be restored in the result. This task is taken over by the method.
     *
     * @param field This is the initial untreated matrix.
     * @param finish This is the matrix in which the result has been generated.
     */
    private static void overwriteBoarders(IField[][] field, IField[][] finish) {
        for (int col = 0; col < field.length; col++) {
            finish[col][0] = field[col][0];
        }

        for (int row = 0; row < field[0].length; row++) {
            finish[0][row] = field[0][row];
        }
    }

    /**
     * The individual constraints are available in the 'Constraint' class. The task of this method is to iterate step by
     * step over the matrix and to check the conditions depending on the respective field type. If even one constraint
     * is not fulfilled, the global variable is set to false to adjust the later output.
     *
     * @param field This is the matrix in which the result has been generated.
     */
    private static void checkConstraints(IField[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].getFieldType() == IField.field_type.BORDER) {
                    continue;
                }
                IField[][] constraintField;
                if (field[i][j].getFieldType() == IField.field_type.TENT) {
                    constraintField = getWindowFromField(field, i, j);
                    if (!cons.noTentAroundTent(constraintField)) {
                        constraintConform = false;
                        break;
                    } else if (!cons.oneTreePerTent(constraintField)) {
                        constraintConform = false;
                        break;
                    } else if (!cons.onlyNTentsPerRow(field, i, j)) {
                        constraintConform = false;
                        break;
                    }

                }

                if (field[i][j].getFieldType() == IField.field_type.TREE) {
                    constraintField = getWindowFromField(field, i, j);
                    if (!cons.oneTentPerTree(constraintField)) {
                        constraintConform = false;
                        break;
                    }
                }
            }
        }
        constraintConform = true;
    }

    /**
     * This method is used to generate a section around a certain point within the matrix. The result is a 3 x 3 matrix
     * and in the center is the given point.
     *
     * @param field This is the matrix in which the result has been generated.
     * @param i     position column
     * @param j     position row
     * @return 3x3 field from type 2D Array
     */
    private static IField[][] getWindowFromField(IField[][] field, int i, int j) {
        IField[][] temp_field = new Field[3][3];

        temp_field[0][0] = field[i - 1][j - 1];
        temp_field[0][1] = field[i - 1][j];
        temp_field[0][2] = field[i - 1][j + 1];
        temp_field[1][0] = field[i][j - 1];
        temp_field[1][1] = field[i][j];
        temp_field[1][2] = field[i][j + 1];
        temp_field[2][0] = field[i + 1][j - 1];
        temp_field[2][1] = field[i + 1][j];
        temp_field[2][2] = field[i + 1][j + 1];

        return temp_field;
    }

    /**
     * A simple output to make the generated result visible via the console.
     *
     * @param field This is the matrix in which the result has been generated.
     */
    private static void fieldsToConsole(IField[][] field) {
        for (IField[] iFields : field) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(iFields[j].getFieldType() + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}

