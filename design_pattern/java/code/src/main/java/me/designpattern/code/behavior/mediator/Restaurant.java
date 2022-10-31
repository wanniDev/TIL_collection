package me.designpattern.code.behavior.mediator;

import java.time.LocalDateTime;

public class Restaurant {

    private CleaningService cleaningService = new CleaningService();
    public void dinner(Integer id, LocalDateTime dateTime) {
        System.out.println(id + " " + dateTime);
    }

}
