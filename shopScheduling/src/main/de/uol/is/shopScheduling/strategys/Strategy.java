package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public abstract class Strategy {

    protected ArrayList<Job> jobArrayList;
    protected ArrayList<Resource> resourceArrayList;

    public Strategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        this.jobArrayList = jobArrayList;
        this.resourceArrayList = resource;
    }

    public void print() {
        //System.out.println("Print strategy");
        printDiagram();
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
                System.out.print("|" + StringUtils.leftPad(resource.getOperation(i) + "|\t", 7, "X"));
            }
            System.out.println();
        }
        for (int i = (int) minDuration; i < (int) maxDuration; i++) {
            System.out.print("|" + StringUtils.leftPad(i + "|\t", 6, "0"));
        }
    }

    protected void planning() {
        //System.out.println("New planning");
        for (Job job : jobArrayList) {
            long verplanteZeit = 0;
            for (Operation operation : job.getOperationArrayList()) {
                Resource machine = resourceArrayList.get((int) operation.getResource());
                long dauerDerOperation = operation.getDuration();

                if (machine.getOperations().size() == 0) {
                    operation.setStartTime(verplanteZeit);
                    operation.setEndTime(verplanteZeit + dauerDerOperation);
                    verplanteZeit += dauerDerOperation;
                    machine.addOperation(operation);
                } else {
                    boolean hinzugefügt = false;
                    boolean blockiert = false;

                    while (!hinzugefügt) {
                        //System.out.println(verplanteZeit);
                        for (Operation operationInMaschine : machine.getOperations()) {
                            if ((verplanteZeit > operationInMaschine.getStartTime() && verplanteZeit < operationInMaschine.getEndTime()) ||
                                    ((verplanteZeit + dauerDerOperation) > operationInMaschine.getStartTime() && (verplanteZeit + dauerDerOperation) < operationInMaschine.getEndTime())) {
                                verplanteZeit = operationInMaschine.getEndTime();
                                blockiert = true;
                                break;
                            }
                        }

                        if (!blockiert) {
                            operation.setStartTime(verplanteZeit);
                            operation.setEndTime(verplanteZeit + operation.getDuration());
                            machine.addOperation(operation);
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
}
