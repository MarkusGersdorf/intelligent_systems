package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;

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
     *
     * @return sorted list of operations with ascending order by random
     */
    @Override
    public ArrayList<Operation> sort() {
        ArrayList<Operation> operationArrayList = new ArrayList<>();
        for (Job job : jobArrayList) {
            operationArrayList.addAll(job.getOperationArrayList());
        }
        Collections.shuffle(operationArrayList, new Random()); // Generate a random ranking
        return operationArrayList;
    }
}
