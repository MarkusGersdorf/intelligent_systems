package de.uol.is.shopScheduling.solutionObject;

import de.uol.is.shopScheduling.Job;
import de.uol.is.shopScheduling.Operation;
import de.uol.is.shopScheduling.Resource;

import java.util.ArrayList;

// Branch and Bound Algorithm
public class BABAlgorithm extends Algorithm {
    private long tv, th, tt; // tv = Verarbeitungszeit; th = Verstrichene Zeit; tt = Verbleibende Bearbeitungszeit

    public BABAlgorithm(ArrayList<Job> jobArrayList, ArrayList<Resource> resource) {
        super(jobArrayList, resource);
    }

    @Override
    public void optimize() {
        for (Job j : jobArrayList) {
            for (Operation o : j.getOperationArrayList()) {

            }
        }
    }




    private boolean constraint_makespan() {
        return true;
    }
}
