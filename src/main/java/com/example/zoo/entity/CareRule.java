package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "care_rules")
public class CareRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ruleName;

    private String conditionExpr; // IF foodIntake == 'Low' AND activityLevel == 'Low'
    private String actionRecommendation; // Monitor closely. Consider veterinary review if persistent.

    public CareRule() {}

    public CareRule(String ruleName, String conditionExpr, String actionRecommendation) {
        this.ruleName = ruleName;
        this.conditionExpr = conditionExpr;
        this.actionRecommendation = actionRecommendation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getConditionExpr() { return conditionExpr; }
    public void setConditionExpr(String conditionExpr) { this.conditionExpr = conditionExpr; }

    public String getActionRecommendation() { return actionRecommendation; }
    public void setActionRecommendation(String actionRecommendation) { this.actionRecommendation = actionRecommendation; }
}
