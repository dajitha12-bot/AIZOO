package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "species_knowledge")
public class SpeciesKnowledge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String speciesName; // Elephant, Lion, Giraffe, Zebra, etc.

    private String description;
    private Integer minDailyFeedings; // Default feedings per day (e.g. 3)

    public SpeciesKnowledge() {}

    public SpeciesKnowledge(String speciesName, String description, Integer minDailyFeedings) {
        this.speciesName = speciesName;
        this.description = description;
        this.minDailyFeedings = minDailyFeedings;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSpeciesName() { return speciesName; }
    public void setSpeciesName(String speciesName) { this.speciesName = speciesName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getMinDailyFeedings() { return minDailyFeedings; }
    public void setMinDailyFeedings(Integer minDailyFeedings) { this.minDailyFeedings = minDailyFeedings; }
}
