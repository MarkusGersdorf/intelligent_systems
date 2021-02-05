package de.uol.is.shopScheduling;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

/**
 * A job contains an id and a certain number of operations, which are necessary to perform a machining operation.
 *
 * @author markusgersdorf
 * @version 0.1
 */
@AllArgsConstructor
public class Job {

    @Getter
    private final long id;

    private final ArrayList<Operation> operationArrayList;

    public ArrayList<Operation> getOperationArrayList() {
        return new ArrayList<>(operationArrayList);
    }

    /**
     * Duration of the entire job
     *
     * @return duration from all operations
     */
    public long duration() {
        long duration = 0L;
        for (Operation operation : operationArrayList) {
            duration += operation.getDuration();
        }
        return duration;
    }

}
