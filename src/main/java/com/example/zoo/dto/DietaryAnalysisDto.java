package com.example.zoo.dto;

import com.example.zoo.entity.DangerousFoodKnowledge;
import com.example.zoo.entity.SpeciesFood;

import java.util.List;

public class DietaryAnalysisDto {

    private String animalName;
    private String species;
    private Integer age;
    private String ageCategory; // Juvenile, Adult, Senior
    private List<SpeciesFood> suitableFoods;
    private List<DangerousFoodKnowledge> dangerousFoods;
    private String ageNutritionAdvisory;
    private String aiSummary;

    public DietaryAnalysisDto() {}

    public DietaryAnalysisDto(String animalName, String species, Integer age, String ageCategory,
                              List<SpeciesFood> suitableFoods, List<DangerousFoodKnowledge> dangerousFoods,
                              String ageNutritionAdvisory, String aiSummary) {
        this.animalName = animalName;
        this.species = species;
        this.age = age;
        this.ageCategory = ageCategory;
        this.suitableFoods = suitableFoods;
        this.dangerousFoods = dangerousFoods;
        this.ageNutritionAdvisory = ageNutritionAdvisory;
        this.aiSummary = aiSummary;
    }

    public String getAnimalName() { return animalName; }
    public void setAnimalName(String animalName) { this.animalName = animalName; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getAgeCategory() { return ageCategory; }
    public void setAgeCategory(String ageCategory) { this.ageCategory = ageCategory; }

    public List<SpeciesFood> getSuitableFoods() { return suitableFoods; }
    public void setSuitableFoods(List<SpeciesFood> suitableFoods) { this.suitableFoods = suitableFoods; }

    public List<DangerousFoodKnowledge> getDangerousFoods() { return dangerousFoods; }
    public void setDangerousFoods(List<DangerousFoodKnowledge> dangerousFoods) { this.dangerousFoods = dangerousFoods; }

    public String getAgeNutritionAdvisory() { return ageNutritionAdvisory; }
    public void setAgeNutritionAdvisory(String ageNutritionAdvisory) { this.ageNutritionAdvisory = ageNutritionAdvisory; }

    public String getAiSummary() { return aiSummary; }
    public void setAiSummary(String aiSummary) { this.aiSummary = aiSummary; }
}
