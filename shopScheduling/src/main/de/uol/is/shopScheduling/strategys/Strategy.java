package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;

public abstract class Strategy {

    protected ArrayList<Job> jobArrayList;
    ArrayList<Resource> resourceArrayList;

    public Strategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        this.jobArrayList = jobArrayList;
        this.resourceArrayList = resource;
    }

    public abstract void print();

    public Resource getResource(long resourceNumber) {
        if(resourceArrayList.get((int) resourceNumber).getId() == resourceNumber) {
            return resourceArrayList.get((int)resourceNumber);
        } else {
            for(Resource resource : resourceArrayList) {
                if(resource.getId() == resourceNumber) {
                    return resource;
                }
            }
        }
        return null;
    }

    public void planning() {
        for(Job job : jobArrayList) {
            long planedTime = 0;
            for(Operation operation : job.getOperationArrayList()) {
                addOperationToResource(getResource(operation.getResource()), operation, planedTime);
            }
        }
    }

    protected void addOperationToResource(Resource resource, Operation new_operation, long planedTime) {
        boolean added = false;

        if(resource.getOperations().size() == 0) {
            new_operation.setStartTime(planedTime);
            new_operation.setEndTime(planedTime + new_operation.getDuration());
            resource.addOperation(new_operation);
            added = true;
        }

        while(!added) {
            boolean blocked = false;
            for(Operation operation : resource.getOperations()) {
                if((operation.getStartTime() >= planedTime && operation.getStartTime() <= planedTime + new_operation.getDuration()) ||
                   (operation.getEndTime() >= planedTime && operation.getEndTime() <= planedTime + new_operation.getDuration())){

                    blocked = true;
                    break;
                }
            }
            if(!blocked) {
                new_operation.setStartTime(planedTime);
                new_operation.setEndTime(planedTime + new_operation.getDuration());
                resource.addOperation(new_operation);
                added = true;
            } else {
                planedTime++;
            }
        }
    }

}
