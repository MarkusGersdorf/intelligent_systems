package de.uol.is.shopScheduling;

import java.util.Set;

public interface ISchedule {
    void addOperation(Operation operation);

    void addOperation(int index, Operation operation);

    void removeOperation(Operation operation);

    Operation getPreviousOperation(Operation operation);

    Operation getNextOperation(Operation operation);
}
