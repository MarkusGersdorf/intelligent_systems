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

    public void print_to_console() {
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

        // Tabelle nach Ressource sortiert
        /*
        System.out.print("RessourceID\t");
        Resource res = resourceArrayList.get(0);
        int steps = 0;
        for (Operation op : res.getOperations()) {
            System.out.print(" Step " + steps + " \t");
            steps += 1;
        }
        System.out.println();
        for (Resource r : resourceArrayList) {
            System.out.print(r.getId() + "\t\t\t");
            for (Operation op : r.getOperations()) {
                System.out.print(" " + op.getResource() + "," + op.getDuration() + " \t\t");
            }
            System.out.println("");
        }
        */
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
                String resourceString = resource.getOperation(i) == -1.0 ? " " : "" + i + "";
                System.out.print("|" + StringUtils.leftPad(resourceString + "|\t", 6, " "));
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
                    boolean hinzugefuegt = false;
                    boolean blockiert = false;

                    while (!hinzugefuegt) {
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
                            hinzugefuegt = true;
                        } else {
                            blockiert = false;
                        }
                    }
                }
            }
        }
    }

}
