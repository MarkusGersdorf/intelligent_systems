package de.uol.is.shopScheduling;

import java.util.ArrayList;

/**
 * Interface for scheduler
 *
 * @author Thomas Cwill
 * @version 1.0
 */
public interface ISchedule {

    void addOperation(Operation operation);

    void addOperation(int index, Operation operation);

    void removeOperation(Operation operation);

    Operation getPreviousResourceOperation(Operation operation);

    Operation getNextResourceOperation(Operation operation);

    Operation getPreviousJobOperation(Operation operation);

    Operation getNextJobOperation(Operation operation);

    long durationFromTo(Operation startOperation, Operation endOperation);

    long durationTo(Operation endOperation, boolean inclusive);

    long durationTo(Operation endOperation);

    long durationFrom(Operation endOperation, boolean inclusive);

    long durationFrom(Operation endOperation);

    long getMakespan(Resource resource);

    long getMakespan();

    Resource getResource(long resourceId);

    ArrayList<Operation> getOperations(long resourceId);

}
