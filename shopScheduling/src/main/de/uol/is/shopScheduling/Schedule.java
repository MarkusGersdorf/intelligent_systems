package de.uol.is.shopScheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Schedule {


    private HashMap<Resource, ArrayList<Operation>> resourceQueueHashMap = new HashMap<>();


    private void initResources(ArrayList<Long> resourcesList) {

        for (Long resource : resourcesList) {
            resourceQueueHashMap.put(new Resource("Resource ", resource), new ArrayList<>());
        }
    }

    public void addOperation(Operation operation) {
        Set<Resource> resources = resourceQueueHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                resourceQueueHashMap.get(res).add(operation);
                break;
            }
        }
    }

    public void addOperation(int index, Operation operation) {
        Set<Resource> resources = resourceQueueHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                resourceQueueHashMap.get(res).add(index, operation);
                break;
            }
        }
    }


    public void removeOperation(Operation operation) {
        Set<Resource> resources = resourceQueueHashMap.keySet();
        for (Resource res : resources) {
            if (res.getId() == operation.getResource()) {
                resourceQueueHashMap.get(res).remove(operation);
                // TODO: update times from other operations
                break;
            }
        }
    }

    public Operation getPreviousOperation(Operation operation) {

        return null;
    }

    public Operation getNextOperation(Operation operation) {

        return null;
    }


}
