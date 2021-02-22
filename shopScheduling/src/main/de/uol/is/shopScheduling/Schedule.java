package de.uol.is.shopScheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Schedule implements ISchedule {


    private final HashMap<Resource, ArrayList<Operation>> resourceHashMap = new HashMap<>();

    public Schedule(ArrayList<Long> resourcesList) {
        initResources(resourcesList);
    }


    private void initResources(ArrayList<Long> resourcesList) {

        for (Long resource : resourcesList) {
            resourceHashMap.put(new Resource("Resource ", resource), new ArrayList<>());
            // TODO: Operations hinzufügen?
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

    public Operation getPreviousOperation(Operation operation) {
        Set<Resource> resources = resourceHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                int idx = resourceHashMap.get(res).indexOf(operation);
                if (resourceHashMap.get(res).get(idx - 1) != null) {
                    return resourceHashMap.get(res).get(idx - 1);
                }
            }
        }
        return null;
    }

    public Operation getNextOperation(Operation operation) {
        Set<Resource> resources = resourceHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                int idx = resourceHashMap.get(res).indexOf(operation);
                if (resourceHashMap.get(res).get(idx + 1) != null) {
                    return resourceHashMap.get(res).get(idx + 1);
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


    public Long durationFromTo(Operation startOperation, Operation endOperation) {
        Long duration = 0L;
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
    public Long durationTo(Operation endOperation, boolean inclusive) {
        Long duration = 0L;
        duration = durationTo(endOperation);

        if (inclusive) {
            duration += endOperation.getDuration();
        }
        return duration;
    }

    public Long durationTo(Operation endOperation) {
        Long duration = 0L;
        for (ArrayList<Operation> operationArrayList : resourceHashMap.values()) {
            for (Operation op : operationArrayList) {
                if (op.getResource() < endOperation.getResource())
                    duration += op.getDuration();
            }
        }

        return duration;
    }

}
