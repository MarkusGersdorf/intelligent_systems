package de.uol.is.shopScheduling;

import de.uol.is.shopScheduling.optimizationMethods.EvolutionStrategy;
import de.uol.is.shopScheduling.strategys.FifoStrategy;
import de.uol.is.shopScheduling.strategys.RandomStrategy;
import de.uol.is.shopScheduling.strategys.SptStrategy;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ShopScheduling {
    public static void main(String[] args) {

        JsonParser jsonParser = new JsonParser();

        // get absolut project path
        String project_path = new File("").getAbsolutePath();

        // search for all json files and save them
        File folder = new File(project_path.concat("/shopScheduling/src/main/resources/benchmark_problems"));
        File[] listOfFiles = folder.listFiles();

        //CSVWriter writer = null;
        BufferedWriter writer = null;
        try {

            writer = Files.newBufferedWriter(Paths.get(project_path.concat("/shopScheduling/src/main/resources/benchmark_problems.csv")));
            writer.write("id,fifo,random,spt,es");
            writer.newLine();
        } catch (Exception ignored) {

        }
        // now for each file generate java object
        try {
            for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
                //for (int i = 0; i < 2; i++) {
                System.out.println("Path: " + listOfFiles[i].getPath().substring(55));
                FifoStrategy fifoStrategy = new FifoStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
                //fifoStrategy.printDiagram();
                long fifoMakeSpan = fifoStrategy.getMakespan();
                System.out.println("Fifo-Strategy: " + fifoMakeSpan);
                RandomStrategy randomStrategy = new RandomStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
                //randomStrategy.printDiagram();
                long randomMakeSpan = randomStrategy.getMakespan();
                System.out.println("Random-Strategy: " + randomMakeSpan);
                SptStrategy sptStrategy = new SptStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
                //sptStrategy.printDiagram();
                long sptMakeSpan = sptStrategy.getMakespan();
                System.out.println("Spt-Strategy: " + sptMakeSpan);
                System.out.println("---------ES-Start---------");
                EvolutionStrategy evolutionStrategy = new EvolutionStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
                long esMakeSpan = evolutionStrategy.getMakespan();
                System.out.println("ES-Strategy: " + esMakeSpan);
                System.out.println("-------------------------------------------------------------------------------");

                assert writer != null;
                writer.write(i + "," + fifoMakeSpan + "," + randomMakeSpan + "," + sptMakeSpan + "," + esMakeSpan);
                writer.newLine();
            }
            assert writer != null;
            writer.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}
