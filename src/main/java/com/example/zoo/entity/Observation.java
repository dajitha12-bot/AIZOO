package com.example.zoo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "observations")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String animalId; // E101

    private String animalName; // Aruna
    private String foodIntake; // Low, Normal, High
    private String waterIntake; // Low, Normal, High
    private String activityLevel; // Low, Normal, High
    private String observationText; // Aruna ate less food today.
    private String aiPatternDetected; // Normal, Monitor, Attention Required
    private LocalDateTime timestamp;

    public Observation() {
        this.timestamp = LocalDateTime.now();
        this.aiPatternDetected = "Normal";
    }

    public Observation(String animalId, String animalName, String foodIntake, String waterIntake, String activityLevel, String observationText) {
        this();
        this.animalId = animalId;
        this.animalName = animalName;
        this.foodIntake = foodIntake;
        this.waterIntake = waterIntake;
        this.activityLevel = activityLevel;
        this.observationText = observationText;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAnimalId() { return animalId; }
    public void setAnimalId(String animalId) { this.animalId = animalId; }

    public String getAnimalName() { return animalName; }
    public void setAnimalName(String animalName) { this.animalName = animalName; }

    public String getFoodIntake() { return foodIntake; }
    public void setFoodIntake(String foodIntake) { this.foodIntake = foodIntake; }

    public String getWaterIntake() { return waterIntake; }
    public void setWaterIntake(String waterIntake) { this.waterIntake = waterIntake; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public String getObservationText() { return observationText; }
    public void setObservationText(String observationText) { this.observationText = observationText; }

    public String getAiPatternDetected() { return aiPatternDetected; }
    public void setAiPatternDetected(String aiPatternDetected) { this.aiPatternDetected = aiPatternDetected; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
