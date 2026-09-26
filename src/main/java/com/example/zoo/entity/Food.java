package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Grass, Hay, Banana, etc.

    private Double totalQuantity; // Available inventory in kg
    private Double usedQuantity; // Used inventory in kg
    private String unit; // kg, pieces, etc.
    private Boolean isAvailable; // true/false

    public Food() {
        this.usedQuantity = 0.0;
        this.unit = "kg";
        this.isAvailable = true;
    }

    public Food(String name, Double totalQuantity, String unit, Boolean isAvailable) {
        this();
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.unit = unit;
        this.isAvailable = isAvailable;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Double totalQuantity) { this.totalQuantity = totalQuantity; }

    public Double getUsedQuantity() { return usedQuantity; }
    public void setUsedQuantity(Double usedQuantity) { this.usedQuantity = usedQuantity; }

    public Double getRemainingQuantity() {
        return Math.max(0.0, (totalQuantity != null ? totalQuantity : 0.0) - (usedQuantity != null ? usedQuantity : 0.0));
    }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
}
