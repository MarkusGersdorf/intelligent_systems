package de.uol.is.shopScheduling;

import java.util.Comparator;

/**
 * This class represents an operation that is necessary to process a workpiece
 *
 * @author markusgersdorf
 * @version 0.1
 */
public class Operation implements Cloneable {
    private final long index;
    private final long duration;
    private final long resource;
    private final long jobId;
    private long startTime;
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
     * Sort Operations in ascending order by index
     */
    public static Comparator<Operation> sortByIndex = (obj1, obj2) -> {
        //sort in ascending order
        return (int) (obj1.index - obj2.index);
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	public long getDuration() {
		return this.duration;
	}

	public long getIndex() {
		return this.index;
	}

	public long getEndTime() {
		return this.endTime;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public long getResource() {
		return this.resource;
	}

	public long getJobId() {
		return this.jobId;
	}

	public void setStartTime(long l) {
		this.startTime = l;
	}

	public void setEndTime(long l) {
		this.endTime = l;
	}
}
