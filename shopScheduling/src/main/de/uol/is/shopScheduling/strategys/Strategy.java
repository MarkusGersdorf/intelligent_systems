package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import de.uol.is.shopScheduling.solutionObject.SolutionObject;

import java.util.ArrayList;

/**
 * Superclass, which is to be used by all heuristics
 *
 * @author Joosten Steenhusen, Marcel Peplies
 */
public abstract class Strategy extends SolutionObject {

    /**
     * constructor calls the functions for sorting and planning the heuristics directly after creating the class
     *
     * @param jobArrayList       List of jobs
     * @param resourcesArrayList List of resources
     */
    public Strategy(ArrayList<Job> jobArrayList, ArrayList<Long> resourcesArrayList) {
        super(jobArrayList, resourcesArrayList);
        planning();
        for (int i = 0; i < 9; i++) {
            for (Operation o : schedule.getOperations((long) i)) {
                check_ascending_operation_order(o);
            }
        }
    }

    /**
     * Implemented by the heuristic and by this it is specified in which order
     * the jobs are selected by the scheduling function
     */
    protected abstract void sort();

    /**
     * The planning function uses the list sorted by the heuristics
     * to create a plan. This function implements all dependencies
     */
    protected void planning() {
        for (Job job : jobArrayList) {
            for (Operation operation : job.getOperationArrayList()) {
                schedule.addOperationToResource(schedule.getResource(operation.getResource()), operation);
                // TODO: implementieren
            }
        }
    }
}
