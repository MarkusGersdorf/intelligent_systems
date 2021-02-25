package de.uol.is.shopScheduling;

import de.uol.is.shopScheduling.optimizationMethods.Algorithm;
import de.uol.is.shopScheduling.optimizationMethods.EvolutionStrategy;
import de.uol.is.shopScheduling.strategys.FifoStrategy;
import de.uol.is.shopScheduling.strategys.RandomStrategy;
import de.uol.is.shopScheduling.strategys.SptStrategy;
import de.uol.is.shopScheduling.strategys.Strategy;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.fail;

/**
 * In this test the functionality of the scheduler should be checked. The insertion of the data is to be checked.
 * For this purpose, a strategy is run through and the generated schedule is then checked. Operations in a resource must not overlap and a job must be completed in a specified order.
 *
 * @author Markus Gersdorf
 * @version 1.0
 * @since 24.02.2021
 */
public class ShopSchedulingTest {

    JsonParser jsonParser = new JsonParser();

    // get absolut project path
    String project_path = new File("").getAbsolutePath();

    // search for all json files and save them
    File folder = new File(project_path.concat("/src/main/resources/benchmark_problems"));
    File[] listOfFiles = folder.listFiles();

    Strategy strategy;

    private final Map<Long, Job> map = new HashMap<>();


