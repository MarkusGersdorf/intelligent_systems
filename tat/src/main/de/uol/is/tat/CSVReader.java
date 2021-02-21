package de.uol.is.tat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Read in a cdv file and create the individual fields
 *
 * @author Thomas Cwill
 * @version 0.1
 */
public class CSVReader implements ICSVReader {
    private BufferedReader br = null;
    private String line = "";
    private String csvSplitBy = ",";
    private int x;
    private int y;

    /**
     * Covert csv file to field array
     *
     * @param path from csv file
     * @return field with data from csv
     */
    public IField[][] convertCsvToFields(String path) {
        boolean setY = false;
        try {
            x = 0;
            y = 0;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (!setY) {
                    y = 0;
                }
                x++;
                String[] field = line.split(csvSplitBy);

                for (String s : field) {
                    if (!setY) {
                        y++;
                    }
                }
                setY = true;
            }

            IField[][] fields = new IField[(x + 1)][(y + 1)];

            // Initialize fields with IField.field_type.EMPTY
            for (int i = 0; i < fields.length; i++) {
                for (int j = 0; j < fields[0].length; j++) {
                    fields[i][j] = new Field(IField.field_type.EMPTY);
                }
            }

            // Overwrite fields at the border (left right top bottom) with IField.field_type.BORDER
            for (int i = 0; i < fields[0].length; i++) {
                fields[0][i] = new Field(IField.field_type.BORDER);
                fields[fields.length - 1][i] = new Field(IField.field_type.BORDER);
            }
            for (int i = 0; i < fields.length; i++) {
                fields[i][0] = new Field(IField.field_type.BORDER);
                fields[i][fields[0].length - 1] = new Field(IField.field_type.BORDER);
            }

            // Read CSV and adjust fields in fields array
            x = 0;
            y = 0;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                y = 0;
                String[] field = line.split(csvSplitBy);
                for (String s : field) {
                    if (!s.equals("") && Character.isDigit(s.charAt(0))) {
                        fields[x][y].setBorderLimit(Integer.parseInt(s));
                    } else if (s.equals("t")) {
                        fields[x][y] = new Field(IField.field_type.TREE);
                    } else if (s.equals("x")) {
                        fields[x][y] = new Field(IField.field_type.TENT);
                    }
                    y++;
                }
                x++;
            }
            return fields;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
