package com.example.zoo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class TerminalEventLogger {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @EventListener
    public void handleZooDataChangeEvent(ZooDataChangeEvent event) {
        String timestamp = LocalTime.now().format(TIME_FORMATTER);

        System.out.println("\n==================================================");
        switch (event.getEventType()) {
            case "ANIMAL_UPDATE":
                System.out.println("[REAL-TIME ANIMAL UPDATE]");
                break;
            case "FOOD_UPDATE":
                System.out.println("[REAL-TIME FOOD UPDATE]");
                break;
            case "OBSERVATION_UPDATE":
                System.out.println("[REAL-TIME OBSERVATION]");
                break;
            case "PLAN_GENERATED":
                System.out.println("[AI FEEDING PLAN GENERATED]");
                break;
            case "CONFLICT_DETECTED":
                System.out.println("[AI CONFLICT DETECTED]");
                break;
            case "REPLAN_COMPLETED":
                System.out.println("[AI RE-PLANNING COMPLETED]");
                break;
            default:
                System.out.println("[REAL-TIME ZOO SYSTEM EVENT]");
                break;
        }

        System.out.println("Time  : " + timestamp);
        if (event.getTitle() != null && !event.getTitle().isEmpty()) {
            System.out.println("Title : " + event.getTitle());
        }
        System.out.println(event.getDetails());
        System.out.println("==================================================\n");
    }
}
