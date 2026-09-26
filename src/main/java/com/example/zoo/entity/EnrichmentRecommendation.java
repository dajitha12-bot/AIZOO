package com.example.zoo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrichment_recommendations")
public class EnrichmentRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String animalId;
    private String animalName;
    private String species;
    private String activityName;
    private String reason;
    private LocalDate date;

    public EnrichmentRecommendation() {
        this.date = LocalDate.now();
    }

    public EnrichmentRecommendation(String animalId, String animalName, String species, String activityName, String reason) {
        this();
        this.animalId = animalId;
        this.animalName = animalName;
        this.species = species;
        this.activityName = activityName;
        this.reason = reason;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAnimalId() { return animalId; }
    public void setAnimalId(String animalId) { this.animalId = animalId; }

    public String getAnimalName() { return animalName; }
    public void setAnimalName(String animalName) { this.animalName = animalName; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
