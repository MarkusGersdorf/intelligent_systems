package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;

public abstract class Strategy {

    protected ArrayList<Job> jobArrayList;
    ArrayList<Resource> resourceArrayList;

    public Strategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        this.jobArrayList = jobArrayList;
        this.resourceArrayList = resource;
    }

    public void print() {
        //System.out.println("Print strategy");
    }

    public Resource getResource(long resourceNumber) {
        if (resourceArrayList.get((int) resourceNumber).getId() == resourceNumber) {
            return resourceArrayList.get((int) resourceNumber);
        } else {
            for (Resource resource : resourceArrayList) {
                if (resource.getId() == resourceNumber) {
                    return resource;
                }
            }
        }
        return null;
    }

    protected void planning() {
        //System.out.println("New planning");
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

                    while (!hinzugefügt) {
                        //System.out.println(verplanteZeit);
                        for (Operation operationInMaschine : maschine.getOperations()) {
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

}
