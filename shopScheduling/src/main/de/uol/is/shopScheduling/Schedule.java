package de.uol.is.shopScheduling;

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

    public Operation getPreviousResourceOperation(Operation operation) {
        Set<Resource> resources = resourceHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                int idx = resourceHashMap.get(res).indexOf(operation);
                if (idx < resourceHashMap.get(res).size() && idx > 0) {
                    if (resourceHashMap.get(res).get(idx - 1) != null) {
                        return resourceHashMap.get(res).get(idx - 1);
                    }
                }
            }
        }
        return null;
    }

    public Operation getNextResourceOperation(Operation operation) {
        Set<Resource> resources = resourceHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                int idx = resourceHashMap.get(res).indexOf(operation);
                if (resourceHashMap.get(res).size() > idx + 1) {
                    if (resourceHashMap.get(res).get(idx) != null && resourceHashMap.get(res).get(idx + 1) != null) {
                        return resourceHashMap.get(res).get(idx + 1);
                    }
                }
            }
        }
        return null;
    }

    public Operation getPreviousJobOperation(Operation operation) {
        for (ArrayList<Operation> operationArrayList : resourceHashMap.values()) {
            for (Operation op : operationArrayList) {
                if (op.getResource() - 1 == operation.getResource() && op.getJobId() == operation.getJobId())
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
                return operations.get(operations.size() - 1).getStartTime() - operations.get(0).getStartTime();
            }
        }

        return 0L;
    }

    public long getMakespan() {
        long makespan = 0L;
        for (Resource resource : resourceHashMap.keySet()) {
            makespan += getMakespan(resource);
        }
        return makespan;
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

    public void print() {
        for (Resource r : resourceHashMap.keySet()) {
            for (Operation o : resourceHashMap.get(r)) {
                System.out.println("Res: " + r.getId() + " - Operation Start Time: " + o.getStartTime());
            }
        }
    }

    public void addOperationToResource(Resource resource, Operation operation) {
        long startPointOperation = 0L;
        if (getPreviousJobOperation(operation) != null) {
            startPointOperation = getPreviousJobOperation(operation).getEndTime();
        }

        ArrayList<Operation> resourceArrayList = resourceHashMap.get(resource);
        long maxEndPoint = 0L;
        for (Operation o : resourceArrayList) {
            maxEndPoint = Long.max(maxEndPoint, o.getEndTime());
        }

        operation.setStartTime(Long.max(maxEndPoint, startPointOperation) + 1);
        operation.setEndTime((Long.max(maxEndPoint, startPointOperation) + 1) + operation.getDuration());

        addOperation(operation);
    }

}
