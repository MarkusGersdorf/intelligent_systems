package de.uol.is.shopScheduling;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Job {

    @Getter
    private int id;
    @Getter
    private int duration;
    @Getter
    private int resource;

}
