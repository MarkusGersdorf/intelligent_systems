package de.uol.is.tat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



// TODO: FieldType Border eine Variable "ConstraintNumberOfTents" hinzufügen
// TODO: Fields miteinander verketten

/**
 *
 * (V,D,C) = (
 * V = mxn Matrix (ArrayList<IField[][]>fields</IField[][]>,
 * D = {Border, Blocked, Tree, Tent, Free},
 *
 * C = (
 * 1: Im Umfeld von Tent und Tree darf nur jeweils einmal der andere vorkommen
 * 2: Tent muss horizontal oder vertikal am Baum liegen
 * 3: Zwischen Tent und Baum dürfen 0 Felder sein
 * 4: n Tents in einer Reihe
 * 5: Wenn Field = Tent, dann drumherum kein Tent
 * )
 *
 * )
 */

public class App {
    private static final String[] path = new String[10]; // Anzahl der einzulesenden csv Dateien - müssen Namenskonvention einhalten
    private static CSVReader reader = new CSVReader();
    private static ArrayList<IField[][]> fields = new ArrayList<>();
    private static IConstraints cons;

    public static void main(String[] args) throws IOException {
        String project_path = new File("").getAbsolutePath();
        String csv_path = project_path.concat("/tat/csv/");

        for(int i = 0; i < 10; i++) {
            path[i] = csv_path.concat("tents_trees_" + i + ".csv");
            fields.add(reader.convert_csv_to_fields(path[i]));
        }
        fields_to_console(fields);

        cons = new Constraints(fields);
    }

    private static void fields_to_console(ArrayList<IField[][]> fields)
    {
        for(IField[][] f : fields) {
            for (IField[] iFields : f) {
                for (int j = 0; j < f[0].length; j++) {
                    System.out.print(iFields[j].get_field_type() + "\t");
                }
                System.out.println();
            }
        }

    }
}
