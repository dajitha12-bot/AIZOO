package com.example.zoo.controller;

import com.example.zoo.dto.ScenarioRequestDto;
import com.example.zoo.dto.ScenarioResultDto;
import com.example.zoo.entity.FeedingPlan;
import com.example.zoo.entity.FeedingTask;
import com.example.zoo.repository.*;
import com.example.zoo.service.ScenarioPlannerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/scenario-planner")
public class ScenarioPlannerController {

    private final ScenarioPlannerService scenarioPlannerService;
    private final FoodRepository foodRepository;
    private final ZookeeperRepository zookeeperRepository;
    private final FeedingPlanRepository feedingPlanRepository;
    private final FeedingTaskRepository feedingTaskRepository;
    private final PlannerConflictRepository plannerConflictRepository;
    private final AnimalRepository animalRepository;

    public ScenarioPlannerController(ScenarioPlannerService scenarioPlannerService,
                                     FoodRepository foodRepository,
                                     ZookeeperRepository zookeeperRepository,
                                     FeedingPlanRepository feedingPlanRepository,
                                     FeedingTaskRepository feedingTaskRepository,
                                     PlannerConflictRepository plannerConflictRepository,
                                     AnimalRepository animalRepository) {
        this.scenarioPlannerService = scenarioPlannerService;
        this.foodRepository = foodRepository;
        this.zookeeperRepository = zookeeperRepository;
        this.feedingPlanRepository = feedingPlanRepository;
        this.feedingTaskRepository = feedingTaskRepository;
        this.plannerConflictRepository = plannerConflictRepository;
        this.animalRepository = animalRepository;
    }

    @GetMapping
    public String showScenarioPlannerPage(HttpSession session, Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        // Current Live Status Metrics
        long availableFoods = foodRepository.findAll().stream().filter(f -> Boolean.TRUE.equals(f.getIsAvailable()) && f.getRemainingQuantity() > 0).count();
        long availableKeepers = zookeeperRepository.findAll().stream().filter(z -> Boolean.TRUE.equals(z.getIsAvailable())).count();

        Optional<FeedingPlan> latestPlan = feedingPlanRepository.findTopByOrderByGeneratedTimeDesc();
        long activeTasksCount = 0;
        List<FeedingTask> liveTasks = List.of();
        if (latestPlan.isPresent()) {
            liveTasks = feedingTaskRepository.findByPlanId(latestPlan.get().getId());
            activeTasksCount = liveTasks.size();
        }

        long activeConflicts = plannerConflictRepository.countByIsResolvedFalse();

        model.addAttribute("availableFoodsCount", availableFoods);
        model.addAttribute("availableKeepersCount", availableKeepers);
        model.addAttribute("livePlan", latestPlan.orElse(null));
        model.addAttribute("activeTasksCount", activeTasksCount);
        model.addAttribute("liveTasks", liveTasks);
        model.addAttribute("activeConflictsCount", activeConflicts);

        model.addAttribute("foods", foodRepository.findAll());
        model.addAttribute("zookeepers", zookeeperRepository.findAll());
        model.addAttribute("animals", animalRepository.findAll());

        // Check for Scenario Result in Session
        ScenarioResultDto scenarioResult = (ScenarioResultDto) session.getAttribute("scenarioResult");
        model.addAttribute("scenarioResult", scenarioResult);

        model.addAttribute("activePage", "scenario-planner");
        model.addAttribute("headerTitle", "AI Scenario Planner");

        return "scenario-planner";
    }

    @PostMapping("/run")
    public String runScenario(@ModelAttribute ScenarioRequestDto req, HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        ScenarioResultDto result = scenarioPlannerService.runSimulation(req);
        session.setAttribute("scenarioResult", result);
        return "redirect:/scenario-planner";
    }

    @PostMapping("/apply")
    public String applyScenario(HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        ScenarioResultDto result = (ScenarioResultDto) session.getAttribute("scenarioResult");
        if (result != null) {
            scenarioPlannerService.applyScenarioToLivePlan(result);
            session.removeAttribute("scenarioResult");
        }
        return "redirect:/planner";
    }

    @PostMapping("/discard")
    public String discardScenario(HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        session.removeAttribute("scenarioResult");
        return "redirect:/scenario-planner";
    }
}
