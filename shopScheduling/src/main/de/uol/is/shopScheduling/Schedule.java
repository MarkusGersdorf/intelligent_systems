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

    public Long getMakespan(Resource resource) {
        ArrayList<Operation> operations = resourceHashMap.get(resource);

        if (operations != null) {
            if (operations.size() > 0) {
                return operations.get(operations.size() - 1).getStartTime() - operations.get(0).getStartTime();
            }
        }

        return 0L;
    }

    public Long getMakespan() {
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
