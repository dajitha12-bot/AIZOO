package com.example.zoo.service;

import com.example.zoo.entity.*;
import com.example.zoo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DashboardService {

    private final AnimalRepository animalRepository;
    private final FoodRepository foodRepository;
    private final FeedingTaskRepository feedingTaskRepository;
    private final FeedingPlanRepository feedingPlanRepository;
    private final ZookeeperRepository zookeeperRepository;
    private final PlannerConflictRepository plannerConflictRepository;
    private final ObservationRepository observationRepository;
    private final EnrichmentRecommendationRepository enrichmentRecommendationRepository;
    private final AiDecisionRepository aiDecisionRepository;

    public DashboardService(AnimalRepository animalRepository,
                            FoodRepository foodRepository,
                            FeedingTaskRepository feedingTaskRepository,
                            FeedingPlanRepository feedingPlanRepository,
                            ZookeeperRepository zookeeperRepository,
                            PlannerConflictRepository plannerConflictRepository,
                            ObservationRepository observationRepository,
                            EnrichmentRecommendationRepository enrichmentRecommendationRepository,
                            AiDecisionRepository aiDecisionRepository) {
        this.animalRepository = animalRepository;
        this.foodRepository = foodRepository;
        this.feedingTaskRepository = feedingTaskRepository;
        this.feedingPlanRepository = feedingPlanRepository;
        this.zookeeperRepository = zookeeperRepository;
        this.plannerConflictRepository = plannerConflictRepository;
        this.observationRepository = observationRepository;
        this.enrichmentRecommendationRepository = enrichmentRecommendationRepository;
        this.aiDecisionRepository = aiDecisionRepository;
    }

    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();

        // 8 Cards Data directly calculated from SQLite
        long totalAnimals = animalRepository.count();
        long availableFoodsToday = foodRepository.findAll().stream().filter(f -> Boolean.TRUE.equals(f.getIsAvailable()) && f.getRemainingQuantity() > 0).count();

        Optional<FeedingPlan> latestPlan = feedingPlanRepository.findTopByOrderByGeneratedTimeDesc();
        long todayFeedingTasks = 0;
        List<FeedingTask> tasksList = List.of();
        if (latestPlan.isPresent()) {
            tasksList = feedingTaskRepository.findByPlanId(latestPlan.get().getId());
            todayFeedingTasks = tasksList.size();
        }

        long availableKeepers = zookeeperRepository.findAll().stream().filter(z -> Boolean.TRUE.equals(z.getIsAvailable())).count();
        long foodConflicts = plannerConflictRepository.findAll().stream().filter(c -> !c.getIsResolved() && "FOOD_SHORTAGE".equalsIgnoreCase(c.getConflictType())).count();
        long scheduleConflicts = plannerConflictRepository.findAll().stream().filter(c -> !c.getIsResolved() && "ZOOKEEPER_OVERLAP".equalsIgnoreCase(c.getConflictType())).count();
        long pendingObservations = observationRepository.findAll().stream().filter(o -> !"Normal".equalsIgnoreCase(o.getAiPatternDetected())).count();
        long aiRecommendations = enrichmentRecommendationRepository.count() + aiDecisionRepository.count();

        data.put("totalAnimals", totalAnimals);
        data.put("availableFoodsToday", availableFoodsToday);
        data.put("todayFeedingTasks", todayFeedingTasks);
        data.put("availableKeepers", availableKeepers);
        data.put("foodConflicts", foodConflicts);
        data.put("scheduleConflicts", scheduleConflicts);
        data.put("pendingObservations", pendingObservations);
        data.put("aiRecommendations", aiRecommendations);

        // 6 Dashboard Sections Data
        data.put("todayFeedingPlan", latestPlan.orElse(null));
        data.put("feedingTasks", tasksList);
        data.put("foodList", foodRepository.findAll());
        data.put("aiAlerts", plannerConflictRepository.findByIsResolvedFalse());
        data.put("recentObservations", observationRepository.findAllByOrderByTimestampDesc());
        data.put("enrichmentRecommendations", enrichmentRecommendationRepository.findAll());
        data.put("recentAiDecisions", aiDecisionRepository.findTop10ByOrderByTimestampDesc());

        return data;
    }
}
