package com.example.zoo.ai;

import com.example.zoo.entity.Food;
import com.example.zoo.entity.Zookeeper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleEngineService {

    /**
     * IF food is not suitable THEN reject food
     */
    public boolean isFoodSuitableForSpecies(String foodName, List<String> suitableFoods) {
        if (foodName == null || suitableFoods == null) return false;
        return suitableFoods.contains(foodName);
    }

    /**
     * IF available quantity is insufficient THEN create shortage condition
     */
    public boolean isStockSufficient(Food food, double requiredQty) {
        if (food == null || Boolean.FALSE.equals(food.getIsAvailable())) return false;
        return food.getRemainingQuantity() >= requiredQty;
    }

    /**
     * IF zookeeper is unavailable THEN reject assignment
     */
    public boolean isZookeeperAvailable(Zookeeper zookeeper) {
        return zookeeper != null && Boolean.TRUE.equals(zookeeper.getIsAvailable());
    }

    /**
     * IF two tasks overlap for the same zookeeper at the exact same time THEN create scheduling conflict
     */
    public boolean isTaskScheduleOverlapping(String time1, String time2) {
        if (time1 == null || time2 == null) return false;
        return time1.equalsIgnoreCase(time2);
    }
}
