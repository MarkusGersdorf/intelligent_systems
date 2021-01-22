package de.uol.is.shopScheduling;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

/**
 * A job contains an id and a certain number of operations, which are necessary to perform a machining operation.
 *
 * @author markusgersdorf
 * @version 0.1
 */
@AllArgsConstructor
public class Job {

    @Getter
    private final long id;
    @Getter
    private final ArrayList<Operation> operationArrayList;

}
