package de.uol.is.shopScheduling.optimizationMethods;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.strategys.RandomStrategy;
import de.uol.is.shopScheduling.strategys.Strategy;

import java.util.ArrayList;
import java.util.Collections;

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
     * @param jobArrayList       a arrayList which contains all jobs
     * @param resourcesArrayList a arrayList which contains the usable machines.
     */
    public EvolutionStrategy(ArrayList<Job> jobArrayList, ArrayList<Long> resourcesArrayList) {
        super(jobArrayList, resourcesArrayList);
        optimize(resourcesArrayList);
    }

    /**
     * In this method we implement our Evolution Strategy
     * We implement a simple 1+1 ES
     */
    @Override
    protected void optimize(ArrayList<Long> resourcesArrayList) {
        int searchCounter = 0;

        // init solution
        RandomStrategy best = new RandomStrategy(jobArrayList, resourcesArrayList);
        long bestMakespan = best.getMakespan();

        ArrayList<Strategy> strategies = new ArrayList<>();
        System.out.println("ES-init Fitness: " + bestMakespan);

        while (searchCounter < 100) {
            searchCounter++;
            ArrayList<Job> test = jobArrayList;
            Collections.copy(test, jobArrayList);

            // generate mutation
            RandomStrategy mutation = new RandomStrategy(jobArrayList, resourcesArrayList);

            long mutationMakespan = mutation.getMakespan();

            if (mutationMakespan <= bestMakespan) {
                strategies.add(mutation); // add solutions to a list
                bestMakespan = mutationMakespan;
                System.out.println("Fitness: " + best.getMakespan());
            }
        }
        if (strategies.size() > 0) {
            schedule = strategies.get(strategies.size() - 1).getSchedule(); // select last solution
        } else {
            schedule = best.getSchedule(); // select init solution
        }
    }
}
