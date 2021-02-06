package de.uol.is.shopScheduling;

import lombok.Getter;
import lombok.Setter;

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
    @Setter
    private long startTime;
    @Getter
    @Setter
    private long endTime;

    public Operation(long index, long duration, long resource) {
        this.index = index;
        this.duration = duration;
        this.resource = resource;
    }

    public boolean operationExists(long pointInTime) {
        for (int i = (int) startTime; i < (int) endTime; i++) {
            if (i == pointInTime) return true;
        }
        return false;
    }
}
