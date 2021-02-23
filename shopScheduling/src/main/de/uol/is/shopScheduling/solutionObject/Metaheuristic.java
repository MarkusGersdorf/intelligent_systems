package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;


// Initialize solution (done)
// search for neighbors (shuffle)
// find best solution

public class Metaheuristic extends Algorithm {

    public Metaheuristic(ArrayList<Job> jobArrayList, ArrayList<Long> resourcesArrayList) {
        super(jobArrayList, resourcesArrayList);
        optimize(resourcesArrayList);
    }

    @Override
    protected void optimize(ArrayList<Long> resourcesArrayList) {
       /* ArrayList<Resource> new_resources_array_list = new ArrayList<>();
        int runs = 0;
        boolean success = false;

        while (runs < 1000) {
            for (Resource r : resourceArrayList) {
                r.setOperationQueue(new LinkedList<>());
                for (Operation o : r.getOperationQueue()) {
                    r.addOperation(o);
                }
                new_resources_array_list.add(r);
            }

            // Maybe something better?
            Collections.shuffle(new_resources_array_list);

            if (check_for_constraints(new_resources_array_list) &&
                    total_processing_time(new_resources_array_list) < total_processing_time(resourceArrayList)) {
                resourceArrayList = new_resources_array_list;
                success = true;
                break;
            }
            runs++;
        }

        if(success) {
            System.out.println("[+] Optimierung war erfolgreich");
        }
        else {
            System.out.println("[-] Optimierung war nicht erfolgreich");
        }*/
    }

    protected double total_processing_time(ArrayList<Resource> resources) {
        long time = 0;

        for(Resource r : resources) {
            time += r.getDuration();
        }

        return time;
    }
}
