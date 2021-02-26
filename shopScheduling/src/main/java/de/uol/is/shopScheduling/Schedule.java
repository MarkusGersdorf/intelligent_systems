package de.uol.is.shopScheduling;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Resources are controlled here. If something is added then everything around it is adjusted if necessary.
 * This includes the start times for all further operations of the own job and the start times of other jobs on the same resource.
 *
 * @author Thomas Cwill, Markus Gersdorf
 * @version 1.0
 */
public class Schedule implements ISchedule, Cloneable {

    private HashMap<Resource, ArrayList<Operation>> resourceHashMap = new HashMap<>();
    private ArrayList<Long> resourcesList;

    public Schedule(ArrayList<Long> resourcesList) {
        this.resourcesList = resourcesList;
        initResources(resourcesList);
    }

    /**
     * generate the resource objects from a list
     *
     * @param resourcesList list of resources
     */
    private void initResources(ArrayList<Long> resourcesList) {

        for (Long id : resourcesList) {
            resourceHashMap.put(new Resource("Resource " + id, id), new ArrayList<>());
        }
    }

    /**
     * Add operation to resource
     *
     * @param operation operation object with initialized resourceId
     */
    public void addOperation(Operation operation) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == operation.getResource()) {
                resourceHashMap.get(resource).add(operation);
                break;
            }
        }
    }

    /**
     * * Add operation to resource
     *
     * @param index     index where the operation should be added
     * @param operation operation object with initialized resourceId
     */
    public void addOperation(int index, Operation operation) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == operation.getResource()) {
                resourceHashMap.get(resource).add(index, operation);
                break;
            }
        }
    }

    /**
     * Remove operation from resource
     *
     * @param operation operation object with initialized resourceId
     */
    public void removeOperation(Operation operation) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == operation.getResource()) {
                resourceHashMap.get(resource).remove(operation);
                break;
            }
        }
    }

    /**
     * Get last element from resource without inserted current
     *
     * @param operation operation object with initialized resourceId
     * @return last inserted operation or null when the resource list is empty
     */
    public Operation getLastInsertedElement(Operation operation) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == operation.getResource()) {
                ArrayList<Operation> operationArrayList = resourceHashMap.get(resource);
                if (operationArrayList.size() > 0) {
                    return operationArrayList.get(operationArrayList.size() - 1);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Get previous operation on the same resource
     *
     * @param operation operation for which is asked
     * @return previous operation or null when there is none
     */
    public Operation getPreviousResourceOperation(Operation operation) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == operation.getResource()) {
                ArrayList<Operation> operationArrayList = resourceHashMap.get(resource);
                if (operationArrayList.size() > 1 && operationArrayList.indexOf(operation) > 0) {
                    return operationArrayList.get(operationArrayList.indexOf(operation) - 1);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Get next operation on the same resource
     *
     * @param operation operation for which is asked
     * @return next operation or null when there is none
     */
    public Operation getNextResourceOperation(Operation operation) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == operation.getResource()) {
                ArrayList<Operation> operationArrayList = resourceHashMap.get(resource);
                if (operationArrayList.size() > operationArrayList.indexOf(operation) + 1) {
                    return operationArrayList.get(operationArrayList.indexOf(operation) + 1);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Get previous operation with the same jobId
     *
     * @param operation operation for which is asked
     * @return previous operation or null when there is none
     */
    public Operation getPreviousJobOperation(Operation operation) {
        for (ArrayList<Operation> operationArrayList : resourceHashMap.values()) {
            for (Operation op : operationArrayList) {
                if (op.getIndex() == operation.getIndex() - 1 && op.getJobId() == operation.getJobId())
                    return op;
            }
        }
        return null;
    }

    /**
     * Get next operation with the same jobId
     *
     * @param operation operation for which is asked
     * @return next operation or null when there is none
     */
    public Operation getNextJobOperation(Operation operation) {
        for (ArrayList<Operation> operationArrayList : resourceHashMap.values()) {
            for (Operation op : operationArrayList) {
                if (op.getResource() + 1 == operation.getResource() && op.getJobId() == operation.getJobId())
                    return op;
            }
        }
        return null;
    }

    /**
     * Duration  from one operation to another
     *
     * @param startOperation operation to start to measure the duration
     * @param endOperation   operation to stop the duration measurement
     * @return duration
     */
    public long durationFromTo(Operation startOperation, Operation endOperation) {
        long duration = 0L;
        for (ArrayList<Operation> operationArrayList : resourceHashMap.values()) {
            for (Operation op : operationArrayList) {
                if (op.getResource() > startOperation.getResource() && op.getResource() < endOperation.getResource())
                    duration += op.getDuration();
            }
        }
        return duration;
    }

    /**
     * Duration to a Operation
     *
     * @param endOperation operation to stop the duration measurement
     * @param inclusive    inclusive endOperation while parameter is true
     * @return duration
     */
    public long durationTo(Operation endOperation, boolean inclusive) {
        return (inclusive) ? durationFrom(endOperation) + endOperation.getDuration() : durationFrom(endOperation);
    }

    /**
     * Duration to a Operation
     *
     * @param endOperation operation to stop the duration measurement
     * @return duration
     */
    public long durationTo(Operation endOperation) {
        long duration = 0L;
        for (ArrayList<Operation> operationArrayList : resourceHashMap.values()) {
            for (Operation op : operationArrayList) {
                if (op.getResource() < endOperation.getResource() && op.getJobId() == endOperation.getJobId())
                    duration += op.getDuration();
            }
        }

        return duration;
    }

    /**
     * Duration from a Operation
     *
     * @param startOperation operation to start the duration measurement
     * @param inclusive      inclusive endOperation while parameter is true
     * @return duration
     */
    public long durationFrom(Operation startOperation, boolean inclusive) {
        return (inclusive) ? durationFrom(startOperation) + startOperation.getDuration() : durationFrom(startOperation);
    }

    /**
     * Duration from a Operation
     *
     * @param startOperation operation to start the duration measurement
     * @return duration
     */
    public long durationFrom(Operation startOperation) {
        long duration = 0L;
        for (ArrayList<Operation> operationArrayList : resourceHashMap.values()) {
            for (Operation op : operationArrayList) {
                if (op.getResource() > startOperation.getResource() && op.getJobId() == startOperation.getJobId())
                    duration += op.getDuration();
            }
        }

        return duration;
    }

    /**
     * Get makespan from one resource
     *
     * @param resource resource for which makespan should be calculated
     * @return makespan
     */
    public long getMakespan(Resource resource) {
        ArrayList<Operation> operations = resourceHashMap.get(resource);

        if (operations != null) {
            if (operations.size() > 0) {
                return operations.get(operations.size() - 1).getEndTime() - operations.get(0).getStartTime();
            }
        }

        return 0L;
    }

    /**
     * Get makespan from the whole scheduler
     *
     * @return makespan
     */
    public long getMakespan() {
        long minStartTime = Long.MAX_VALUE;
        long maxEndTime = Long.MIN_VALUE;

        for (Resource resource : resourceHashMap.keySet()) {
            for (Operation operation : resourceHashMap.get(resource)) {
                minStartTime = Long.min(minStartTime, operation.getStartTime());
                maxEndTime = Long.max(maxEndTime, operation.getEndTime());
            }
        }
        return maxEndTime - minStartTime;
    }

    /**
     * Get resource by resourceId
     *
     * @param resourceId resourceId
     * @return resource object
     */
    public Resource getResource(long resourceId) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == resourceId) {
                return resource;
            }
        }
        return null;
    }

    /**
     * Get operations by resourceId
     *
     * @param resourceId resourceId
     * @return list of operations
     */
    public ArrayList<Operation> getOperations(long resourceId) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == resourceId) {
                return resourceHashMap.get(resource);
            }
        }
        return null;
    }

    /**
     * Get operations by resource object
     *
     * @param resource resource object
     * @return list of operations
     */
    public ArrayList<Operation> getOperations(Resource resource) {
        return getOperations(resource.getId());
    }

    /**
     * Get operation at point in time
     *
     * @param resource    resource object
     * @param pointInTime point in time as long value
     * @return operation object
     */
    public Operation getOperation(Resource resource, long pointInTime) {
        for (Operation operation : resourceHashMap.get(resource)) {
            if (operation.operationExists(pointInTime)) {
                return operation;
            }
        }
        return null;
    }

    public void print_to_console() {
        // Tabelle nach Job sortiert
        /*
        System.out.print("JobID\t\t");
        Job job = jobArrayList.get(0);
        int steps = 0;
        for (Operation op : job.getOperationArrayList()) {
            System.out.print(" Step " + steps + " \t");
            steps += 1;
        }
        System.out.println();
        for (Job j : jobArrayList) {
            System.out.print(j.getId() + "\t\t\t");
            for (Operation op : j.getOperationArrayList()) {
                System.out.print(" " + op.getResource() + "," + op.getDuration() + " \t\t");
            }
            System.out.println();
        }
        */
        // Tabelle nach Ressource sortiert

        System.out.print("RessourceID\t");
        for (Resource res : resourceHashMap.keySet()) {
            int steps = 0;
            for (Operation op : resourceHashMap.get(res)) {
                System.out.print(" Step " + steps + " \t");
                steps += 1;
            }
            break;
        }
        System.out.println();
        // TODO: Liste sortieren (TreeMap)
        for (Resource r : resourceHashMap.keySet()) {
            System.out.print(r.getId() + "\t\t\t");
            for (Operation op : resourceHashMap.get(r)) {
                System.out.print(" " + op.getResource() + "," + op.getDuration() + " \t\t");
            }
            System.out.println("");
        }
    }

    /**
     * print chart with points at what time on which resource which job runs
     */
    public void printDiagram() {
        long minDuration = Long.MAX_VALUE;
        long maxDuration = Long.MIN_VALUE;

        for (Resource resource : resourceHashMap.keySet()) {
            for (Operation operation : resourceHashMap.get(resource)) {
                minDuration = Long.min(minDuration, operation.getStartTime());
                maxDuration = Long.max(maxDuration, operation.getEndTime());
            }
        }

        for (Resource resource : resourceHashMap.keySet()) {
            for (int i = (int) minDuration; i < (int) maxDuration; i++) {
                String resourceString = getOperation(resource, i) == null ? " " : "" + getOperation(resource, i).getJobId() + "";
                System.out.print("|" + StringUtils.leftPad(resourceString + "|\t", 6, " "));
            }
            System.out.println();
        }
        for (int i = (int) minDuration; i < (int) maxDuration; i++) {
            System.out.print("|" + StringUtils.leftPad(i + "|\t", 6, "0"));
        }
        System.out.println();
    }

    /**
     * Get all resources
     *
     * @return set of all resources
     */
    public Set<Resource> getResources() {
        return resourceHashMap.keySet();
    }

    /**
     * Add one operation to resource, update start and end time from this object
     *
     * @param operation operation object which should be added to resource
     */
    public void addOperationToResource(Operation operation) {
        long endPointPreviousJob = 0L;
        long endPointPreviousResource = 0L;

        if (getPreviousJobOperation(operation) != null) {
            endPointPreviousJob = getPreviousJobOperation(operation).getEndTime();
        }

        if (getLastInsertedElement(operation) != null) {
            endPointPreviousResource = getLastInsertedElement(operation).getEndTime();
        }

        // update start and end time from operation
        operation.setStartTime(Long.max(endPointPreviousResource, endPointPreviousJob) + 1);
        operation.setEndTime(operation.getStartTime() + operation.getDuration());

        // add operation to resource
        addOperation(operation);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Schedule newSchedule = new Schedule(resourcesList);
        HashMap<Resource, ArrayList<Operation>> resourceHashMap = new HashMap<>();

        for (Resource resource : getResources()) {
            ArrayList<Operation> newOperation = new ArrayList<>();

            for (Operation operation : getOperations(resource.getId())) {
                newOperation.add((Operation) operation.clone());
            }

            resourceHashMap.put(resource, newOperation);
        }

        newSchedule.setResourceHashMap(resourceHashMap);
        return newSchedule;
    }

	private void setResourceHashMap(HashMap<Resource, ArrayList<Operation>> resourceHashMap2) {
		this.resourceHashMap = resourceHashMap2;
	}
}
