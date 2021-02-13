package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
public abstract class SolutionObject {

    protected ArrayList<Job> jobArrayList;
    protected ArrayList<Resource> resourceArrayList;

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
}
