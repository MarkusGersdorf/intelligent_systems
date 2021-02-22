package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Random strategy
 *
 * @author Markus gersdorf
 * @version 1.0
 */
public class RandomStrategy extends Strategy {

    /**
     * Basic constructor
     *
     * @param jobArrayList       a arrayList which contains all jobs
     * @param resourcesArrayList a arrayList which contains the usable machines.
     */
    public RandomStrategy(ArrayList<Job> jobArrayList, ArrayList<Long> resourcesArrayList) {
        super(jobArrayList, resourcesArrayList);
    }

    /**
     * This method is used to sort the jobArrayList by the duration of the operation.
     */
    @Override
    public void sort() {
        Collections.shuffle(jobArrayList, new Random()); // Generate a random ranking
    }
}
