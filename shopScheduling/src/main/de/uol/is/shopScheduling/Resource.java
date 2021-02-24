package de.uol.is.shopScheduling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents one resource a machine for example
 *
 * @author Thomas Cwill, Markus Gersdorf
 * @version 1.0
 */
@AllArgsConstructor
public class Resource {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private long id;

}
