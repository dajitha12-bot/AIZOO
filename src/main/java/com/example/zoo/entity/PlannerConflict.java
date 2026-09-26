package com.example.zoo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "planner_conflicts")
public class PlannerConflict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conflictType; // FOOD_SHORTAGE, ZOOKEEPER_OVERLAP, KEEPER_UNAVAILABLE
    private String affectedTarget; // Banana, Zookeeper A
    private Double availableQty;
    private Double requiredQty;
    private String description;
    private Boolean isResolved;
    private LocalDateTime timestamp;

    public PlannerConflict() {
        this.timestamp = LocalDateTime.now();
        this.isResolved = false;
    }

    public PlannerConflict(String conflictType, String affectedTarget, Double availableQty, Double requiredQty, String description) {
        this();
        this.conflictType = conflictType;
        this.affectedTarget = affectedTarget;
        this.availableQty = availableQty;
        this.requiredQty = requiredQty;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getConflictType() { return conflictType; }
    public void setConflictType(String conflictType) { this.conflictType = conflictType; }

    public String getAffectedTarget() { return affectedTarget; }
    public void setAffectedTarget(String affectedTarget) { this.affectedTarget = affectedTarget; }

    public Double getAvailableQty() { return availableQty; }
    public void setAvailableQty(Double availableQty) { this.availableQty = availableQty; }

    public Double getRequiredQty() { return requiredQty; }
    public void setRequiredQty(Double requiredQty) { this.requiredQty = requiredQty; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsResolved() { return isResolved; }
    public void setIsResolved(Boolean isResolved) { this.isResolved = isResolved; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
