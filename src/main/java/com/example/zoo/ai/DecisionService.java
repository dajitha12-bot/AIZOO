package com.example.zoo.ai;

import com.example.zoo.entity.Food;
import com.example.zoo.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DecisionService {

    private final FoodRepository foodRepository;

    public DecisionService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    /**
     * Decision Making Under Uncertainty: When preferred food is depleted/unavailable,
     * evaluates feasible alternatives and returns best candidate.
     */
    public String selectAlternativeFood(String preferredFood, List<String> suitableFoods) {
        // Try preferred food first if stock > 0
        Optional<Food> prefOpt = foodRepository.findByName(preferredFood);
        if (prefOpt.isPresent() && Boolean.TRUE.equals(prefOpt.get().getIsAvailable()) && prefOpt.get().getRemainingQuantity() > 0) {
            return preferredFood;
        }

        // Search suitable alternative with highest remaining quantity
        String bestAlternative = null;
        double maxQuantity = 0.0;

        for (String foodName : suitableFoods) {
            Optional<Food> fOpt = foodRepository.findByName(foodName);
            if (fOpt.isPresent()) {
                Food food = fOpt.get();
                if (Boolean.TRUE.equals(food.getIsAvailable()) && food.getRemainingQuantity() > maxQuantity) {
                    maxQuantity = food.getRemainingQuantity();
                    bestAlternative = food.getName();
                }
            }
        }

        return bestAlternative != null ? bestAlternative : suitableFoods.get(0);
    }
}
