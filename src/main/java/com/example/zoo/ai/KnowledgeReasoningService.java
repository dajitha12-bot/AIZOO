package com.example.zoo.ai;

import com.example.zoo.dto.DietaryAnalysisDto;
import com.example.zoo.entity.Animal;
import com.example.zoo.entity.DangerousFoodKnowledge;
import com.example.zoo.entity.SpeciesFood;
import com.example.zoo.repository.DangerousFoodKnowledgeRepository;
import com.example.zoo.repository.SpeciesFoodRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeReasoningService {

    private final KnowledgeRepresentationService knowledgeRepresentationService;
    private final SpeciesFoodRepository speciesFoodRepository;
    private final DangerousFoodKnowledgeRepository dangerousFoodKnowledgeRepository;

    public KnowledgeReasoningService(KnowledgeRepresentationService knowledgeRepresentationService,
                                     SpeciesFoodRepository speciesFoodRepository,
                                     DangerousFoodKnowledgeRepository dangerousFoodKnowledgeRepository) {
        this.knowledgeRepresentationService = knowledgeRepresentationService;
        this.speciesFoodRepository = speciesFoodRepository;
        this.dangerousFoodKnowledgeRepository = dangerousFoodKnowledgeRepository;
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

    /**
     * Analyzes diet safety, suitable foods, and toxic food hazards for a queried animal.
     */
    public DietaryAnalysisDto analyzeAnimalDietarySafety(String animalName, String species, Integer age) {
        if (animalName == null || animalName.trim().isEmpty()) {
            animalName = "Query Animal";
        }
        if (species == null || species.trim().isEmpty()) {
            species = "Elephant";
        }
        if (age == null || age < 0) {
            age = 5;
        }

        List<SpeciesFood> suitableFoods = speciesFoodRepository.findBySpeciesNameIgnoreCase(species);
        if (suitableFoods == null || suitableFoods.isEmpty()) {
            suitableFoods = speciesFoodRepository.findBySpeciesName(species);
        }

        List<DangerousFoodKnowledge> dangerousFoods = dangerousFoodKnowledgeRepository.findBySpeciesNameIgnoreCase(species);

        String ageCategory;
        String ageAdvisory;
        if (age < 3) {
            ageCategory = "Juvenile / Young";
            ageAdvisory = "High-protein growth phase active. Digestibility is critical — feed standard portions adjusted to 80% volume split across 3-4 feedings daily. Ensure high hydration and close monitoring.";
        } else if (age <= 15) {
            ageCategory = "Adult (Prime Maintenance)";
            ageAdvisory = "Standard maintenance diet active. High fiber, balanced minerals, and standard portions (100%) split into regular scheduled feedings twice daily.";
        } else {
            ageCategory = "Senior / Aging";
            ageAdvisory = "Geriatric nutrition care active. Joint support and soft digestible foods recommended. Reduce heavy fat/protein loads slightly; adjust standard portions to 90% volume with joint-care supplements.";
        }

        String aiSummary = String.format("AI Knowledge Engine evaluated %s (Species: %s, Age: %d yrs — %s). Identified %d suitable food options and %d toxic food hazards in persistent SQLite knowledge database.",
                animalName, species, age, ageCategory, suitableFoods.size(), dangerousFoods.size());

        return new DietaryAnalysisDto(animalName, species, age, ageCategory, suitableFoods, dangerousFoods, ageAdvisory, aiSummary);
    }
}
