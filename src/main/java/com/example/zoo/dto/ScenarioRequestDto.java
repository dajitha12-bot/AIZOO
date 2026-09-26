package com.example.zoo.dto;

import java.util.List;

public class ScenarioRequestDto {

    private String scenarioType; // FOOD_QUANTITY, KEEPER_UNAVAILABLE, TIME_CHANGE, FOOD_UNAVAILABLE, REQUIREMENT_CHANGE, MULTIPLE
    private String foodName;
    private Double currentFoodQty;
    private Double scenarioFoodQty;
    private String zookeeperName;
    private String keeperUnavailableFrom;
    private String keeperUnavailableTo;
    private String animalId;
    private String currentFeedingTime;
    private String scenarioFeedingTime;
    private String newFoodPreference;

    public ScenarioRequestDto() {}

    public String getScenarioType() { return scenarioType; }
    public void setScenarioType(String scenarioType) { this.scenarioType = scenarioType; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public Double getCurrentFoodQty() { return currentFoodQty; }
    public void setCurrentFoodQty(Double currentFoodQty) { this.currentFoodQty = currentFoodQty; }

    public Double getScenarioFoodQty() { return scenarioFoodQty; }
    public void setScenarioFoodQty(Double scenarioFoodQty) { this.scenarioFoodQty = scenarioFoodQty; }

    public String getZookeeperName() { return zookeeperName; }
    public void setZookeeperName(String zookeeperName) { this.zookeeperName = zookeeperName; }

    public String getKeeperUnavailableFrom() { return keeperUnavailableFrom; }
    public void setKeeperUnavailableFrom(String keeperUnavailableFrom) { this.keeperUnavailableFrom = keeperUnavailableFrom; }

    public String getKeeperUnavailableTo() { return keeperUnavailableTo; }
    public void setKeeperUnavailableTo(String keeperUnavailableTo) { this.keeperUnavailableTo = keeperUnavailableTo; }

    public String getAnimalId() { return animalId; }
    public void setAnimalId(String animalId) { this.animalId = animalId; }

    public String getCurrentFeedingTime() { return currentFeedingTime; }
    public void setCurrentFeedingTime(String currentFeedingTime) { this.currentFeedingTime = currentFeedingTime; }

    public String getScenarioFeedingTime() { return scenarioFeedingTime; }
    public void setScenarioFeedingTime(String scenarioFeedingTime) { this.scenarioFeedingTime = scenarioFeedingTime; }

    public String getNewFoodPreference() { return newFoodPreference; }
    public void setNewFoodPreference(String newFoodPreference) { this.newFoodPreference = newFoodPreference; }
}
