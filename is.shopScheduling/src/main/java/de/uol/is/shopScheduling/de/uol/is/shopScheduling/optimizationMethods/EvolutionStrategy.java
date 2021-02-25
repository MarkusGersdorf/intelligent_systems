package de.uol.is.shopScheduling.optimizationMethods;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Schedule;
import de.uol.is.shopScheduling.strategys.RandomStrategy;

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

        ArrayList<Schedule> strategies = new ArrayList<>();
        System.out.println("ES-init Fitness: " + bestMakespan);

        while (searchCounter < 10) {
            searchCounter++;

            // generate mutation
            RandomStrategy mutation = new RandomStrategy(jobArrayList, resourcesArrayList);

            long mutationMakespan = mutation.getMakespan();

            if (mutationMakespan <= bestMakespan) {
                try {
                    strategies.add((Schedule) mutation.getSchedule().clone()); // add solutions to a list
                    bestMakespan = mutationMakespan;
                    System.out.println("Fitness: " + bestMakespan);
                } catch (Exception ignored) {

                }
            }
        }
        if (strategies.size() > 0) {
            schedule = strategies.get(strategies.size() - 1); // select last solution
        } else {
            try {
                schedule = (Schedule) best.getSchedule().clone(); // select init solution
            } catch (Exception ignored) {

            }
        }
    }
}
