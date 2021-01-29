package de.uol.is.shopScheduling;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

public class Resource {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private long id;

    private Queue<Operation> operationQueue = new LinkedList<>();

    public Resource(String name, long id) {
        this.name = name;
        this.id = id;
    }

    private void addOperation(Operation operation) {
        operationQueue.add(operation);
    }

    private Operation getOperation() {
        return operationQueue.poll();
    }

    private int getNumberOfOperations() {
        return operationQueue.size();
    }

    private Queue<Operation> getOperations() {
        return new LinkedList<>(operationQueue);
    }

    private long getDuration() {
        long duration = 0L;
        for (Operation operation : operationQueue) {
            duration += operation.getDuration();
        }
        return duration;
    }
}
