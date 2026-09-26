package com.example.zoo.planner;

import com.example.zoo.ai.*;
import com.example.zoo.entity.*;
import com.example.zoo.event.ZooDataChangeEvent;
import com.example.zoo.repository.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class FeedingPlanningService {

    private final AnimalRepository animalRepository;
    private final FoodRepository foodRepository;
    private final ZookeeperRepository zookeeperRepository;
    private final FeedingPlanRepository feedingPlanRepository;
    private final FeedingTaskRepository feedingTaskRepository;
    private final PlannerConflictRepository plannerConflictRepository;
    private final AiDecisionRepository aiDecisionRepository;
    private final KnowledgeReasoningService knowledgeReasoningService;
    private final ConstraintPropagationService constraintPropagationService;
    private final FeedingCSPService feedingCSPService;
    private final BacktrackingSolver backtrackingSolver;
    private final UtilityService utilityService;
    private final AStarReplanningService aStarReplanningService;
    private final DecisionExplanationService explanationService;
    private final ApplicationEventPublisher eventPublisher;

    public FeedingPlanningService(AnimalRepository animalRepository,
                                  FoodRepository foodRepository,
                                  ZookeeperRepository zookeeperRepository,
                                  FeedingPlanRepository feedingPlanRepository,
                                  FeedingTaskRepository feedingTaskRepository,
                                  PlannerConflictRepository plannerConflictRepository,
                                  AiDecisionRepository aiDecisionRepository,
                                  KnowledgeReasoningService knowledgeReasoningService,
                                  ConstraintPropagationService constraintPropagationService,
                                  FeedingCSPService feedingCSPService,
                                  BacktrackingSolver backtrackingSolver,
                                  UtilityService utilityService,
                                  AStarReplanningService aStarReplanningService,
                                  DecisionExplanationService explanationService,
                                  ApplicationEventPublisher eventPublisher) {
        this.animalRepository = animalRepository;
        this.foodRepository = foodRepository;
        this.zookeeperRepository = zookeeperRepository;
        this.feedingPlanRepository = feedingPlanRepository;
        this.feedingTaskRepository = feedingTaskRepository;
        this.plannerConflictRepository = plannerConflictRepository;
        this.aiDecisionRepository = aiDecisionRepository;
        this.knowledgeReasoningService = knowledgeReasoningService;
        this.constraintPropagationService = constraintPropagationService;
        this.feedingCSPService = feedingCSPService;
        this.backtrackingSolver = backtrackingSolver;
        this.utilityService = utilityService;
        this.aStarReplanningService = aStarReplanningService;
        this.explanationService = explanationService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public FeedingPlan generateTodayFeedingPlan() {
        List<Animal> animals = animalRepository.findAll();
        List<Food> foods = foodRepository.findAll();
        List<Zookeeper> keepers = zookeeperRepository.findAll();

        Map<String, List<String>> suitableFoodsMap = new HashMap<>();
        Map<String, Food> foodMap = new HashMap<>();

        for (Food f : foods) {
            foodMap.put(f.getName(), f);
        }

        for (Animal animal : animals) {
            List<String> inferredFoods = knowledgeReasoningService.inferSuitableFoods(animal);
            suitableFoodsMap.put(animal.getSpecies(), inferredFoods);
        }

        // Build CSP Variables
        List<FeedingCSPService.CSPVariable> cspVariables = feedingCSPService.buildVariables(animals, suitableFoodsMap, keepers);

        // Solve via CSP Backtracking
        List<FeedingTask> generatedTasks = backtrackingSolver.solve(cspVariables, foodMap);

        // Calculate Utility Score
        double utilityScore = utilityService.calculatePlanUtility(generatedTasks, animals);

        // Save Plan
        FeedingPlan plan = new FeedingPlan(LocalDate.now(), utilityScore, "CSP + Backtracking Search");
        plan = feedingPlanRepository.save(plan);

        for (FeedingTask task : generatedTasks) {
            task.setPlanId(plan.getId());
            feedingTaskRepository.save(task);
        }

        // Record AI Decision
        aiDecisionRepository.save(new AiDecision("PLAN_GENERATION",
                "Generated Today's Feeding Plan",
                "Created feeding schedule for " + generatedTasks.size() + " animals. Utility Score: " + utilityScore));

        // Publish Terminal Event
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-10s %-10s %-10s %-12s\n", "Animal", "Food", "Quantity", "Time", "Zookeeper"));
        for (FeedingTask t : generatedTasks) {
            sb.append(String.format("%-10s %-10s %-10s %-10s %-12s\n",
                    t.getAnimalName(), t.getFoodName(), t.getQuantityKg() + " kg", t.getFeedingTime(), t.getZookeeperName()));
        }

        eventPublisher.publishEvent(new ZooDataChangeEvent(this, "PLAN_GENERATED", "Plan ID #" + plan.getId(), sb.toString()));

        return plan;
    }

    @Transactional
    public FeedingPlan executeAStarReplanning(String unavailableKeeper, String unavailableFood) {
        Optional<FeedingPlan> latestPlanOpt = feedingPlanRepository.findTopByOrderByGeneratedTimeDesc();
        if (latestPlanOpt.isEmpty()) {
            return generateTodayFeedingPlan();
        }

        FeedingPlan currentPlan = latestPlanOpt.get();
        List<FeedingTask> currentTasks = feedingTaskRepository.findByPlanId(currentPlan.getId());

        List<Zookeeper> keepers = zookeeperRepository.findAll();
        List<Food> foods = foodRepository.findAll();

        List<String> availableKeepers = keepers.stream().filter(z -> Boolean.TRUE.equals(z.getIsAvailable())).map(Zookeeper::getName).toList();
        List<String> availableFoods = foods.stream().filter(f -> Boolean.TRUE.equals(f.getIsAvailable()) && f.getRemainingQuantity() > 0).map(Food::getName).toList();

        // Run A* Graph Search
        AStarReplanningService.PlanState bestState = aStarReplanningService.executeAStarReplan(
                currentTasks, unavailableKeeper, unavailableFood, availableKeepers, availableFoods
        );

        // Save Revised Plan
        FeedingPlan revisedPlan = new FeedingPlan(LocalDate.now(), currentPlan.getUtilityScore() - bestState.getGCost(), "A* Graph Search Replanned");
        revisedPlan.setStatus("Re-planned");
        revisedPlan = feedingPlanRepository.save(revisedPlan);

        for (FeedingTask task : bestState.getTasks()) {
            task.setPlanId(revisedPlan.getId());
            task.setId(null); // save new entity
            feedingTaskRepository.save(task);
        }

        // Record AI Decision
        aiDecisionRepository.save(new AiDecision("ASTAR_REPLANNING",
                "Dynamic A* Re-Planning Completed",
                "Reason: " + (unavailableKeeper != null ? "Keeper " + unavailableKeeper + " unavailable. " : "") +
                        (unavailableFood != null ? "Food " + unavailableFood + " stock low. " : "") +
                        "A* f(n) Cost: " + bestState.getFCost() + " (g=" + bestState.getGCost() + ", h=" + bestState.getHCost() + ")"));

        // Terminal Output
        StringBuilder sb = new StringBuilder();
        sb.append("Reason     : ").append(bestState.getChangeDescription()).append("\n");
        sb.append("A* f(n)    : ").append(bestState.getFCost()).append(" (g=").append(bestState.getGCost()).append(", h=").append(bestState.getHCost()).append(")\n");

        eventPublisher.publishEvent(new ZooDataChangeEvent(this, "REPLAN_COMPLETED", "Re-planned Plan ID #" + revisedPlan.getId(), sb.toString()));

        return revisedPlan;
    }
}
