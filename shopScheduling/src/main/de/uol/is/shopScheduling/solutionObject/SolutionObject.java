package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Queue;

/**
 * This is a solution object. This solution object provides functions to output the solution visually in the console.
 *
 * @author Thomas Cwill, Markus Gersdorf, Joosten Steenhusen, Marcel Peplies
 * @version 2.0
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class SolutionObject {

    protected ArrayList<Job> jobArrayList;
    @Getter
    protected ArrayList<Resource> resourceArrayList;

    /**
     * Calculate the finesse of the solution
     *
     * @param fitnessArrayList Solution for which the fitness is to be calculated
     * @return fitness value
     */
    protected int calcDuration(ArrayList<Resource> fitnessArrayList) {
        long minDuration = Long.MAX_VALUE;
        long maxDuration = Long.MIN_VALUE;

        // calculate for each resource max start and end time find maximal range
        for (Resource resource : fitnessArrayList) {
            for (Operation operation : resource.getOperations()) {
                minDuration = Long.min(minDuration, operation.getStartTime());
                maxDuration = Long.max(maxDuration, operation.getEndTime());
            }
        }
        return (int) (maxDuration - minDuration);
    }

    /**
     * Job Shop Scheduling table
     */
    public void printToConsole() {
        // Tabelle nach Job sortiert
        System.out.print("JobID\t\t");
        Job job = jobArrayList.get(0);
        int steps = 0;
        for (Operation op : job.getOperationArrayList()) {
            System.out.print(" Step " + steps + " \t");
            steps += 1;
        }
        System.out.println();
        for (Job j : jobArrayList) {
            System.out.print(j.getId() + "\t\t\t");
            for (Operation op : j.getOperationArrayList()) {
                System.out.print(" " + op.getResource() + "," + op.getDuration() + " \t\t");
            }
            System.out.println();
        }
    }

    /**
     * Job Shop Scheduling diagram
     */
    public void printDiagram() {
        long minDuration = Long.MAX_VALUE;
        long maxDuration = Long.MIN_VALUE;

        for (Resource resource : resourceArrayList) {
            for (Operation operation : resource.getOperations()) {
                minDuration = Long.min(minDuration, operation.getStartTime());
                maxDuration = Long.max(maxDuration, operation.getEndTime());
            }
        }

        for (Resource resource : resourceArrayList) {
            for (int i = (int) minDuration; i < (int) maxDuration; i++) {
                String resourceString = resource.getOperation(i) == -1.0 ? " " : "" + resource.getOperation(i) + "";
                System.out.print("|" + StringUtils.leftPad(resourceString + "|\t", 6, " "));
            }
            System.out.println();
        }
        for (int i = (int) minDuration; i < (int) maxDuration; i++) {
            System.out.print("|" + StringUtils.leftPad(i + "|\t", 6, "0"));
        }
    }

    /**
     * Check for constraints
     *
     * @return true, if all constraints are fulfilled
     */
    protected boolean check_for_constraints(ArrayList<Resource> resourceArrayList) {
        return one_job_at_a_time(resourceArrayList) && check_ascending_operation_order(resourceArrayList);
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
    protected boolean check_ascending_operation_order(ArrayList<Resource> resourceArrayList) {
        for (Job job : jobArrayList) {
            long jobId = job.getId();
            long plannedTime = 0;
            for (Operation operation : job.getOperationArrayList()) {
                long resourceId = operation.getResource();
                for (Operation operationInResource : getOperationsListFromResource(resourceId)) {
                    if (operationInResource.getJobId() == jobId) {
                        if (plannedTime > operationInResource.getStartTime()) {
                            return false;
                        } else {
                            plannedTime = operationInResource.getStartTime();
                        }
                    }
                }
            }
        }
        return true;
    }

    public Queue<Operation> getOperationsListFromResource(long resourceId) {
        for (Resource resource : resourceArrayList) {
            if (resource.getId() == resourceId) {
                return resource.getOperationQueue();
            }
        }
        return null;
    }
}
