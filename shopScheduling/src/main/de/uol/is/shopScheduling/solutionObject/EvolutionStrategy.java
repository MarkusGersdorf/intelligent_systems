package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;

/**
 * Evolutionary 1+1 algorithm
 *
 * @author Markus Gersdorf
 * @version 1.0
 */
public class EvolutionStrategy extends Algorithm {


    /**
     * Basic constructor
     *
     * @param jobArrayList a arrayList which contains all jobs
     * @param resource     a arrayList which contains the usable machines.
     */
    public EvolutionStrategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        super(jobArrayList, resource);
    }

    /**
     * In this method we implement our Evolution Strategy
     * We implement a simple 1+1 ES
     */
    @Override
    protected void optimize() {
        int searchCounter = 0;

        // initialize
        resourceArrayList = initialize(jobArrayList);

        while (searchCounter < 1000) {
            searchCounter++;
            // generate mutation
            ArrayList<Resource> mutation = mutation(resourceArrayList);

            // override old Array List
            if (calcFitness(resourceArrayList) <= calcFitness(mutation)) {
                resourceArrayList = new ArrayList<>(mutation);
            }
        }
    }

    /**
     * Calculate the finesse of the solution
     *
     * @param fitnessArrayList Solution for which the fitness is to be calculated
     * @return fitness value
     */
    private int calcFitness(ArrayList<Resource> fitnessArrayList) {
        long minDuration = Long.MAX_VALUE;
        long maxDuration = Long.MIN_VALUE;

        // calculate for each resource max start and end time find maximal range
        for (Resource resource : fitnessArrayList) {
            for (Operation operation : resource.getOperations()) {
                minDuration = Long.min(minDuration, operation.getStartTime());
                maxDuration = Long.max(maxDuration, operation.getEndTime());
            }
        }
        return (int) (maxDuration - minDuration);
    }

    /**
     * mutated population
     *
     * @param resourcesOld old population
     * @return mutate population
     */
    private ArrayList<Resource> mutation(ArrayList<Resource> resourcesOld) {

        return new ArrayList<>();
    }
}
