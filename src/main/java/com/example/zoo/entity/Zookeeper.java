package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "zookeepers")
public class Zookeeper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Zookeeper A, Zookeeper B, Zookeeper C

    private String shiftStart; // 08:00 AM
    private String shiftEnd;   // 04:00 PM
    private Boolean isAvailable;

    public Zookeeper() {
        this.isAvailable = true;
    }

    public Zookeeper(String name, String shiftStart, String shiftEnd, Boolean isAvailable) {
        this();
        this.name = name;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.isAvailable = isAvailable;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getShiftStart() { return shiftStart; }
    public void setShiftStart(String shiftStart) { this.shiftStart = shiftStart; }

    public String getShiftEnd() { return shiftEnd; }
    public void setShiftEnd(String shiftEnd) { this.shiftEnd = shiftEnd; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
}
