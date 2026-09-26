package com.example.zoo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "food_availability")
public class FoodAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String foodName;

    private Double availableQuantity;
    private String unit;
    private Boolean isAvailable;
    private LocalDate date;

    public FoodAvailability() {
        this.date = LocalDate.now();
        this.unit = "kg";
        this.isAvailable = true;
    }

    public FoodAvailability(String foodName, Double availableQuantity, String unit, Boolean isAvailable, LocalDate date) {
        this.foodName = foodName;
        this.availableQuantity = availableQuantity;
        this.unit = unit;
        this.isAvailable = isAvailable;
        this.date = date != null ? date : LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public Double getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(Double availableQuantity) { this.availableQuantity = availableQuantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
