package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Resource;
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

    private long bestMakespan;

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

        RandomStrategy best = new RandomStrategy(jobArrayList, resourcesArrayList);
        bestMakespan = best.getMakespan();

        ArrayList<Strategy> strategies = new ArrayList<>();

        System.out.println("ES-init Fitness: " + bestMakespan);

        while (searchCounter < 1) {
            searchCounter++;
            ArrayList<Job> test = jobArrayList;
            Collections.copy(test, jobArrayList);

            // generate mutation
            RandomStrategy mutation = new RandomStrategy(jobArrayList, resourcesArrayList);

            long mutationMakespan = mutation.getMakespan();

            if (mutationMakespan <= bestMakespan) {
                strategies.add(mutation);
                bestMakespan = mutationMakespan;
                System.out.println("Fitness: " + best.getMakespan());
            }
        }
        if (strategies.size() > 0) {
            schedule = strategies.get(strategies.size() - 1).getSchedule();
        } else {
            schedule = best.getSchedule();
        }
    }

    public long get_best_makespan() { return this.bestMakespan; }

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
