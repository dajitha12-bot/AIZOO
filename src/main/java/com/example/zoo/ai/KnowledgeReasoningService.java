package com.example.zoo.ai;

import com.example.zoo.entity.Animal;
import com.example.zoo.entity.SpeciesFood;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeReasoningService {

    private final KnowledgeRepresentationService knowledgeRepresentationService;

    public KnowledgeReasoningService(KnowledgeRepresentationService knowledgeRepresentationService) {
        this.knowledgeRepresentationService = knowledgeRepresentationService;
    }

    /**
     * Infers candidate suitable foods for an animal based on species taxonomy, age adjustments, and preferences.
     */
    public List<String> inferSuitableFoods(Animal animal) {
        List<SpeciesFood> speciesFoods = knowledgeRepresentationService.getSuitableFoodsForSpecies(animal.getSpecies());
        List<String> suitableFoods = new ArrayList<>();

        for (SpeciesFood sf : speciesFoods) {
            suitableFoods.add(sf.getFoodName());
        }

        // Knowledge reasoning rule: If animal has a specific valid food preference, prioritize it first
        if (animal.getFoodPreference() != null && !animal.getFoodPreference().isEmpty()) {
            String pref = animal.getFoodPreference();
            if (suitableFoods.contains(pref)) {
                suitableFoods.remove(pref);
                suitableFoods.add(0, pref);
            }
        }
        return suitableFoods;
    }

    /**
     * Calculates age-adjusted daily quantity for an animal species.
     */
    public double calculateRequiredQuantityKg(Animal animal, String foodName) {
        List<SpeciesFood> speciesFoods = knowledgeRepresentationService.getSuitableFoodsForSpecies(animal.getSpecies());
        double basePortion = 5.0;

        for (SpeciesFood sf : speciesFoods) {
            if (sf.getFoodName().equalsIgnoreCase(foodName)) {
                basePortion = sf.getStandardPortionKg();
                break;
            }
        }

        // Age factor: Young animals (< 3 yrs) eat 80%, prime adults (3-15 yrs) eat 100%, senior (> 15 yrs) eat 90%
        double ageFactor = 1.0;
        if (animal.getAge() != null) {
            if (animal.getAge() < 3) ageFactor = 0.8;
            else if (animal.getAge() > 15) ageFactor = 0.9;
        }

        return Math.round(basePortion * ageFactor * 10.0) / 10.0;
    }
}
