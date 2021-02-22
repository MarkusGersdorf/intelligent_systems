package de.uol.is.shopScheduling;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to parse json files
 *
 * @author markusgersdorf
 * @version 0.1
 */
public class JsonParser {

    /**
     * A file is processed here. Each job is saved with the associated work
     *
     * @param fileName file to be processed
     * @return A list of jobs to be performed
     * @throws IOException    while the file is being processed
     * @throws ParseException while the file is being processed
     */
    public ArrayList<Job> parseJsonJobs(File fileName) throws IOException, ParseException {
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader(fileName));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting firstName and lastName
        JSONArray jobs = (JSONArray) jo.get("jobs");

        // save all jobs from one file into this list
        ArrayList<Job> jobArrayList = new ArrayList<>();

        /*
        In this part the necessary operations for all jobs are searched and stored.
        In addition, the id is stored for each job.
         */
        jobs.stream().iterator().forEachRemaining(e -> {
            JSONObject jsonObject = (JSONObject) e;
            JSONArray operations = (JSONArray) jsonObject.get("operations");

            // this list contains all jobs from one task
            ArrayList<Operation> operationArrayList = new ArrayList<>();
            jobArrayList.add(new Job((long) jsonObject.get("id"), operationArrayList));

            operations.stream().iterator().forEachRemaining(o -> {
                JSONObject jsonOperation = (JSONObject) o;

                long index = (long) jsonOperation.get("index");
                long duration = (long) jsonOperation.get("duration");
                long resource = (long) jsonOperation.get("resource");

                operationArrayList.add(new Operation(index, duration, resource, (long) jsonObject.get("id")));
            });
        });
        return jobArrayList;
    }

    /**
     * A file is processed here. Each resources is saved with the associated work
     *
     * @param fileName file to be processed
     * @return A list of jobs to be performed
     * @throws IOException    while the file is being processed
     * @throws ParseException while the file is being processed
     */
    public ArrayList<Long> parseJsonResources(File fileName) throws IOException, ParseException {
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader(fileName));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting firstName and lastName
        JSONArray jobs = (JSONArray) jo.get("resources");

        // save all jobs from one file into this list
        ArrayList<Long> resourcesArrayList = new ArrayList<>();

        jobs.stream().iterator().forEachRemaining(e -> {
            JSONObject jsonObject = (JSONObject) e;
            resourcesArrayList.add((Long) jsonObject.get("id"));

        });

        return resourcesArrayList;
    }
}
