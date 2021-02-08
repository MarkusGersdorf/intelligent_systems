package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;
import java.util.Queue;

public abstract class Strategy {

    protected ArrayList<Job> jobArrayList;
    ArrayList<Resource> resourceArrayList;

    public Strategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        this.jobArrayList = jobArrayList;
        this.resourceArrayList = resource;
    }

    public void print() {
        for (Resource resource : resourceArrayList) {
            ArrayList<Operation> op = sortByStartTime(resource.getOperations());
            StringBuilder output = new StringBuilder(resource.getName() + ": ");
            for (Operation operation : op) {
                output.append("[").append(operation.getStartTime()).append(", ").append(operation.getEndTime()).append("] ");
            }
            System.out.println(output);
        }
        System.out.println("---------------------------------");
    }

    /**
     * This method is used to sort the queue of a operationsList in a resource by the start time
     */
    public ArrayList<Operation> sortByStartTime(Queue<Operation> queue) {
        int size = queue.size();
        int counter = 0;
        ArrayList<Operation> sortedOuput = new ArrayList<>();
        while (counter < size) {
            long smallest = -1;
            Operation next = null;
            for (Operation operation : queue) {
                if (smallest == -1) {
                    smallest = operation.getStartTime();
                    next = operation;
                }
                if (operation.getStartTime() < smallest) {
                    smallest = operation.getStartTime();
                    next = operation;
                }
            }
            sortedOuput.add(next);
            queue.remove(next);
            counter++;
        }
        return sortedOuput;
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

}
