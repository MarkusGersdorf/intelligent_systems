package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;

public class FifoStrategy extends Strategy {


    public FifoStrategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        super(jobArrayList, resource);
        sort();
        planning();
        print();
    }

    public void sort() {
        jobArrayList.sort((j1, j2) -> {
            return (int) (j1.getId() - j2.getId()); // Ascending
        });

        for (Job j : jobArrayList) {
            System.out.println((j.getId()));
        }
    }
}
