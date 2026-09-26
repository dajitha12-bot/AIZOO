package com.example.zoo.ai;

import com.example.zoo.entity.Food;
import com.example.zoo.entity.Zookeeper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConstraintPropagationService {

    /**
     * Constraint Propagation: Removes impossible values (food with 0 stock, unavailable keepers)
     * before search begins.
     */
    public Map<String, List<String>> filterCandidateDomains(List<String> candidateFoods,
                                                             Map<String, Food> foodMap,
                                                             List<Zookeeper> allKeepers) {
        Map<String, List<String>> domains = new HashMap<>();

        // 1. Filter Foods
        List<String> validFoods = new ArrayList<>();
        for (String foodName : candidateFoods) {
            Food food = foodMap.get(foodName);
            if (food != null && Boolean.TRUE.equals(food.getIsAvailable()) && food.getRemainingQuantity() > 0) {
                validFoods.add(foodName);
            }
        }
        domains.put("FOODS", validFoods);

        // 2. Filter Zookeepers
        List<String> validKeepers = new ArrayList<>();
        for (Zookeeper zk : allKeepers) {
            if (Boolean.TRUE.equals(zk.getIsAvailable())) {
                validKeepers.add(zk.getName());
            }
        }
        domains.put("KEEPERS", validKeepers);

        return domains;
    }
}
