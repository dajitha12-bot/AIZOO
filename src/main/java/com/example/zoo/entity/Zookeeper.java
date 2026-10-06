package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "zookeepers")
public class Zookeeper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String zookeeperId; // ZK001, ZK002, ZK003, ZK004, ZK005, ZK006

    @Column(nullable = false)
    private String name; // Arun, Priya, Kumar, Sneha, Ravi, Meena

    private String role; // Senior Keeper, Keeper, Assistant
    private String zone; // Zone A, Zone B, Zone C, Zone D
    private String workingHours; // 09:00 - 17:00
    private String status; // Available, On Leave, Busy
    private String password; // pass123

    private String shiftStart; // 08:00 AM
    private String shiftEnd;   // 04:00 PM
    private Boolean isAvailable;

    public Zookeeper() {
        this.status = "Available";
        this.isAvailable = true;
        this.password = "pass123";
    }

    public Zookeeper(String zookeeperId, String name, String role, String zone, String workingHours, String status, String password) {
        this.zookeeperId = zookeeperId;
        this.name = name;
        this.role = role;
        this.zone = zone;
        this.workingHours = workingHours;
        this.status = status;
        this.password = password != null ? password : "pass123";
        this.isAvailable = "Available".equalsIgnoreCase(status);
        if (workingHours != null && workingHours.contains("-")) {
            String[] parts = workingHours.split("-");
            this.shiftStart = parts[0].trim();
            this.shiftEnd = parts[1].trim();
        } else {
            this.shiftStart = "09:00 AM";
            this.shiftEnd = "05:00 PM";
        }
    }

    public Zookeeper(String name, String shiftStart, String shiftEnd, Boolean isAvailable) {
        this();
        this.name = name;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.isAvailable = isAvailable;
        this.workingHours = shiftStart + " - " + shiftEnd;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getZookeeperId() { return zookeeperId; }
    public void setZookeeperId(String zookeeperId) { this.zookeeperId = zookeeperId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public String getWorkingHours() { return workingHours; }
    public void setWorkingHours(String workingHours) { this.workingHours = workingHours; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
        this.isAvailable = "Available".equalsIgnoreCase(status);
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getShiftStart() { return shiftStart; }
    public void setShiftStart(String shiftStart) { this.shiftStart = shiftStart; }

    public String getShiftEnd() { return shiftEnd; }
    public void setShiftEnd(String shiftEnd) { this.shiftEnd = shiftEnd; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
}
