package com.example.zoo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "feeding_plans")
public class FeedingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate planDate;

    private String status; // Active, Re-planned, Completed
    private Double utilityScore; // Multi-objective utility score
    private String solverMethod; // CSP + Backtracking + Utility / A* Replanned
    private LocalDateTime generatedTime;

    public FeedingPlan() {
        this.planDate = LocalDate.now();
        this.status = "Active";
        this.generatedTime = LocalDateTime.now();
        this.solverMethod = "CSP + Backtracking";
    }

    public FeedingPlan(LocalDate planDate, Double utilityScore, String solverMethod) {
        this();
        this.planDate = planDate;
        this.utilityScore = utilityScore;
        this.solverMethod = solverMethod;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getPlanDate() { return planDate; }
    public void setPlanDate(LocalDate planDate) { this.planDate = planDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getUtilityScore() { return utilityScore; }
    public void setUtilityScore(Double utilityScore) { this.utilityScore = utilityScore; }

    public String getSolverMethod() { return solverMethod; }
    public void setSolverMethod(String solverMethod) { this.solverMethod = solverMethod; }

    public LocalDateTime getGeneratedTime() { return generatedTime; }
    public void setGeneratedTime(LocalDateTime generatedTime) { this.generatedTime = generatedTime; }
}
