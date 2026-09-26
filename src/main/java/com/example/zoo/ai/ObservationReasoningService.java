package com.example.zoo.ai;

import com.example.zoo.entity.Observation;
import com.example.zoo.repository.ObservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObservationReasoningService {

    private final ObservationRepository observationRepository;

    public ObservationReasoningService(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    /**
     * Evaluates probabilistic pattern score from observation input and historical logs.
     * Returns "Normal", "Monitor", or "Attention Required".
     */
    public String evaluateObservationPattern(String animalId, String foodIntake, String waterIntake, String activityLevel) {
        double anomalyScore = 0.0;

        // 1. Food Intake Weight (Max 40)
        if ("Low".equalsIgnoreCase(foodIntake)) anomalyScore += 40.0;
        else if ("High".equalsIgnoreCase(foodIntake)) anomalyScore += 5.0;

        // 2. Water Intake Weight (Max 30)
        if ("Low".equalsIgnoreCase(waterIntake)) anomalyScore += 30.0;

        // 3. Activity Level Weight (Max 30)
        if ("Low".equalsIgnoreCase(activityLevel)) anomalyScore += 30.0;

        // 4. Historical Bayes adjustment (prior probability factor)
        List<Observation> history = observationRepository.findByAnimalIdOrderByTimestampDesc(animalId);
        if (!history.isEmpty()) {
            long lowIntakeCount = history.stream().filter(o -> "Low".equalsIgnoreCase(o.getFoodIntake())).count();
            double priorProb = (double) lowIntakeCount / history.size();
            anomalyScore += (priorProb * 15.0); // Boost score if recurring issue
        }

        if (anomalyScore >= 60.0) {
            return "Attention Required";
        } else if (anomalyScore >= 35.0) {
            return "Monitor";
        } else {
            return "Normal";
        }
    }
}
