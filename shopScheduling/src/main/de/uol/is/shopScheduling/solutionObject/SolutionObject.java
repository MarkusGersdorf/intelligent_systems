package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import de.uol.is.shopScheduling.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * This is a solution object. This solution object provides functions to output the solution visually in the console.
 *
 * @author Thomas Cwill, Markus Gersdorf, Joosten Steenhusen, Marcel Peplies
 * @version 2.0
 */
public abstract class SolutionObject {

    protected ArrayList<Job> jobArrayList;
    @Getter
    @Setter
    protected Schedule schedule;

    public SolutionObject(ArrayList<Job> jobArrayList, ArrayList<Long> resourcesArrayList) {
        this.jobArrayList = jobArrayList;
        schedule = new Schedule(resourcesArrayList);
    }


    /**
     * Calculate the finesse of the solution
     *
     * @param fitnessArrayList Solution for which the fitness is to be calculated
     * @return fitness value
     */
    @Deprecated
    protected int calcDuration(ArrayList<Resource> fitnessArrayList) {
        return 0;
    }

    /**
     * Job Shop Scheduling table
     */
    public void printToConsole() {

    }

    /**
     * Job Shop Scheduling diagram
     */
    public void printDiagram() {

    }

    /**
     * Check if one job (and his operations) is processed on one machine at a time
     *
     * @return True if one job (and his Operations) is processed on one machine at a time
     */
    protected boolean checkIneJobAtOnePointInTime(Operation operation) {
        boolean orderPreviousOk = true;
        boolean orderNextOk = true;

        Resource resource = schedule.getResource(operation.getResource());
        ArrayList<Operation> operationArrayList = schedule.getOperations(resource);

        if (operationArrayList == null) {
            System.out.printf("Error");
        }

        for (Operation op : operationArrayList) {
            Operation previousResourceOperation = schedule.getPreviousResourceOperation(op);
            Operation nextResourceOperation = schedule.getNextResourceOperation(op);

            if (previousResourceOperation != null) {
                orderPreviousOk = previousResourceOperation.getEndTime() < op.getStartTime();
            }

            if (nextResourceOperation != null) {
                orderNextOk = nextResourceOperation.getStartTime() > op.getEndTime();
            }
        }
        return orderPreviousOk && orderNextOk;
    }

    /**
     * Check if all operations are in ascending id order
     *
     * @return True if all operations are in the correct order.
     */
    protected boolean checkAscendingOperationOrder(Operation operation) {
        Operation nextOperation = schedule.getNextResourceOperation(operation);

        if (nextOperation != null) {
            return nextOperation.getStartTime() > operation.getEndTime();
        }
        return true; // there is no next operation
    }

    public long getMakespan() {
        return schedule.getMakespan();
    }

}
