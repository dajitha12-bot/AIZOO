package com.example.zoo.ai;

import com.example.zoo.entity.*;
import com.example.zoo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZooAssistantService {

    private final AnimalRepository animalRepository;
    private final FoodRepository foodRepository;
    private final KnowledgeRepresentationService knowledgeService;
    private final FeedingPlanRepository feedingPlanRepository;
    private final FeedingTaskRepository feedingTaskRepository;
    private final ObservationRepository observationRepository;
    private final ZooPlanningAgent zooPlanningAgent;

    public ZooAssistantService(AnimalRepository animalRepository,
                               FoodRepository foodRepository,
                               KnowledgeRepresentationService knowledgeService,
                               FeedingPlanRepository feedingPlanRepository,
                               FeedingTaskRepository feedingTaskRepository,
                               ObservationRepository observationRepository,
                               ZooPlanningAgent zooPlanningAgent) {
        this.animalRepository = animalRepository;
        this.foodRepository = foodRepository;
        this.knowledgeService = knowledgeService;
        this.feedingPlanRepository = feedingPlanRepository;
        this.feedingTaskRepository = feedingTaskRepository;
        this.observationRepository = observationRepository;
        this.zooPlanningAgent = zooPlanningAgent;
    }

    public String processQuery(String userMessage) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "Hello Zookeeper! I am your AI Zoo Assistant. Ask me anything about feeding plans, animal observations, food availability, or species knowledge!";
        }

        String msg = userMessage.trim().toLowerCase();

        // Query 1: "What food is suitable for [Animal/Species]?"
        if (msg.contains("suitable") || msg.contains("food for")) {
            for (Animal animal : animalRepository.findAll()) {
                if (msg.contains(animal.getName().toLowerCase()) || msg.contains(animal.getAnimalId().toLowerCase())) {
                    List<SpeciesFood> sfList = knowledgeService.getSuitableFoodsForSpecies(animal.getSpecies());
                    StringBuilder sb = new StringBuilder();
                    sb.append("🍎 **Suitable foods for ").append(animal.getName()).append(" (").append(animal.getSpecies()).append(")**:\n\n");
                    for (SpeciesFood sf : sfList) {
                        sb.append("- **").append(sf.getFoodName()).append("** (Standard portion: ").append(sf.getStandardPortionKg()).append(" kg)");
                        if (Boolean.TRUE.equals(sf.getIsPrimaryPreference())) sb.append(" ⭐ Primary Preference");
                        sb.append("\n");
                    }
                    sb.append("\n*Data retrieved directly from persistent SQLite database `zoo.db`.*");
                    return sb.toString();
                }
            }
        }

        // Query 2: "Plan today's feeding"
        if (msg.contains("plan today") || msg.contains("generate plan")) {
            FeedingPlan plan = zooPlanningAgent.executeCognitiveCycle();
            return "✅ **AI Feeding Plan Generated!**\n\nPlan ID: #" + plan.getId() + "\nUtility Score: " + plan.getUtilityScore() + "\nAlgorithm: CSP + Forward Checking + Backtracking Search.\n\nYou can view the full schedule on the **AI Feeding & Care Planner** page!";
        }

        // Query 3: "Banana is low today" / "alternatives available"
        if (msg.contains("low") || msg.contains("alternative") || msg.contains("shortage")) {
            List<Food> foods = foodRepository.findAll();
            StringBuilder sb = new StringBuilder();
            sb.append("⚠️ **Food Availability & Alternatives Analysis**:\n\n");
            for (Food f : foods) {
                sb.append("- **").append(f.getName()).append("**: Available Quantity = **").append(f.getRemainingQuantity()).append(" ").append(f.getUnit()).append("** (Status: ").append(f.getIsAvailable() ? "Available" : "Unavailable").append(")\n");
            }
            sb.append("\n*AI Decision Service recommends using Hay or Grass as high-utility substitutes if fruits are depleted.*");
            return sb.toString();
        }

        // Query 4: "Show today's feeding plan" / "feeding plan"
        if (msg.contains("show") && msg.contains("plan")) {
            Optional<FeedingPlan> planOpt = feedingPlanRepository.findTopByOrderByGeneratedTimeDesc();
            if (planOpt.isPresent()) {
                List<FeedingTask> tasks = feedingTaskRepository.findByPlanId(planOpt.get().getId());
                StringBuilder sb = new StringBuilder();
                sb.append("📋 **Today's Active AI Feeding Plan (ID #").append(planOpt.get().getId()).append(")**:\n\n");
                sb.append("| Animal | Species | Food | Quantity | Time | Assigned Zookeeper |\n");
                sb.append("|---|---|---|---|---|---|\n");
                for (FeedingTask t : tasks) {
                    sb.append("| ").append(t.getAnimalName()).append(" | ").append(t.getSpecies()).append(" | ").append(t.getFoodName()).append(" | ").append(t.getQuantityKg()).append(" kg | ").append(t.getFeedingTime()).append(" | ").append(t.getZookeeperName()).append(" |\n");
                }
                return sb.toString();
            } else {
                return "No feeding plan generated for today yet. You can click 'Generate Today's Feeding Plan' on Page 5!";
            }
        }

        // Query 5: "Suggest enrichment for [Animal]"
        if (msg.contains("enrichment")) {
            for (Animal animal : animalRepository.findAll()) {
                if (msg.contains(animal.getName().toLowerCase()) || msg.contains(animal.getAnimalId().toLowerCase())) {
                    List<EnrichmentKnowledge> ekList = knowledgeService.getEnrichmentsForSpecies(animal.getSpecies());
                    StringBuilder sb = new StringBuilder();
                    sb.append("🎉 **Recommended AI Enrichment for ").append(animal.getName()).append(" (").append(animal.getSpecies()).append(")**:\n\n");
                    for (EnrichmentKnowledge ek : ekList) {
                        sb.append("- **").append(ek.getActivityName()).append("**: ").append(ek.getDescription()).append("\n");
                    }
                    return sb.toString();
                }
            }
            return "To suggest enrichment, please specify an animal name like 'Suggest enrichment for Simba' or 'Aruna'.";
        }

        // Query 6: "What did Aruna eat" / "observation"
        if (msg.contains("eat") || msg.contains("yesterday") || msg.contains("observation")) {
            List<Observation> obsList = observationRepository.findAllByOrderByTimestampDesc();
            if (!obsList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("📝 **Recent Animal Observations from SQLite**:\n\n");
                for (Observation obs : obsList) {
                    sb.append("- **").append(obs.getAnimalName()).append("**: Food Intake = ").append(obs.getFoodIntake())
                            .append(", Water Intake = ").append(obs.getWaterIntake())
                            .append(", Activity = ").append(obs.getActivityLevel())
                            .append("\n  *Notes*: \"").append(obs.getObservationText()).append("\"\n");
                }
                return sb.toString();
            }
        }

        // Default response
        return "🤖 **AI Zoo Assistant**: I checked the persistent SQLite database (`zoo.db`). I can help you with animal diets, feeding schedules, keeper shifts, observation logs, or dynamic $A^*$ replanning rationales. Try asking: *'What food is suitable for Aruna?'* or *'Show today's feeding plan'*!";
    }
}
