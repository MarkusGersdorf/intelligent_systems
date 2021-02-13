package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import de.uol.is.shopScheduling.solutionObject.SolutionObject;

import java.util.ArrayList;

public abstract class Strategy extends SolutionObject {

    public Strategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        this.jobArrayList = jobArrayList;
        this.resourceArrayList = resource;
        sort();
        planning();
        printToConsole();
        printDiagram();
    }

    protected abstract void sort();

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
