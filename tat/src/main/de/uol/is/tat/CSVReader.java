package de.uol.is.tat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class CSVReader {
    private BufferedReader br = null;
    private String line = "";
    private String csvSplitBy = ",";
    private int x;
    private int y;

    public IField[][] convert_csv_to_fields(String path) {
        boolean setY = false;
        try{
            x = 0;
            y = 0;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if(!setY) {
                    y = 0;
                }
                x++;
                String[] field = line.split(csvSplitBy);

                for (String s : field) {
                    if(!setY) {
                        y++;
                    }
                }
                setY = true;
            }

            IField[][] fields = new IField[(x+1)][(y+1)];
            // Felder mit IField.field_type.EMPTY initialisieren
            for(int i = 0; i < fields.length; i++)
            {
                for(int j = 0; j < fields[0].length; j++)
                {
                    fields[i][j] = new Field(IField.field_type.EMPTY);
                }
            }

            // Felder am Rand (links rechts oben unten) mit IField.field_type.BORDER überschreiben
            for (int i = 0; i < fields[0].length; i++) {
                fields[0][i] = new Field(IField.field_type.BORDER);
                fields[fields.length-1][i] = new Field(IField.field_type.BORDER);
            }
            for (int i = 0; i < fields.length; i++){
                fields[i][0] = new Field(IField.field_type.BORDER);
                fields[i][fields[0].length-1] = new Field(IField.field_type.BORDER);
            }

            // CSV einlesen und Felder im fields-Array anpassen
            x = 0;
            y = 0;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                y = 0;
                String[] field = line.split(csvSplitBy);
                for (String s : field) {
                    if(s.equals("t")) {
                        fields[x][y] = new Field(IField.field_type.TREE);
                    }
                    if(!s.equals("") && Character.isDigit(s.charAt(0))) {
                        fields[x][y].set_border_limit(Integer.parseInt(s));
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
