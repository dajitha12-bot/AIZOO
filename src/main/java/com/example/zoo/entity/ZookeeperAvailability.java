package com.example.zoo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "zookeeper_availability")
public class ZookeeperAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String zookeeperName;

    private String availableFrom; // 08:00 AM
    private String availableTo;   // 04:00 PM
    private Boolean isPresent;

    public ZookeeperAvailability() {
        this.isPresent = true;
    }

    public ZookeeperAvailability(String zookeeperName, String availableFrom, String availableTo, Boolean isPresent) {
        this();
        this.zookeeperName = zookeeperName;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.isPresent = isPresent;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getZookeeperName() { return zookeeperName; }
    public void setZookeeperName(String zookeeperName) { this.zookeeperName = zookeeperName; }

    public String getAvailableFrom() { return availableFrom; }
    public void setAvailableFrom(String availableFrom) { this.availableFrom = availableFrom; }

    public String getAvailableTo() { return availableTo; }
    public void setAvailableTo(String availableTo) { this.availableTo = availableTo; }

    public Boolean getIsPresent() { return isPresent; }
    public void setIsPresent(Boolean isPresent) { this.isPresent = isPresent; }
}
