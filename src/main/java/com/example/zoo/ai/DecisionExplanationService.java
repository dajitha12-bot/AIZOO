package com.example.zoo.ai;

import org.springframework.stereotype.Service;

@Service
public class DecisionExplanationService {

    public String generateFoodSelectionExplanation(String animalName, String species, String selectedFood, String preference, double availableQty, double utility) {
        StringBuilder sb = new StringBuilder();
        sb.append("Reason:\n");
        sb.append("1. Suitable for ").append(species).append(" (Knowledge Base lookup)\n");
        if (selectedFood.equalsIgnoreCase(preference)) {
            sb.append("2. Primary animal preference matched (").append(preference).append(")\n");
        } else {
            sb.append("2. Substitute selected due to stock availability\n");
        }
        sb.append("3. Stock check passed: ").append(availableQty).append(" kg available\n");
        sb.append("4. Utility Score calculated: ").append(utility);
        return sb.toString();
    }

    public String generateReplanExplanation(String oldKeeper, String newKeeper, String oldTime, String newTime, int cost) {
        StringBuilder sb = new StringBuilder();
        sb.append("Reason:\n");
        sb.append("Zookeeper ").append(oldKeeper).append(" became unavailable.\n");
        sb.append("Old: ").append(oldTime).append(" -> Keeper ").append(oldKeeper).append("\n");
        sb.append("New: ").append(newTime).append(" -> Keeper ").append(newKeeper).append("\n");
        sb.append("A* Heuristic Search Cost f(n): ").append(cost);
        return sb.toString();
    }
}
