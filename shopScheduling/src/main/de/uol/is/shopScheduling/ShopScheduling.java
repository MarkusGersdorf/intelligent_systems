package de.uol.is.shopScheduling;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ShopScheduling {
    public static void main(String[] args) {

        JsonParser jsonParser = new JsonParser();

        // get absolut project path
        String project_path = new File("").getAbsolutePath();

        // search for all json files and save them
        File folder = new File(project_path.concat("/shopScheduling/src/main/resources/benchmark_problems"));
        File[] listOfFiles = folder.listFiles();

        // save all jobs into this list
        ArrayList<ArrayList<Job>> listArrayList = new ArrayList<>();

        // now for each file generate java object
        try {
            for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
                listArrayList.add(jsonParser.parseJsonResources(listOfFiles[i]));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}