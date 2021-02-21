package de.uol.is.shopScheduling.strategys;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomStrategy extends Strategy {

    public RandomStrategy(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        super(jobArrayList, resource);
        sort();
        planning();
        print();
    }

    @Override
    public void sort() {
        Collections.shuffle(jobArrayList, new Random()); // Generate a random ranking
    }
}
