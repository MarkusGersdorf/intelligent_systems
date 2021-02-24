package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;

import java.util.ArrayList;

/**
 * All optimization methods inherit from this class
 *
 * @author Markus Gersdorf, Thomas Cwill
 * @version 1.0
 */
public abstract class Algorithm extends SolutionObject {

    public Algorithm(ArrayList<Job> jobArrayList, ArrayList<Long> resourcesArrayList) {
        super(jobArrayList, resourcesArrayList);
        this.jobArrayList = jobArrayList;
    }

    /**
     * Optimize the existing plan
     */
    protected abstract void optimize(ArrayList<Long> resourcesArrayList);

}