    /**
     * Unfortunately only a test.
     * Here the test is performed once for each strategy.
     *
     * @throws IOException    read file from benchmark problems
     * @throws ParseException read file from benchmark problems
     */
    @Test
    public void testOrderFromData() throws IOException, ParseException {
        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            ArrayList<Job> jobArrayList = jsonParser.parseJsonJobs(listOfFiles[i]);

            for (Job job : jobArrayList) {
                map.put(job.getId(), job);
            }

            strategy = new FifoStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
            Schedule schedule = strategy.getSchedule();
            tests(schedule);
            checkStrategy(strategy, schedule);

            strategy = new RandomStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
            Schedule schedule_random = strategy.getSchedule();
            tests(schedule_random);

            strategy = new SptStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
            Schedule schedule_spt = strategy.getSchedule();
            tests(schedule_spt);
            //checkStrategy(strategy, schedule);

            Algorithm algorithm = new EvolutionStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
            schedule = algorithm.getSchedule();
            tests(schedule);

        }
    }

    /**
     * Call different test methods
     *
     * @param schedule schedule object from strategy which is tested
     */
    public void tests(Schedule schedule) {
        testNextItemHasBiggerStartPoint(schedule);
        testPreviousItemHasSmallerEndPoint(schedule);
        testCheckNextJobElement(schedule);
        testPreviousJobOperation(schedule);
        testNextResourceOperation(schedule);
        testPreviousResourceOperation(schedule);
        testOneOperationDirectBeforeCurrent(schedule);

    }

    /**
     * Check if the next element on the resource has a larger starting point than the current one.
     *
     * @param schedule machine assignment plan
     */
    private void testNextItemHasBiggerStartPoint(Schedule schedule) {

        for (Resource resource : schedule.getResources()) {
            if (schedule.getOperations(resource) != null) {
                for (int i = 0; i < schedule.getOperations(resource).size() - 1; i++) {
                    if (!(schedule.getOperations(resource).get(i).getEndTime() < schedule.getOperations(resource).get(i + 1).getStartTime())) {
                        fail();
                    }
                }
            }
        }
    }

    /**
     * Check if the previous element on the resource has a larger starting point than the current one.
     *
     * @param schedule machine assignment plan
     */
    private void testPreviousItemHasSmallerEndPoint(Schedule schedule) {

        for (Resource resource : schedule.getResources()) {
            if (schedule.getOperations(resource) != null) {
                if (schedule.getOperations(resource).size() > 1) {
                    for (int i = 1; i < schedule.getOperations(resource).size(); i++) {
                        if (!(schedule.getOperations(resource).get(i - 1).getEndTime() < schedule.getOperations(resource).get(i).getStartTime())) {
                            fail();
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if the next element of the job has a larger starting point than the current one.
     *
     * @param schedule machine assignment plan
     */
    private void testCheckNextJobElement(Schedule schedule) {
        ArrayList<Operation> operationArrayList = getAllOperations(schedule);

        for (Operation operation : operationArrayList) {
            Operation nextJobOperation = schedule.getNextJobOperation(operation);
            if (nextJobOperation != null) {
                if (nextJobOperation.getJobId() == operation.getJobId() && nextJobOperation.getStartTime() <= operation.getEndTime() && nextJobOperation.getIndex() == operation.getIndex() + 1) {
                    fail();
                }
            }
        }
    }

    /**
     * Check if the previous element of the job has a larger starting point than the current one.
     *
     * @param schedule machine assignment plan
     */
    private void testPreviousJobOperation(Schedule schedule) {
        ArrayList<Operation> operationArrayList = getAllOperations(schedule);

        for (Operation operation : operationArrayList) {
            Operation previousJobOperation = schedule.getPreviousJobOperation(operation);
            if (previousJobOperation != null) {
                if (previousJobOperation.getJobId() == operation.getJobId() && previousJobOperation.getEndTime() >= operation.getStartTime() && previousJobOperation.getIndex() + 1 == operation.getIndex()) {
                    fail();
                }
            } else if (operation.getIndex() != 0) {
                fail();
            }
        }
    }

    /**
     * @param schedule
     */
    private void testNextResourceOperation(Schedule schedule) {
        ArrayList<Operation> operationArrayList = getAllOperations(schedule);

        for (Operation operation : operationArrayList) {
            Operation nextJobOperation = schedule.getNextResourceOperation(operation);
            if (map.get(operation.getJobId()) != null) {
                map.get(operation.getJobId()).getOperationArrayList().sort(Job.sortById);

                if (nextJobOperation != null) {
                    if (nextJobOperation.getJobId() == operation.getJobId() && nextJobOperation.getStartTime() <= operation.getEndTime() && nextJobOperation.getIndex() == operation.getIndex() + 1) {
                        fail();
                    }
                } else if (!operationIsLastOnResource(schedule, operation)) {
                    fail();
                }
            }
        }
    }

    private void testPreviousResourceOperation(Schedule schedule) {
        ArrayList<Operation> operationArrayList = getAllOperations(schedule);

        for (Operation operation : operationArrayList) {
            Operation previousJobOperation = schedule.getPreviousResourceOperation(operation);
            if (previousJobOperation != null) {
                if (previousJobOperation.getJobId() == operation.getJobId() && previousJobOperation.getEndTime() >= operation.getStartTime() && previousJobOperation.getIndex() + 1 == operation.getIndex()) {
                    fail();
                }
            } else if (!operationIsFirstOnResource(schedule, operation)) {
                fail();
            }
        }
    }

    private void testOneOperationDirectBeforeCurrent(Schedule schedule) {
        ArrayList<Operation> operationArrayList = getAllOperations(schedule);

        for (Operation operation : operationArrayList) {
            Operation previousOperationOnResource = schedule.getPreviousResourceOperation(operation);
            Operation previousOperationOnJob = schedule.getPreviousJobOperation(operation);

            if (previousOperationOnJob != null && previousOperationOnResource != null) {
                if (Long.max(previousOperationOnResource.getEndTime(), previousOperationOnJob.getEndTime()) + 1 != operation.getStartTime()) {
                    fail();
                }
            } else if (previousOperationOnJob == null && previousOperationOnResource == null && operation.getStartTime() > 1) {
                fail();
            } else if (previousOperationOnJob != null) {
                if (previousOperationOnJob.getEndTime() + 1 != operation.getStartTime()) {
                    fail();
                }
            } else if (previousOperationOnResource != null) {
                if (previousOperationOnResource.getEndTime() + 1 != operation.getStartTime()) {
                    fail();
                }
            }
        }
    }

    private void checkStrategy(Strategy strategy, Schedule schedule) {
        if (strategy instanceof FifoStrategy) {
            for (Resource resource : schedule.getResources()) {
                for (Operation operation : schedule.getOperations(resource.getId())) {
                    Operation nextResourceOperation = schedule.getNextResourceOperation(operation);
                    if (nextResourceOperation != null) {
                        if (!(operation.getJobId() < nextResourceOperation.getJobId())) {
                            fail();
                        }
                    }
                }
            }
        } else if (strategy instanceof SptStrategy) {
            for (Resource resource : schedule.getResources()) {
                for (Operation operation : schedule.getOperations(resource.getId())) {
                    Operation nextResourceOperation = schedule.getNextResourceOperation(operation);
                    if (nextResourceOperation != null) {
                        if (map.get(operation.getJobId()).duration() > map.get(nextResourceOperation.getJobId()).duration()) {
                            fail();
                        }
                    }
                }
            }
        }
    }

    private boolean operationIsFirstOnResource(Schedule schedule, Operation operation) {
        ArrayList<Operation> operationOnResource = schedule.getOperations(schedule.getResource(operation.getResource()));

        if (operationOnResource != null) {
            if ((operationOnResource.size() != 0)) {
                return operation == operationOnResource.get(0);
            }
        }
        return false;
    }

    private boolean operationIsLastOnResource(Schedule schedule, Operation operation) {
        ArrayList<Operation> operationOnResource = schedule.getOperations(schedule.getResource(operation.getResource()));

        if (operationOnResource != null) {
            if ((operationOnResource.size() != 0)) {
                return operation == operationOnResource.get(operationOnResource.size() - 1);
            }
        }
        return false;
    }

    private ArrayList<Operation> getAllOperations(Schedule schedule) {
        ArrayList<Operation> allOperations = new ArrayList<>();
        for (Resource resource : schedule.getResources()) {
            allOperations.addAll(schedule.getOperations(resource));
        }
        return allOperations;
    }
}
