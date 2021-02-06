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

}
