package com.example.zoo.controller;

import com.example.zoo.ai.ZooPlanningAgent;
import com.example.zoo.entity.FeedingPlan;
import com.example.zoo.entity.FeedingTask;
import com.example.zoo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/planner")
public class PlannerController {

    private final ZooPlanningAgent zooPlanningAgent;
    private final FeedingPlanRepository feedingPlanRepository;
    private final FeedingTaskRepository feedingTaskRepository;
    private final PlannerConflictRepository plannerConflictRepository;
    private final EnrichmentRecommendationRepository enrichmentRecommendationRepository;
    private final ObservationRepository observationRepository;

    public PlannerController(ZooPlanningAgent zooPlanningAgent,
                             FeedingPlanRepository feedingPlanRepository,
                             FeedingTaskRepository feedingTaskRepository,
                             PlannerConflictRepository plannerConflictRepository,
                             EnrichmentRecommendationRepository enrichmentRecommendationRepository,
                             ObservationRepository observationRepository) {
        this.zooPlanningAgent = zooPlanningAgent;
        this.feedingPlanRepository = feedingPlanRepository;
        this.feedingTaskRepository = feedingTaskRepository;
        this.plannerConflictRepository = plannerConflictRepository;
        this.enrichmentRecommendationRepository = enrichmentRecommendationRepository;
        this.observationRepository = observationRepository;
    }

    @GetMapping
    public String showPlannerPage(HttpSession session, Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        Optional<FeedingPlan> latestPlan = feedingPlanRepository.findTopByOrderByGeneratedTimeDesc();
        List<FeedingTask> tasks = List.of();
        if (latestPlan.isPresent()) {
            tasks = feedingTaskRepository.findByPlanId(latestPlan.get().getId());
        }

        model.addAttribute("feedingPlan", latestPlan.orElse(null));
        model.addAttribute("feedingTasks", tasks);
        model.addAttribute("conflicts", plannerConflictRepository.findByIsResolvedFalse());
        model.addAttribute("enrichments", enrichmentRecommendationRepository.findAll());
        model.addAttribute("observations", observationRepository.findAllByOrderByTimestampDesc());
        model.addAttribute("activePage", "planner");

        return "planner";
    }

    @PostMapping("/generate")
    public String generatePlan(HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        zooPlanningAgent.executeCognitiveCycle();
        return "redirect:/planner";
    }

    @PostMapping("/replan")
    public String triggerReplan(@RequestParam(required = false) String unavailableKeeper,
                                @RequestParam(required = false) String unavailableFood,
                                HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        zooPlanningAgent.triggerDynamicReplan(unavailableKeeper, unavailableFood);
        return "redirect:/planner";
    }
}
