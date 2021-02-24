package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
     * @return sorted list of operations with ascending order by duration
     */
    @Override
    public ArrayList<Operation> sort() {
        Map<Long, ArrayList<Operation>> map = new TreeMap<>();
        ArrayList<Operation> operationArrayList = new ArrayList<>();

        for (Job job : jobArrayList) {
            // key is already in map, add operations to this key
            if (map.get(job.duration()) != null) {
                map.get(job.duration()).addAll(job.getOperationArrayList());
                map.get(job.duration()).sort(Operation.sortByIndex);
            } else {
                job.getOperationArrayList().sort(Operation.sortByIndex);
                map.put(job.duration(), job.getOperationArrayList());
            }
        }
        for (Long l : map.keySet()) {
            operationArrayList.addAll(map.get(l));
        }
        return operationArrayList;
    }
}