package de.uol.is.shopScheduling;

import java.util.ArrayList;

public interface ISchedule {
    void addOperation(Operation operation);

    void addOperation(int index, Operation operation);

    void removeOperation(Operation operation);

    Operation getPreviousOperation(Operation operation);

    Operation getNextOperation(Operation operation);

    Operation getPreviousJobOperation(Operation operation);

    Operation getNextJobOperation(Operation operation);

    Long durationFromTo(Operation startOperation, Operation endOperation);

    Long durationTo(Operation endOperation, boolean inclusive);

    Long durationTo(Operation endOperation);

    Long getMakespan(Resource resource);

    Long getMakespan();

    Resource getResource(Long resourceId);

    ArrayList<Operation> getOperations(Long resourceId);

}
