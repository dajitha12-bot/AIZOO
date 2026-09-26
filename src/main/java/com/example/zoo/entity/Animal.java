package com.example.zoo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String animalId; // e.g. E101

    @Column(nullable = false)
    private String name; // e.g. Aruna

    @Column(nullable = false)
    private String species; // e.g. Elephant

    @Column(nullable = false)
    private Integer age; // e.g. 12

    private String gender; // Female / Male
    private String foodPreference; // e.g. Banana
    private String notes;
    private String imagePath; // e.g. /images/animals/elephant.png
    private String status; // Healthy, Monitoring, Needs Attention
    private LocalDateTime createdAt;

    public Animal() {
        this.createdAt = LocalDateTime.now();
        this.status = "Healthy";
    }

    public Animal(String animalId, String name, String species, Integer age, String gender, String foodPreference, String notes, String imagePath) {
        this();
        this.animalId = animalId;
        this.name = name;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.foodPreference = foodPreference;
        this.notes = notes;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAnimalId() { return animalId; }
    public void setAnimalId(String animalId) { this.animalId = animalId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getFoodPreference() { return foodPreference; }
    public void setFoodPreference(String foodPreference) { this.foodPreference = foodPreference; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
