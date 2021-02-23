package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;

import java.util.ArrayList;
import java.util.Collections;

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
     *
     * @param jobArrayList       a arrayList which contains all jobs
     * @param resourcesArrayList a arrayList which contains the usable machines.
     */
    public SptStrategy(ArrayList<Job> jobArrayList, ArrayList<Long> resourcesArrayList) {
        super(jobArrayList, resourcesArrayList);
    }

    /**
     * This method is used to sort the jobArrayList by the duration of the operation.
     *
     * @return
     */
    @Override
    public ArrayList<Operation> sort() {
        ArrayList<Operation> operationArrayList = new ArrayList<>();
        for (Job job : jobArrayList) {
            operationArrayList.addAll(job.getOperationArrayList());
        }
        Collections.sort(operationArrayList, Operation.sortByDuration);
        return operationArrayList;
    }
}