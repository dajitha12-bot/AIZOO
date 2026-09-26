package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enrichment_knowledge")
public class EnrichmentKnowledge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String speciesName; // Elephant

    @Column(nullable = false)
    private String activityName; // Water Play

    private String description;

    public EnrichmentKnowledge() {}

    public EnrichmentKnowledge(String speciesName, String activityName, String description) {
        this.speciesName = speciesName;
        this.activityName = activityName;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSpeciesName() { return speciesName; }
    public void setSpeciesName(String speciesName) { this.speciesName = speciesName; }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
