package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "feeding_tasks")
public class FeedingTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long planId;
    private String animalId; // E101
    private String animalName; // Aruna
    private String species; // Elephant
    private String foodName; // Grass
    private Double quantityKg; // 25
    private String feedingTime; // 08:00 AM
    private String zookeeperName; // Zookeeper A
    private String status; // Scheduled, Completed, Substituted, Re-planned
    @Column(length = 1000)
    private String explanation; // Reason for selection / XAI explanation

    public FeedingTask() {
        this.status = "Scheduled";
    }

    public FeedingTask(Long planId, String animalId, String animalName, String species, String foodName, Double quantityKg, String feedingTime, String zookeeperName, String explanation) {
        this();
        this.planId = planId;
        this.animalId = animalId;
        this.animalName = animalName;
        this.species = species;
        this.foodName = foodName;
        this.quantityKg = quantityKg;
        this.feedingTime = feedingTime;
        this.zookeeperName = zookeeperName;
        this.explanation = explanation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public String getAnimalId() { return animalId; }
    public void setAnimalId(String animalId) { this.animalId = animalId; }

    public String getAnimalName() { return animalName; }
    public void setAnimalName(String animalName) { this.animalName = animalName; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public Double getQuantityKg() { return quantityKg; }
    public void setQuantityKg(Double quantityKg) { this.quantityKg = quantityKg; }

    public String getFeedingTime() { return feedingTime; }
    public void setFeedingTime(String feedingTime) { this.feedingTime = feedingTime; }

    public String getZookeeperName() { return zookeeperName; }
    public void setZookeeperName(String zookeeperName) { this.zookeeperName = zookeeperName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}
