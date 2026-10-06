package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dangerous_food_knowledge")
public class DangerousFoodKnowledge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String speciesName; // e.g. Elephant, Lion, Monkey

    @Column(nullable = false)
    private String foodName; // e.g. Chocolate, Onions, Cooked Bones

    private String riskLevel; // CRITICAL, HIGH, MODERATE
    private String dangerReason; // e.g. Contains persin causing respiratory distress and organ failure.
    private String symptomWarning; // e.g. Hemolytic anemia, severe stomach inflammation.

    public DangerousFoodKnowledge() {}

    public DangerousFoodKnowledge(String speciesName, String foodName, String riskLevel, String dangerReason, String symptomWarning) {
        this.speciesName = speciesName;
        this.foodName = foodName;
        this.riskLevel = riskLevel;
        this.dangerReason = dangerReason;
        this.symptomWarning = symptomWarning;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSpeciesName() { return speciesName; }
    public void setSpeciesName(String speciesName) { this.speciesName = speciesName; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public String getDangerReason() { return dangerReason; }
    public void setDangerReason(String dangerReason) { this.dangerReason = dangerReason; }

    public String getSymptomWarning() { return symptomWarning; }
    public void setSymptomWarning(String symptomWarning) { this.symptomWarning = symptomWarning; }
}
