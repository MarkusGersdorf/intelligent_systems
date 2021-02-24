package de.uol.is.shopScheduling;

import de.uol.is.shopScheduling.strategys.FifoStrategy;
import de.uol.is.shopScheduling.strategys.RandomStrategy;
import de.uol.is.shopScheduling.strategys.SptStrategy;
import de.uol.is.shopScheduling.strategys.Strategy;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.fail;

public class ShopSchedulingTest {

    JsonParser jsonParser = new JsonParser();

    // get absolut project path
    String project_path = new File("").getAbsolutePath();

    // search for all json files and save them
    File folder = new File(project_path.concat("/src/main/resources/benchmark_problems"));
    File[] listOfFiles = folder.listFiles();

    Strategy strategy;

    private final Map<Long, Job> map = new HashMap<>();

    @Before
    public void init() throws IOException, ParseException {
        ArrayList<Job> jobArrayList = jsonParser.parseJsonJobs(listOfFiles[0]);

        for (Job job : jobArrayList) {
            map.put(job.getId(), job);
        }
    }

    @Test
    public void testOrderFromData() throws IOException, ParseException {
        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {

            strategy = new FifoStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
            Schedule schedule = strategy.getSchedule();
            tests(schedule);

            strategy = new RandomStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
            schedule = strategy.getSchedule();
            tests(schedule);

            strategy = new SptStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
            schedule = strategy.getSchedule();
            tests(schedule);

//            Algorithm algorithm = new EvolutionStrategy(jsonParser.parseJsonJobs(listOfFiles[i]), jsonParser.parseJsonResources(listOfFiles[i]));
//            schedule = algorithm.getSchedule();
//            tests(schedule);

        }
    }

    public void tests(Schedule schedule) {
        testNextItemHasBiggerStartPoint(schedule);
        testPreviousItemHasSmallerEndPoint(schedule);
        testCheckNextJobElement(schedule);
        testPreviousJobOperation(schedule);
        testNextResourceOperation(schedule);
        testPreviousResourceOperation(schedule);
        testOneOperationDirectBeforeCurrent(schedule);

    }

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

            if (previousOperationOnJob == null && previousOperationOnResource == null && operation.getStartTime() > 1) {
                fail();
            } else if (previousOperationOnJob != null) {
                if (previousOperationOnJob.getEndTime() + 1 > operation.getStartTime()) {
                    fail();
                }
            } else if (previousOperationOnResource != null) {
                if (previousOperationOnResource.getEndTime() + 1 > operation.getStartTime()) {
                    fail();
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
