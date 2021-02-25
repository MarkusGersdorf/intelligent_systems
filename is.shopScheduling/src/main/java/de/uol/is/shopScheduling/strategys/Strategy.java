package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import de.uol.is.shopScheduling.SolutionObject;

import java.util.ArrayList;
import java.util.ListIterator;

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
        planning(sort());
        checkConstraints(true);
    }

    /**
     * Implemented by the heuristic and by this it is specified in which order
     * the jobs are selected by the scheduling function
     */
    protected abstract ArrayList<Operation> sort();

    /**
     * The planning function uses the list sorted by the heuristics
     * to create a plan. This function implements all dependencies
     */
    protected void planning(ArrayList<Operation> operationArrayList) {

        ListIterator<Operation> listIterator = operationArrayList.listIterator();
        while (listIterator.hasNext()) {
            Operation operation = listIterator.next();
            if (operation.getIndex() == 0 || schedule.getPreviousJobOperation(operation) != null) {
                schedule.addOperationToResource(operation);
                listIterator.remove(); // Removes operation from list
                listIterator = operationArrayList.listIterator(); // start at item 0 again
            }
        }
    }


    /**
     * Check constraints from scheduling plan
     *
     * @param print when true error messages will be printed
     * @return true if constraints success else false
     */
    protected boolean checkConstraints(boolean print) {
        for (Resource resource : schedule.getResources()) {
            for (Operation o : schedule.getOperations(resource)) {
                if (!checkAscendingOperationOrder(o)) {
                    if (print) {
                        System.err.println("Ascending order not ok!" + " Startpoint: " + o.getStartTime() + " - OperationId: " + o.getIndex() + " - JobId: " + o.getJobId());
                    }
                    return false;
                }
                if (!checkIneJobAtOnePointInTime(o)) {
                    if (print) {
                        System.err.println("There is more as one job in one time!" + " Startpoint: " + o.getStartTime() + " - OperationId: " + o.getIndex() + " - JobId: " + o.getJobId());
                    }
                }
            }
        }
        return true;
    }
}
