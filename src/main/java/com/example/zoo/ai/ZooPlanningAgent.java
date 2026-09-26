package com.example.zoo.ai;

import com.example.zoo.entity.FeedingPlan;
import com.example.zoo.planner.FeedingPlanningService;
import org.springframework.stereotype.Service;

@Service
public class ZooPlanningAgent {

    private final FeedingPlanningService feedingPlanningService;

    public ZooPlanningAgent(FeedingPlanningService feedingPlanningService) {
        this.feedingPlanningService = feedingPlanningService;
    }

    /**
     * Executes complete Intelligent Agent Cognitive Loop:
     * PERCEIVE -> REASON -> PLAN -> DECIDE -> ACT
     */
    public FeedingPlan executeCognitiveCycle() {
        System.out.println(">>> [INTELLIGENT AGENT] Executing Cognitive Cycle: PERCEIVE -> REASON -> PLAN -> DECIDE -> ACT");
        return feedingPlanningService.generateTodayFeedingPlan();
    }

    /**
     * Executes Dynamic Monitoring & Re-Planning Cycle:
     * MONITOR -> RE-PLAN (A*)
     */
    public FeedingPlan triggerDynamicReplan(String unavailableKeeper, String unavailableFood) {
        System.out.println(">>> [INTELLIGENT AGENT] Conflict Detected during MONITOR phase. Triggering A* RE-PLAN");
        return feedingPlanningService.executeAStarReplanning(unavailableKeeper, unavailableFood);
    }
}
