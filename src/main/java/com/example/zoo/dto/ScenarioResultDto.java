package com.example.zoo.dto;

import com.example.zoo.entity.FeedingTask;

import java.util.List;
import java.util.Map;

public class ScenarioResultDto {

    private String scenarioTitle;
    private String scenarioDescription;

    private List<FeedingTask> currentTasks;
    private List<FeedingTask> scenarioTasks;

    // Impact Analysis Statistics
    private int affectedAnimals;
    private int affectedTasks;
    private int foodSubstitutions;
    private int keeperChanges;
    private int timeChanges;
    private int newConflicts;
    private int resolvedConflicts;
    private boolean isFeasible;
    private String planStability; // HIGH, MEDIUM, LOW

    // Resource Impact Maps
    private Map<String, Double> foodStockBefore;
    private Map<String, Double> foodStockScenario;
    private Map<String, Integer> keeperWorkloadBefore;
    private Map<String, Integer> keeperWorkloadScenario;

    // Alternative Scenario Plans
    private List<AlternativePlanDto> alternativePlans;

    // XAI Rationales
    private List<String> whyRationales;

    // Timeline Log
    private List<TimelineEntryDto> timelineEntries;

    public ScenarioResultDto() {}

    public static class AlternativePlanDto {
        private String planName; // Plan A (Food Substitution), Plan B (Time Change), Plan C (Keeper Reassignment)
        private String strategy;
        private boolean feasible;
        private double utilityScore;
        private int changesCount;
        private int foodChanges;
        private int keeperChanges;
        private int timeChanges;
        private boolean isPreferred;

        public AlternativePlanDto(String planName, String strategy, boolean feasible, double utilityScore, int changesCount, int foodChanges, int keeperChanges, int timeChanges, boolean isPreferred) {
            this.planName = planName;
            this.strategy = strategy;
            this.feasible = feasible;
            this.utilityScore = utilityScore;
            this.changesCount = changesCount;
            this.foodChanges = foodChanges;
            this.keeperChanges = keeperChanges;
            this.timeChanges = timeChanges;
            this.isPreferred = isPreferred;
        }

        public String getPlanName() { return planName; }
        public String getStrategy() { return strategy; }
        public boolean isFeasible() { return feasible; }
        public double getUtilityScore() { return utilityScore; }
        public int getChangesCount() { return changesCount; }
        public int getFoodChanges() { return foodChanges; }
        public int getKeeperChanges() { return keeperChanges; }
        public int getTimeChanges() { return timeChanges; }
        public boolean isPreferred() { return isPreferred; }
    }

    public static class TimelineEntryDto {
        private String time;
        private String stage;
        private String detail;

        public TimelineEntryDto(String time, String stage, String detail) {
            this.time = time;
            this.stage = stage;
            this.detail = detail;
        }

        public String getTime() { return time; }
        public String getStage() { return stage; }
        public String getDetail() { return detail; }
    }

    // Getters and Setters
    public String getScenarioTitle() { return scenarioTitle; }
    public void setScenarioTitle(String scenarioTitle) { this.scenarioTitle = scenarioTitle; }

    public String getScenarioDescription() { return scenarioDescription; }
    public void setScenarioDescription(String scenarioDescription) { this.scenarioDescription = scenarioDescription; }

    public List<FeedingTask> getCurrentTasks() { return currentTasks; }
    public void setCurrentTasks(List<FeedingTask> currentTasks) { this.currentTasks = currentTasks; }

    public List<FeedingTask> getScenarioTasks() { return scenarioTasks; }
    public void setScenarioTasks(List<FeedingTask> scenarioTasks) { this.scenarioTasks = scenarioTasks; }

    public int getAffectedAnimals() { return affectedAnimals; }
    public void setAffectedAnimals(int affectedAnimals) { this.affectedAnimals = affectedAnimals; }

    public int getAffectedTasks() { return affectedTasks; }
    public void setAffectedTasks(int affectedTasks) { this.affectedTasks = affectedTasks; }

    public int getFoodSubstitutions() { return foodSubstitutions; }
    public void setFoodSubstitutions(int foodSubstitutions) { this.foodSubstitutions = foodSubstitutions; }

    public int getKeeperChanges() { return keeperChanges; }
    public void setKeeperChanges(int keeperChanges) { this.keeperChanges = keeperChanges; }

    public int getTimeChanges() { return timeChanges; }
    public void setTimeChanges(int timeChanges) { this.timeChanges = timeChanges; }

    public int getNewConflicts() { return newConflicts; }
    public void setNewConflicts(int newConflicts) { this.newConflicts = newConflicts; }

    public int getResolvedConflicts() { return resolvedConflicts; }
    public void setResolvedConflicts(int resolvedConflicts) { this.resolvedConflicts = resolvedConflicts; }

    public boolean isFeasible() { return isFeasible; }
    public void setFeasible(boolean feasible) { isFeasible = feasible; }

    public String getPlanStability() { return planStability; }
    public void setPlanStability(String planStability) { this.planStability = planStability; }

    public Map<String, Double> getFoodStockBefore() { return foodStockBefore; }
    public void setFoodStockBefore(Map<String, Double> foodStockBefore) { this.foodStockBefore = foodStockBefore; }

    public Map<String, Double> getFoodStockScenario() { return foodStockScenario; }
    public void setFoodStockScenario(Map<String, Double> foodStockScenario) { this.foodStockScenario = foodStockScenario; }

    public Map<String, Integer> getKeeperWorkloadBefore() { return keeperWorkloadBefore; }
    public void setKeeperWorkloadBefore(Map<String, Integer> keeperWorkloadBefore) { this.keeperWorkloadBefore = keeperWorkloadBefore; }

    public Map<String, Integer> getKeeperWorkloadScenario() { return keeperWorkloadScenario; }
    public void setKeeperWorkloadScenario(Map<String, Integer> keeperWorkloadScenario) { this.keeperWorkloadScenario = keeperWorkloadScenario; }

    public List<AlternativePlanDto> getAlternativePlans() { return alternativePlans; }
    public void setAlternativePlans(List<AlternativePlanDto> alternativePlans) { this.alternativePlans = alternativePlans; }

    public List<String> getWhyRationales() { return whyRationales; }
    public void setWhyRationales(List<String> whyRationales) { this.whyRationales = whyRationales; }

    public List<TimelineEntryDto> getTimelineEntries() { return timelineEntries; }
    public void setTimelineEntries(List<TimelineEntryDto> timelineEntries) { this.timelineEntries = timelineEntries; }
}
