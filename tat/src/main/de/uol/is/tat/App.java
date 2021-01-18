package de.uol.is.tat;

import java.io.File;
import java.util.ArrayList;


// TODO: VDC redefinieren
// TODO: Verkettung von Baum und Zelt (?)

/**
 * (V,D,C) = (
 * V = mxn Matrix (ArrayList<IField[][]>fields</IField[][]>,
 * D = {Border, Blocked, Tree, Tent, Free},
 * <p>
 * C = (
 * 1: Im Umfeld von Tent und Tree darf nur jeweils einmal der andere vorkommen
 * 2: Tent muss horizontal oder vertikal am Baum liegen
 * 3: Zwischen Tent und Baum dürfen 0 Felder sein
 * 4: n Tents in einer Reihe
 * 5: Wenn Field = Tent, dann drumherum kein Tent
 * )
 * <p>
 * )
 */

public class App {
    private static final String[] path = new String[12]; // Anzahl der einzulesenden csv Dateien - müssen Namenskonvention einhalten
    private static final ICSVReader reader = new CSVReader();
    private static ArrayList<IField[][]> fields = new ArrayList<>();
    private static final IConstraints cons = new Constraints();
    private static boolean constraintConform = true;
    private static Heuristics heuristics = new Heuristics();

    public static void main(String[] args) {
        String projectPath = new File("").getAbsolutePath();
        String csvPath = projectPath.concat("/tat/csv/");

        for (int i = 0; i < 12; i++) {
            path[i] = csvPath.concat("tents_trees_" + i + ".csv");
            fields.add(reader.convertCsvToFields(path[i]));
        }
        int fieldnumber = 0;

        fieldsToConsole(fields);
        for (IField[][] field : fields) {
            constraintConform = true;
            if(fieldnumber == 0) {
                heuristics = new Heuristics();
                heuristics.mostConstrainedVariable(field, fieldnumber);
            }
            checkConstraints(field);
            // wenn nicht alle constraints erfüllt werden, heuristik rekursiv aufrufen
            if (constraintConform) {
                System.out.println("Field " + fieldnumber + " \tErfüllt alle Constraints!");
            } else {
                System.out.println("Field " + fieldnumber + " \tErfüllt nicht alle Constraints!");
            }
            fieldnumber++;
        }
        System.out.println("---------------------------------------");
        fieldsToConsole(fields);
    }

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
    }

    /**
     * Get 3x3 Window from field
     *
     * @param field field
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
     * Print fields to console
     *
     * @param fields complete map
     */
    private static void fieldsToConsole(ArrayList<IField[][]> fields) {
        for (IField[][] f : fields) {
            for (IField[] iFields : f) {
                for (int j = 0; j < f[0].length; j++) {
                    System.out.print(iFields[j].getFieldType() + "\t");
                }
                System.out.println();
            }
        }
    }
}
