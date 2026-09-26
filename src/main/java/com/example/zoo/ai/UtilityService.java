package com.example.zoo.ai;

import com.example.zoo.entity.Animal;
import com.example.zoo.entity.FeedingTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityService {

    /**
     * Calculates overall utility score for a candidate feeding plan.
     * Utility = SuitabilityScore + PreferenceScore + AvailabilityScore - ScheduleDisruptionCost
     */
    public double calculatePlanUtility(List<FeedingTask> tasks, List<Animal> animals) {
        double utility = 0.0;

        for (FeedingTask task : tasks) {
            double taskUtility = 50.0; // Base score for feasible task

            // Animal Preference Bonus (+20)
            for (Animal a : animals) {
                if (a.getAnimalId().equalsIgnoreCase(task.getAnimalId())) {
                    if (a.getFoodPreference() != null && a.getFoodPreference().equalsIgnoreCase(task.getFoodName())) {
                        taskUtility += 20.0;
                    }
                    break;
                }
            }

            // High Quantity / Stock Efficiency Bonus (+10)
            if (task.getQuantityKg() != null && task.getQuantityKg() > 10.0) {
                taskUtility += 10.0;
            }

            utility += taskUtility;
        }

        return Math.round(utility * 10.0) / 10.0;
    }
}
