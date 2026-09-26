package com.example.zoo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_decisions")
public class AiDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category; // FEEDING_SELECTION, CONFLICT_RESOLUTION, ASTAR_REPLAN, CARE_ALERT
    private String title;
    @Column(length = 2000)
    private String explanation;
    private LocalDateTime timestamp;

    public AiDecision() {
        this.timestamp = LocalDateTime.now();
    }

    public AiDecision(String category, String title, String explanation) {
        this();
        this.category = category;
        this.title = title;
        this.explanation = explanation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
