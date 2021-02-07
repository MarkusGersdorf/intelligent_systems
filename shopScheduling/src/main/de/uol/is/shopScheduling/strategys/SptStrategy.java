package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;

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
     * @param jobArrayList a arrayList which contains all jobs
     * @param resource     a arrayList which contains the usable machines.
     */
    public SptStrategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        super(jobArrayList, resource);
        sort();
        planning();
        boolean test = true;
    }

    /**
     * This method is overridden from the super class. The method is used to output a table with the solutions.
     */
    @Override
    public void print() {

    }

    public void planning() {
        System.out.println("New planning");
        for (Job job : jobArrayList) {
            long verplanteZeit = 0;
            for (Operation operation : job.getOperationArrayList()) {
                Resource maschine = getResource(operation.getResource());
                long dauerDerOperation = operation.getDuration();

                if (maschine.getOperations().size() == 0) {
                    operation.setStartTime(verplanteZeit);
                    operation.setEndTime(verplanteZeit + dauerDerOperation);
                    verplanteZeit += dauerDerOperation;
                    maschine.addOperation(operation);
                } else {
                    boolean hinzugefügt = false;
                    boolean blockiert = false;

                    while(!hinzugefügt) {
                        System.out.println(verplanteZeit);
                        for (Operation operationInMaschine : maschine.getOperations()) {
                            if ((verplanteZeit > operationInMaschine.getStartTime() && verplanteZeit < operationInMaschine.getEndTime()) ||
                                    ((verplanteZeit + dauerDerOperation) > operationInMaschine.getStartTime() && (verplanteZeit + dauerDerOperation) < operationInMaschine.getEndTime()) ||
                                    (operationInMaschine.getStartTime() > verplanteZeit && operationInMaschine.getStartTime() < (verplanteZeit + dauerDerOperation)) ||
                                    (operationInMaschine.getEndTime() > verplanteZeit && operationInMaschine.getEndTime() < (verplanteZeit + dauerDerOperation)) ||
                                    (verplanteZeit == operationInMaschine.getStartTime() && verplanteZeit + dauerDerOperation == operationInMaschine.getEndTime())) {
                                verplanteZeit = operationInMaschine.getEndTime();
                                blockiert = true;
                                break;
                            }
                        }

                        if(!blockiert) {
                            operation.setStartTime(verplanteZeit);
                            operation.setEndTime(verplanteZeit + operation.getDuration());
                            maschine.addOperation(operation);
                            verplanteZeit += operation.getDuration();
                            hinzugefügt = true;
                        } else {
                            blockiert = false;
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is used to sort the jobArrayList by the duration of the operation.
     */
    public void sort() {
        jobArrayList.sort((j1, j2) -> {
            return (int) (j1.duration() - j2.duration()); // Ascending
        });
    }
}