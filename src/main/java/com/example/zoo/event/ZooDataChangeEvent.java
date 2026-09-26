package com.example.zoo.event;

import org.springframework.context.ApplicationEvent;

public class ZooDataChangeEvent extends ApplicationEvent {

    private final String eventType; // ANIMAL_UPDATE, FOOD_UPDATE, OBSERVATION_UPDATE, PLAN_GENERATED, CONFLICT_DETECTED, REPLAN_COMPLETED
    private final String title;
    private final String details;

    public ZooDataChangeEvent(Object source, String eventType, String title, String details) {
        super(source);
        this.eventType = eventType;
        this.title = title;
        this.details = details;
    }

    public String getEventType() { return eventType; }
    public String getTitle() { return title; }
    public String getDetails() { return details; }
}
