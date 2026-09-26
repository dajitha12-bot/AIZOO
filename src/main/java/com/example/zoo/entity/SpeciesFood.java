package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "species_foods")
public class SpeciesFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String speciesName; // Elephant

    @Column(nullable = false)
    private String foodName; // Grass

    private Double standardPortionKg; // Default portion per meal
    private Boolean isPrimaryPreference;

    public SpeciesFood() {}

    public SpeciesFood(String speciesName, String foodName, Double standardPortionKg, Boolean isPrimaryPreference) {
        this.speciesName = speciesName;
        this.foodName = foodName;
        this.standardPortionKg = standardPortionKg;
        this.isPrimaryPreference = isPrimaryPreference;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSpeciesName() { return speciesName; }
    public void setSpeciesName(String speciesName) { this.speciesName = speciesName; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public Double getStandardPortionKg() { return standardPortionKg; }
    public void setStandardPortionKg(Double standardPortionKg) { this.standardPortionKg = standardPortionKg; }

    public Boolean getIsPrimaryPreference() { return isPrimaryPreference; }
    public void setIsPrimaryPreference(Boolean isPrimaryPreference) { this.isPrimaryPreference = isPrimaryPreference; }
}
