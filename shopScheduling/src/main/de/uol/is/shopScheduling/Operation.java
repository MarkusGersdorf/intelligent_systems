package de.uol.is.shopScheduling;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

/**
 * This class represents an operation that is necessary to process a workpiece
 *
 * @author markusgersdorf
 * @version 0.1
 */
public class Operation {
    @Getter
    private final long index;
    @Getter
    private final long duration;
    @Getter
    private final long resource;
    @Getter
    private final long jobId;
    @Getter
    @Setter
    private long startTime;
    @Getter
    @Setter
    private long endTime;


    public Operation(long index, long duration, long resource, long jobId) {
        this.index = index;
        this.duration = duration;
        this.resource = resource;
        this.jobId = jobId;
    }

    /**
     * Sort Operations in ascending order by jobId
     */
    public static Comparator<Operation> sortByJobId = (obj1, obj2) -> {
        //sort in ascending order
        return (int) (obj1.jobId - obj2.jobId);
    };
    /**
     * Sort Operations in ascending order by duration
     */
    public static Comparator<Operation> sortByDuration = (obj1, obj2) -> {
        //sort in ascending order
        return (int) (obj1.duration - obj2.duration);
    };

    /**
     * This Operation works at point in time
     *
     * @param pointInTime point in time
     * @return true if this operation works at point in time
     */
    public boolean operationExists(long pointInTime) {
        return startTime <= pointInTime && endTime >= pointInTime;
    }

}
