package de.uol.is.shopScheduling;

import java.lang.reflect.Array;
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
        Set<Resource> resources = resourceHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                resourceHashMap.get(res).add(operation);
                break;
            }
        }
    }

    public void addOperation(int index, Operation operation) {
        Set<Resource> resources = resourceHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                resourceHashMap.get(res).add(index, operation);
                break;
            }
        }
    }


    public void removeOperation(Operation operation) {
        Set<Resource> resources = resourceHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                resourceHashMap.get(res).remove(operation);
                // TODO: update times from other operations
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
}
