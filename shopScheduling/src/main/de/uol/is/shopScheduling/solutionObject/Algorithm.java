package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Resource;
import de.uol.is.shopScheduling.strategys.FifoStrategy;
import de.uol.is.shopScheduling.strategys.Strategy;

import java.util.ArrayList;

public abstract class Algorithm extends SolutionObject {
    public Algorithm(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        this.jobArrayList = jobArrayList;
        this.resourceArrayList = resource;
    }

    protected abstract void optimize();

    /**
     * initialize the population
     *
     * @param jobArrayList all jobs
     * @return init population
     */
    protected ArrayList<Resource> initialize(ArrayList<Job> jobArrayList) {
        Strategy strategy = new FifoStrategy(jobArrayList, resourceArrayList);
        return strategy.getResourceArrayList();
    }
}