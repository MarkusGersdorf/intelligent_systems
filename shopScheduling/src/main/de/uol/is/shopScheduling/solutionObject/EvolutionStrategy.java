package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

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

        while (searchCounter < 1000) {
            searchCounter++;
            // generate mutation
            ArrayList<Resource> mutation = mutation(resourceArrayList);

            // override old Array List
            if (calcDuration(resourceArrayList) <= calcDuration(mutation)) {
                resourceArrayList = new ArrayList<>(mutation);
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
        ArrayList<Resource> solutionList = new ArrayList<>(resourcesOld);

        // do while because we want to mutate one time
        do {
            for (Resource resource : solutionList) {
                // queue to array list
                ArrayList<Operation> operationArrayList = new ArrayList<>(resource.getOperationQueue());
                // shuffle list
                Collections.shuffle(operationArrayList);
                // set queue with new values
                resource.setOperationQueue(new LinkedList<>(operationArrayList));
            }
        } while (!check_for_constraints(solutionList));

        return solutionList;
    }
}
