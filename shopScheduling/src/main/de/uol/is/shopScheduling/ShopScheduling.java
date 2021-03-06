package de.uol.is.shopScheduling;

import de.uol.is.shopScheduling.optimizationMethods.EvolutionStrategy;
import de.uol.is.shopScheduling.strategys.FifoStrategy;
import de.uol.is.shopScheduling.strategys.RandomStrategy;
import de.uol.is.shopScheduling.strategys.SptStrategy;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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

        LinkedHashSet<Long> resourcesSet = new LinkedHashSet<>();
        ArrayList<Resource> resourceArrayList = new ArrayList<>();


        // now for each file generate java object
        try {
            for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
                //for (int i = 0; i < 1; i++) {
                System.out.println("Path: " + listOfFiles[0].getPath().substring(55));
                FifoStrategy fifoStrategy = new FifoStrategy(jsonParser.parseJsonJobs(listOfFiles[0]), jsonParser.parseJsonResources(listOfFiles[0]));
                //fifoStrategy.printDiagram();
                System.out.println("Fifo-Strategy: " + fifoStrategy.getMakespan());
                RandomStrategy randomStrategy = new RandomStrategy(jsonParser.parseJsonJobs(listOfFiles[0]), jsonParser.parseJsonResources(listOfFiles[0]));
                //randomStrategy.printDiagram();
                System.out.println("Random-Strategy: " + randomStrategy.getMakespan());
                SptStrategy sptStrategy = new SptStrategy(jsonParser.parseJsonJobs(listOfFiles[0]), jsonParser.parseJsonResources(listOfFiles[0]));
                //sptStrategy.printDiagram();
                System.out.println("Spt-Strategy: " + sptStrategy.getMakespan());
                System.out.println("---------ES-Start---------");
                EvolutionStrategy evolutionStrategy = new EvolutionStrategy(jsonParser.parseJsonJobs(listOfFiles[0]), jsonParser.parseJsonResources(listOfFiles[0]));
                System.out.println("ES-Strategy: " + evolutionStrategy.getMakespan());
                System.out.println("-------------------------------------------------------------------------------");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}
