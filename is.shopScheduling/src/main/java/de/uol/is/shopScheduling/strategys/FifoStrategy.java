package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;

import java.util.ArrayList;

/**
 * Fifo strategy
 *
 * @author Markus Gersdorf, Thomas Cwil
 * @version 1.0
 */
public class FifoStrategy extends Strategy {

    /**
     * Basic constructor
     *
     * @param jobArrayList       a arrayList which contains all jobs
     * @param resourcesArrayList a arrayList which contains the usable machines.
     */
    public FifoStrategy(ArrayList<Job> jobArrayList, ArrayList<Long> resourcesArrayList) {
        super(jobArrayList, resourcesArrayList);
    }

    /**
     * This method is used to sort the jobArrayList by the duration of the operation.
     * <p>
     * * @return sorted list of operations with ascending order by jobId
     */
    @Override
    public ArrayList<Operation> sort() {
        ArrayList<Operation> operationArrayList = new ArrayList<>();
        for (Job job : jobArrayList) {
            operationArrayList.addAll(job.getOperationArrayList());
        }
        operationArrayList.sort(Operation.sortByJobId);
        return operationArrayList;
    }
}
