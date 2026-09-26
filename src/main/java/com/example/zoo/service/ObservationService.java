package com.example.zoo.service;

import com.example.zoo.ai.ObservationReasoningService;
import com.example.zoo.entity.Animal;
import com.example.zoo.entity.Observation;
import com.example.zoo.event.ZooDataChangeEvent;
import com.example.zoo.repository.AnimalRepository;
import com.example.zoo.repository.ObservationRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObservationService {

    private final ObservationRepository observationRepository;
    private final AnimalRepository animalRepository;
    private final ObservationReasoningService observationReasoningService;
    private final ApplicationEventPublisher eventPublisher;

    public ObservationService(ObservationRepository observationRepository,
                              AnimalRepository animalRepository,
                              ObservationReasoningService observationReasoningService,
                              ApplicationEventPublisher eventPublisher) {
        this.observationRepository = observationRepository;
        this.animalRepository = animalRepository;
        this.observationReasoningService = observationReasoningService;
        this.eventPublisher = eventPublisher;
    }

    public Observation recordObservation(String animalId, String foodIntake, String waterIntake, String activityLevel, String text) {
        Optional<Animal> animalOpt = animalRepository.findByAnimalId(animalId);
        String animalName = animalOpt.isPresent() ? animalOpt.get().getName() : animalId;

        Observation obs = new Observation(animalId, animalName, foodIntake, waterIntake, activityLevel, text);

        // Run Probabilistic Reasoning pattern check
        String pattern = observationReasoningService.evaluateObservationPattern(animalId, foodIntake, waterIntake, activityLevel);
        obs.setAiPatternDetected(pattern);

        // Update Animal status if attention required
        if (animalOpt.isPresent()) {
            Animal a = animalOpt.get();
            if ("Attention Required".equalsIgnoreCase(pattern)) {
                a.setStatus("Needs Attention");
            } else if ("Monitor".equalsIgnoreCase(pattern)) {
                a.setStatus("Monitoring");
            } else {
                a.setStatus("Healthy");
            }
            animalRepository.save(a);
        }

        Observation saved = observationRepository.save(obs);

        String details = String.format("Animal       : %s\nName         : %s\nFood Intake  : %s\nWater Intake : %s\nActivity     : %s\nAI Pattern   : %s\nNotes        : \"%s\"",
                saved.getAnimalId(), saved.getAnimalName(), saved.getFoodIntake(), saved.getWaterIntake(), saved.getActivityLevel(), saved.getAiPatternDetected(), saved.getObservationText());

        eventPublisher.publishEvent(new ZooDataChangeEvent(this, "OBSERVATION_UPDATE", "Observation Logged for " + saved.getAnimalId(), details));

        return saved;
    }

    public List<Observation> getAllObservations() {
        return observationRepository.findAllByOrderByTimestampDesc();
    }
}
