package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;

public class FifoStrategy extends Strategy {


    public FifoStrategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        super(jobArrayList, resource);
    }

    @Override
    public void print() {
        System.out.println("Hallo Welt!");
    }
}
