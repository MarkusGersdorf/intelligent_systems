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
public class Schedule implements ISchedule {

    private final HashMap<Resource, ArrayList<Operation>> resourceHashMap = new HashMap<>();

    public Schedule(ArrayList<Long> resourcesList) {
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

    public void addOperation(Operation operation) {
        Set<Resource> resourceSet = resourceHashMap.keySet();
        for (Resource resource : resourceSet) {
            if (resource.getId() == operation.getResource()) {
                resourceHashMap.get(resource).add(operation);
                break;
            }
        }
    }

    public void addOperation(int index, Operation operation) {
        Set<Resource> resourceSet = resourceHashMap.keySet();
        for (Resource resource : resourceSet) {
            if (resource.getId() == operation.getResource()) {
                resourceHashMap.get(resource).add(index, operation);
                break;
            }
        }
    }


    public void removeOperation(Operation operation) {
        Set<Resource> resourceSet = resourceHashMap.keySet();
        for (Resource resource : resourceSet) {
            if (resource.getId() == operation.getResource()) {
                resourceHashMap.get(resource).remove(operation);
                break;
            }
        }
    }

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

    public Operation getPreviousResourceOperation(Operation operation) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == operation.getResource()) {
                ArrayList<Operation> operationArrayList = resourceHashMap.get(resource);
                if (operationArrayList.size() > 1 && operationArrayList.indexOf(operation) > 1) {
                    return operationArrayList.get(operationArrayList.indexOf(operation) - 1);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

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

    public Operation getPreviousJobOperation(Operation operation) {
        for (ArrayList<Operation> operationArrayList : resourceHashMap.values()) {
            for (Operation op : operationArrayList) {
                if (op.getIndex() == operation.getIndex() - 1 && op.getJobId() == operation.getJobId())
                    return op;
            }
        }
        return null;
    }

    public Operation getNextJobOperation(Operation operation) {
        for (ArrayList<Operation> operationArrayList : resourceHashMap.values()) {
            for (Operation op : operationArrayList) {
                if (op.getResource() + 1 == operation.getResource() && op.getJobId() == operation.getJobId())
                    return op;
            }
        }
        return null;
    }


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
     * duration to a Operation
     *
     * @param endOperation
     * @param inclusive    inclusive endOperation
     * @return
     */
    public long durationTo(Operation endOperation, boolean inclusive) {
        return (inclusive) ? durationFrom(endOperation) + endOperation.getDuration() : durationFrom(endOperation);
    }


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

    public long durationFrom(Operation startOperation, boolean inclusive) {
        return (inclusive) ? durationFrom(startOperation) + startOperation.getDuration() : durationFrom(startOperation);
    }

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

    public long getMakespan(Resource resource) {
        ArrayList<Operation> operations = resourceHashMap.get(resource);

        if (operations != null) {
            if (operations.size() > 0) {
                return operations.get(operations.size() - 1).getEndTime() - operations.get(0).getStartTime();
            }
        }

        return 0L;
    }

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

    public Resource getResource(long resourceId) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == resourceId) {
                return resource;
            }
        }
        return null;
    }

    public ArrayList<Operation> getOperations(long resourceId) {
        for (Resource resource : resourceHashMap.keySet()) {
            if (resource.getId() == resourceId) {
                return resourceHashMap.get(resource);
            }
        }
        return null;
    }

    public ArrayList<Operation> getOperations(Resource resource) {
        return getOperations(resource.getId());
    }

    public Operation getOperation(Resource resource, long pointInTime) {
        for (Operation operation : resourceHashMap.get(resource)) {
            if (operation.operationExists(pointInTime)) {
                return operation;
            }
        }
        return null;
    }

    public void print() {
        for (Resource r : resourceHashMap.keySet()) {
            for (Operation o : resourceHashMap.get(r)) {
                System.out.println("Res: " + r.getId() + " - Operation Start Time: " + o.getStartTime());
            }
        }
    }

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

    public void addOperationToResource(Resource resource, Operation operation) {
        long startPointOperation = 0L;
        long maxEndPoint = 0L;

        if (getPreviousJobOperation(operation) != null) {
            startPointOperation = getPreviousJobOperation(operation).getEndTime();
        }

        if (getLastInsertedElement(operation) != null) {
            maxEndPoint = getLastInsertedElement(operation).getEndTime();
        }

        operation.setStartTime(Long.max(maxEndPoint, startPointOperation) + 1);
        operation.setEndTime(operation.getStartTime() + operation.getDuration());


        // TODO: Update other items - but how should we do it?
        /*// update start point from next Job Operation
        if (getNextJobOperation(operation) != null) {
            if (operation.getEndTime() > getNextJobOperation(operation).getStartTime()) {
                getNextJobOperation(operation).setStartTime(operation.getEndTime() + 1);

                ArrayList<Operation> operationArrayList = resourceHashMap.get(getResource(operation.getResource()));
                if (operationArrayList != null) {
                    for (Operation o : operationArrayList) {
                        if (getNextResourceOperation(o) != null) {
                            if (getNextResourceOperation(o).getStartTime() < o.getEndTime()) {
                                getNextResourceOperation(o).setStartTime(o.getEndTime() + 1);
                            }
                        }
                    }
                }
            }
        }*/

        addOperation(operation);
    }

}
