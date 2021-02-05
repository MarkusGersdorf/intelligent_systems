package de.uol.is.shopScheduling;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents an operation that is necessary to process a workpiece
 *
 * @author markusgersdorf
 * @version 0.1
 */
@AllArgsConstructor
public class Operation {
    @Getter
    private final long index;
    @Getter
    private final long duration;
    @Getter
    private final long resource;
}
