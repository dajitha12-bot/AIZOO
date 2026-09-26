package com.example.zoo.planner;

import com.example.zoo.entity.Animal;
import com.example.zoo.entity.FeedingTask;
import com.example.zoo.entity.Food;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BacktrackingSolver {

    public static class Assignment {
        public String foodName;
        public String keeperName;
        public Double quantityKg;

        public Assignment(String foodName, String keeperName, Double quantityKg) {
            this.foodName = foodName;
            this.keeperName = keeperName;
            this.quantityKg = quantityKg;
        }
    }

    /**
     * Entry point for recursive depth-first backtracking search algorithm.
     */
    public List<FeedingTask> solve(List<FeedingCSPService.CSPVariable> variables, Map<String, Food> foodInventoryMap) {
        List<FeedingTask> resultTasks = new ArrayList<>();
        Map<String, Double> remainingStockMap = new HashMap<>();
        for (Map.Entry<String, Food> entry : foodInventoryMap.entrySet()) {
            remainingStockMap.put(entry.getKey(), entry.getValue().getRemainingQuantity());
        }

        Map<Integer, Assignment> currentAssignments = new HashMap<>();
        boolean success = backtrack(0, variables, currentAssignments, remainingStockMap);

        if (success) {
            for (int i = 0; i < variables.size(); i++) {
                FeedingCSPService.CSPVariable var = variables.get(i);
                Assignment assign = currentAssignments.get(i);

                String explanation = "Selected " + assign.foodName + " for " + var.getAnimal().getName() +
                        " assigned to " + assign.keeperName + " at " + var.getSlotTime() + " via CSP Backtracking Search.";

                FeedingTask task = new FeedingTask(
                        null,
                        var.getAnimal().getAnimalId(),
                        var.getAnimal().getName(),
                        var.getAnimal().getSpecies(),
                        assign.foodName,
                        assign.quantityKg,
                        var.getSlotTime(),
                        assign.keeperName,
                        explanation
                );
                resultTasks.add(task);
            }
        }
        return resultTasks;
    }

    /**
     * Pure recursive backtracking search.
     */
    private boolean backtrack(int varIndex,
                              List<FeedingCSPService.CSPVariable> variables,
                              Map<Integer, Assignment> assignments,
                              Map<String, Double> stockMap) {

        // Base case: All variables assigned
        if (varIndex == variables.size()) {
            return true;
        }

        FeedingCSPService.CSPVariable currentVar = variables.get(varIndex);
        Animal animal = currentVar.getAnimal();

        for (String food : currentVar.getFoodDomain()) {
            Double reqQty = 15.0; // Default portion
            if ("Elephant".equalsIgnoreCase(animal.getSpecies())) reqQty = 25.0;
            else if ("Lion".equalsIgnoreCase(animal.getSpecies())) reqQty = 15.0;
            else if ("Giraffe".equalsIgnoreCase(animal.getSpecies())) reqQty = 12.0;

            Double availableStock = stockMap.getOrDefault(food, 0.0);
            if (availableStock < reqQty) {
                // Stock constraint violation -> prune branch
                continue;
            }

            for (String keeper : currentVar.getKeeperDomain()) {

                // Check keeper conflict constraint (no overlapping task at same slot time)
                if (hasKeeperOverlap(varIndex, currentVar.getSlotTime(), keeper, variables, assignments)) {
                    // Conflict detected -> Backtrack branch!
                    continue;
                }

                // Make assignment
                assignments.put(varIndex, new Assignment(food, keeper, reqQty));
                stockMap.put(food, availableStock - reqQty);

                // Recursive step
                if (backtrack(varIndex + 1, variables, assignments, stockMap)) {
                    return true;
                }

                // Backtrack (undo assignment)
                assignments.remove(varIndex);
                stockMap.put(food, availableStock);
            }
        }

        return false; // Triggers backtrack
    }

    private boolean hasKeeperOverlap(int currentIndex,
                                     String slotTime,
                                     String keeper,
                                     List<FeedingCSPService.CSPVariable> variables,
                                     Map<Integer, Assignment> assignments) {
        for (Map.Entry<Integer, Assignment> entry : assignments.entrySet()) {
            int prevIdx = entry.getKey();
            Assignment prevAssign = entry.getValue();
            String prevTime = variables.get(prevIdx).getSlotTime();

            if (prevAssign.keeperName.equalsIgnoreCase(keeper) && prevTime.equalsIgnoreCase(slotTime)) {
                return true;
            }
        }
        return false;
    }
}
