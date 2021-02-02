package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;

/**
 * This class implements the STP strategy. This is a strategy in which the jobs that have the shortest processing time
 * are scheduled first.
 *
 * @author Joosten Steenhusen & Marcel Peplies
 * @since 02.02.2021
 */
public class SptStrategy extends Strategy {

    /**
     * Basic constructor
     * @param jobArrayList a arrayList which contains all jobs
     * @param resource a arrayList which contains the usable machines.
     */
    public SptStrategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        super(jobArrayList, resource);
        sort();
        start();
    }

    /**
     * In this method, the strategy is applied and the result or solution is produced.
     * After sorting the jobs by duration, the contained operations are assigned to the machines in order.
     */
    public void start() {
        for(Job job : jobArrayList) {
            for(Operation operation : job.getOperationArrayList()) {
                getResource(operation.getResource()).addOperation(operation);
            }
        }
    }

    /**
     * This method is overridden from the super class. The method is used to output a table with the solutions.
     */
    @Override
    public void print() {

    }

    /**
     * This method is used to sort the jobArrayList by the duration of the operation.
     */
    public void sort() {
        jobArrayList.sort((j1, j2) -> {
            return (int) (j1.duration() - j2.duration()); // Ascending
        });
    }
}