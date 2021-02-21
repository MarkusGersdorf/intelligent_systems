package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;

/**
 * Fifo strategy
 *
 * @author Markus Gersdorf, Thomas Cwill
 * @version 1.0
 */
public class FifoStrategy extends Strategy {

    /**
     * Basic constructor
     *
     * @param jobArrayList a arrayList which contains all jobs
     * @param resource     a arrayList which contains the usable machines.
     */
    public FifoStrategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        super(jobArrayList, resource);
    }

    /**
     * This method is used to sort the jobArrayList by the duration of the operation.
     */
    @Override
    public void sort() {
        jobArrayList.sort((j1, j2) -> {
            return (int) (j1.getId() - j2.getId()); // Ascending
        });
    }
}
