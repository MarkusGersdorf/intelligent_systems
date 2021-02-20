package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;
import de.uol.is.shopScheduling.solutionObject.SolutionObject;

import java.util.ArrayList;

/**
 * Superclass, which is to be used by all heuristics
 *
 * @author Joosten Steenhusen, Marcel Peplies
 */
public abstract class Strategy extends SolutionObject {

    /**
     * constructor calls the functions for sorting and planning the heuristics directly after creating the class
     *
     * @param jobArrayList List of jobs
     * @param resource     List of resources
     */
    public Strategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        this.jobArrayList = jobArrayList;
        this.resourceArrayList = resource;
        sort();
        planning();
        //printToConsole();
        //printDiagram();
    }

    /**
     * Implemented by the heuristic and by this it is specified in which order
     * the jobs are selected by the scheduling function
     */
    protected abstract void sort();

    /**
     * The planning function uses the list sorted by the heuristics
     * to create a plan. This function implements all dependencies
     */
    protected void planning() {
        // TODO-Marcel&Joosten: Ihr hattet noch änderungen an dieser funktion. Pflegt die bitte ein :-)
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

                    while(!hinzugefuegt) {
                        for (Operation operationInMaschine : machine.getOperations()) {
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
