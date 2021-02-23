package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Resource;
import de.uol.is.shopScheduling.strategys.RandomStrategy;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Evolutionary 1+1 algorithm
 *
 * @author Markus Gersdorf
 * @version 1.0
 */
public class EvolutionStrategy extends Algorithm {

    private ArrayList<Long> resourcesArrayList;

    /**
     * Basic constructor
     *
     * @param jobArrayList       a arrayList which contains all jobs
     * @param resourcesArrayList a arrayList which contains the usable machines.
     */
    public EvolutionStrategy(ArrayList<Job> jobArrayList, ArrayList<Long> resourcesArrayList) {
        super(jobArrayList, resourcesArrayList);
        this.resourcesArrayList = resourcesArrayList;
        optimize();
    }

    /**
     * In this method we implement our Evolution Strategy
     * We implement a simple 1+1 ES
     */
    @Override
    protected void optimize() {
        int searchCounter = 0;

        RandomStrategy best = new RandomStrategy(jobArrayList, resourcesArrayList);

        while (searchCounter < 10) {
            searchCounter++;
            ArrayList<Job> test = jobArrayList;
            Collections.copy(test, jobArrayList);
            // generate mutation

            RandomStrategy mutation = new RandomStrategy(jobArrayList, resourcesArrayList);
            System.out.println("System still working");
            // override old Array List

            if (mutation.getMakespan() <= best.getMakespan()) {
                best = mutation;
                System.out.println("Fitness: " + best.getMakespan());
            }
        }
    }

    /**
     * mutated population
     *
     * @param resourcesOld old population
     * @return mutate population
     */
    private ArrayList<Resource> mutation(ArrayList<Resource> resourcesOld) {


        return null;
    }
}
