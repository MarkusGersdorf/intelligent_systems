package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import de.uol.is.shopScheduling.Schedule;

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

    }

    /**
     * Job Shop Scheduling diagram
     */
    public void printDiagram() {

    }

    /**
     * Check for constraints
     *
     * @return true, if all constraints are fulfilled
     */
    protected boolean check_for_constraints(ArrayList<Resource> resourceArrayList) {


        //return one_job_at_a_time(resourceArrayList) && check_ascending_operation_order(resourceArrayList);
        return false;
    }


    /**
     * Check if one job (and his operations) is processed on one machine at a time
     *
     * @return True if one job (and his Operations) is processed on one machine at a time
     */
    protected boolean one_job_at_a_time(ArrayList<Resource> resourceArrayList) {
        for (Resource resource : resourceArrayList) {
            for (Operation operation : resource.getOperations()) {
                for (Operation operation_check : resource.getOperations()) {
                    // If the same Job = break.
                    if (operation.getJobId() == operation_check.getJobId()) {
                        break;
                    }

                    // Same start or end point not allowed
                    if (operation.getStartTime() == operation_check.getStartTime() || operation.getEndTime() == operation_check.getEndTime()) {
                        return false;
                    }

                    // Other Operation begins before the Start and ends in the middle of the Operation = not allowed.
                    if (operation.getStartTime() > operation_check.getStartTime() && operation.getStartTime() < operation_check.getEndTime()) {
                        return false;
                    }

                    // Other Operation begins before the End and goes over the end of the Operation = not allowed.
                    if (operation.getEndTime() > operation_check.getStartTime() && operation.getEndTime() < operation_check.getEndTime()) {
                        return false;
                    }

                    // Other Operation starts and ends between the Operation = not allowed.
                    if (operation.getStartTime() > operation_check.getStartTime() && operation.getEndTime() > operation_check.getEndTime()) {
                        return false;
                    }

                    // Other Operation starts before the Operation and ends after the Operation = not allowed
                    if (operation.getStartTime() < operation_check.getStartTime() && operation.getEndTime() < operation_check.getEndTime()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check if all operations are in ascending id order
     *
     * @return True if all operations are in the correct order.
     */
    protected boolean check_ascending_operation_order(Operation operation) {
        Operation nextOperation = schedule.getNextOperation(operation);

        if (nextOperation != null) {
            return nextOperation.getStartTime() < operation.getEndTime();
        }
        return false;
    }

}
