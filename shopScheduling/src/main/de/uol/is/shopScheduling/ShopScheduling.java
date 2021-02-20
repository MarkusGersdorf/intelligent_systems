package de.uol.is.shopScheduling;

import de.uol.is.shopScheduling.solutionObject.Algorithm;
import de.uol.is.shopScheduling.solutionObject.EvolutionStrategy;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.TreeSet;

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
                listArrayList.add(jsonParser.parseJsonResources(listOfFiles[i]));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // search for resources and generate them
        for (ArrayList<Job> jobs : listArrayList) {

            // search resources - for each operation in each job - to generate resources
            resourcesSet.clear();
            resourceArrayList.clear();

            for (Job job : jobs) {
                for (Operation operation : job.getOperationArrayList()) {
                    resourcesSet.add(operation.getResource());
                }
            }

            // generate resources - sort set before
            // TODO: We don't need to sort the set
            new TreeSet<>(resourcesSet).forEach(resource -> resourceArrayList.add(new Resource("Resource" + resource.toString(), resource)));

            // call different strategies
            // Strategy strategy = new FifoStrategy(jobs, resourceArrayList);
            //Strategy spt = new SptStrategy(jobs, resourceArrayList);
            //Strategy fifo = new FifoStrategy(jobs, resourceArrayList);
            //Strategy random = new RandomStrategy(jobs, resourceArrayList);
            Algorithm algorithm = new EvolutionStrategy(jobs, resourceArrayList);

        }
    }
}
