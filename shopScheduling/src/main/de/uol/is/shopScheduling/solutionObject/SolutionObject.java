package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * This is a solution object. This solution object provides functions to output the solution visually in the console.
 *
 * @author Thomas Cwill, Markus Gersdorf
 * @version 1.0
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
        return true;
    }

        @// TODO: 20.02.2021  1. In der Maschine nach überschreitungen schauen || 2. Reihenfolge im Job checken.


    /**
     * Check if one job (and his operations) is processed on one machine at a time
     * @return
     */
    protected boolean one_job_at_a_time() {
        return true;
    }

    /**
     * Check if all operations are in ascending id order
     * @return
     */
    protected boolean check_ascending_operation_order() {
        return true;
    }
}
