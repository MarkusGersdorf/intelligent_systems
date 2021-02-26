package de.uol.is.shopScheduling;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A job contains an id and a certain number of operations, which are necessary to perform a machining operation.
 *
 * @author markusgersdorf
 * @version 0.1
 */
public class Job {

    private long id;

    private ArrayList<Operation> operationArrayList;

    public Job(long l, ArrayList<Operation> operationArrayList2) {
		this.id = l;
		this.operationArrayList = operationArrayList2;
	}

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

    /**
     * Sort Operations in ascending order by jobId
     */
    public static Comparator<Operation> sortByDuration = (obj1, obj2) -> {
        //sort in ascending order
        return (int) (obj1.getDuration() - obj2.getDuration());
    };

    /**
     * Sort Operations in ascending order by jobId
     */
    public static Comparator<Operation> sortById = (obj1, obj2) -> {
        //sort in ascending order
        return (int) (obj2.getIndex() - obj1.getIndex());
    };

}
