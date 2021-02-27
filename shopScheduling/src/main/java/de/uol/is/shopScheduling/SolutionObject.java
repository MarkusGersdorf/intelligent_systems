package de.uol.is.shopScheduling;

import java.util.ArrayList;

/**
 * This is a solution object. This solution object provides functions to output the solution visually in the console.
 *
 * @author Thomas Cwill, Markus Gersdorf, Joosten Steenhusen, Marcel Peplies
 * @version 2.0
 */
public abstract class SolutionObject {

    protected ArrayList<Job> jobArrayList;
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
        schedule.print_to_console();
    }

    /**
     * Job Shop Scheduling diagram
     */
    public void printDiagram() {
        schedule.printDiagram();
    }

    /**
     * Check if one job (and his operations) is processed on one machine at a time
     *
     * @return True if one job (and his Operations) is processed on one machine at a time
     */
    protected boolean checkIneJobAtOnePointInTime(Operation operation) {
        boolean orderPreviousOk = true;
        boolean orderNextOk = true;

        for (Operation op : schedule.getOperations(schedule.getResource(operation.getResource()))) {
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
    
    public Schedule getSchedule() {
    	return this.schedule;
    }

}
