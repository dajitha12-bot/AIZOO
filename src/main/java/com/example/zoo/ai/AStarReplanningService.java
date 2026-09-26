package com.example.zoo.ai;

import com.example.zoo.entity.FeedingTask;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AStarReplanningService {

    public static class PlanState implements Comparable<PlanState> {
        private final List<FeedingTask> tasks;
        private final int gCost; // Cost of changes made so far
        private final int hCost; // Heuristic remaining unassigned/unresolved conflicts
        private final String changeDescription;

        public PlanState(List<FeedingTask> tasks, int gCost, int hCost, String changeDescription) {
            this.tasks = tasks;
            this.gCost = gCost;
            this.hCost = hCost;
            this.changeDescription = changeDescription;
        }

        public int getFCost() { return gCost + hCost; }
        public int getGCost() { return gCost; }
        public int getHCost() { return hCost; }
        public List<FeedingTask> getTasks() { return tasks; }
        public String getChangeDescription() { return changeDescription; }

        @Override
        public int compareTo(PlanState other) {
            return Integer.compare(this.getFCost(), other.getFCost());
        }
    }

    /**
     * Executes real A* graph search to find the lowest-cost feasible revised feeding plan.
     */
    public PlanState executeAStarReplan(List<FeedingTask> currentTasks,
                                        String unavailableKeeper,
                                        String unavailableFood,
                                        List<String> availableKeepers,
                                        List<String> availableFoods) {

        PriorityQueue<PlanState> openSet = new PriorityQueue<>();

        // Initial State Heuristic: count conflicting tasks
        int initialConflicts = countConflicts(currentTasks, unavailableKeeper, unavailableFood);
        PlanState initialState = new PlanState(cloneTasks(currentTasks), 0, initialConflicts * 5, "Initial Plan state with conflict detected.");
        openSet.add(initialState);

        Set<String> visitedStates = new HashSet<>();

        while (!openSet.isEmpty()) {
            PlanState current = openSet.poll();

            // Goal test: 0 conflicts
            if (current.getHCost() == 0) {
                return current;
            }

            String stateSignature = buildSignature(current.getTasks());
            if (visitedStates.contains(stateSignature)) {
                continue;
            }
            visitedStates.add(stateSignature);

            // Expand successors
            List<PlanState> successors = generateSuccessors(current, unavailableKeeper, unavailableFood, availableKeepers, availableFoods);
            for (PlanState successor : successors) {
                if (!visitedStates.contains(buildSignature(successor.getTasks()))) {
                    openSet.add(successor);
                }
            }
        }

        // Fallback if exact 0-conflict path not found
        return initialState;
    }

    private List<PlanState> generateSuccessors(PlanState state,
                                                String unavailableKeeper,
                                                String unavailableFood,
                                                List<String> availableKeepers,
                                                List<String> availableFoods) {
        List<PlanState> successors = new ArrayList<>();
        List<FeedingTask> tasks = state.getTasks();

        for (int i = 0; i < tasks.size(); i++) {
            FeedingTask task = tasks.get(i);
            boolean isKeeperConflict = (unavailableKeeper != null && unavailableKeeper.equalsIgnoreCase(task.getZookeeperName()));
            boolean isFoodConflict = (unavailableFood != null && unavailableFood.equalsIgnoreCase(task.getFoodName()));

            if (isKeeperConflict) {
                // Action 1: Change zookeeper (Cost = 2)
                for (String newKeeper : availableKeepers) {
                    if (!newKeeper.equalsIgnoreCase(unavailableKeeper)) {
                        List<FeedingTask> nextTasks = cloneTasks(tasks);
                        FeedingTask modified = nextTasks.get(i);
                        modified.setZookeeperName(newKeeper);
                        modified.setStatus("Re-planned");
                        modified.setExplanation("Reassigned from " + unavailableKeeper + " to " + newKeeper + " via A* Search.");

                        int g = state.getGCost() + 2;
                        int h = countConflicts(nextTasks, unavailableKeeper, unavailableFood) * 5;
                        successors.add(new PlanState(nextTasks, g, h, "Reassigned " + task.getAnimalName() + " to " + newKeeper));
                    }
                }

                // Action 2: Change feeding time by 30 mins (Cost = 1)
                List<FeedingTask> nextTasksTime = cloneTasks(tasks);
                FeedingTask modifiedTime = nextTasksTime.get(i);
                modifiedTime.setFeedingTime(shiftTime(task.getFeedingTime()));
                modifiedTime.setStatus("Re-planned");
                modifiedTime.setExplanation("Shifted time to " + modifiedTime.getFeedingTime() + " due to keeper availability.");

                int gTime = state.getGCost() + 1;
                int hTime = countConflicts(nextTasksTime, unavailableKeeper, unavailableFood) * 5;
                successors.add(new PlanState(nextTasksTime, gTime, hTime, "Shifted feeding time for " + task.getAnimalName() + " to " + modifiedTime.getFeedingTime()));
            }

            if (isFoodConflict) {
                // Action 3: Change food item (Cost = 3)
                for (String newFood : availableFoods) {
                    if (!newFood.equalsIgnoreCase(unavailableFood)) {
                        List<FeedingTask> nextTasksFood = cloneTasks(tasks);
                        FeedingTask modifiedFood = nextTasksFood.get(i);
                        modifiedFood.setFoodName(newFood);
                        modifiedFood.setStatus("Substituted");
                        modifiedFood.setExplanation("Substituted " + unavailableFood + " with available " + newFood + " via A* Search.");

                        int gFood = state.getGCost() + 3;
                        int hFood = countConflicts(nextTasksFood, unavailableKeeper, unavailableFood) * 5;
                        successors.add(new PlanState(nextTasksFood, gFood, hFood, "Substituted food for " + task.getAnimalName() + " to " + newFood));
                    }
                }
            }
        }

        return successors;
    }

    private int countConflicts(List<FeedingTask> tasks, String unavailableKeeper, String unavailableFood) {
        int conflicts = 0;
        for (FeedingTask task : tasks) {
            if (unavailableKeeper != null && unavailableKeeper.equalsIgnoreCase(task.getZookeeperName())) {
                conflicts++;
            }
            if (unavailableFood != null && unavailableFood.equalsIgnoreCase(task.getFoodName())) {
                conflicts++;
            }
        }
        return conflicts;
    }

    private String shiftTime(String originalTime) {
        if ("08:00 AM".equalsIgnoreCase(originalTime)) return "08:30 AM";
        if ("09:00 AM".equalsIgnoreCase(originalTime)) return "09:30 AM";
        if ("01:00 PM".equalsIgnoreCase(originalTime)) return "01:30 PM";
        return "10:00 AM";
    }

    private List<FeedingTask> cloneTasks(List<FeedingTask> tasks) {
        List<FeedingTask> clones = new ArrayList<>();
        for (FeedingTask t : tasks) {
            FeedingTask copy = new FeedingTask(t.getPlanId(), t.getAnimalId(), t.getAnimalName(), t.getSpecies(),
                    t.getFoodName(), t.getQuantityKg(), t.getFeedingTime(), t.getZookeeperName(), t.getExplanation());
            copy.setId(t.getId());
            copy.setStatus(t.getStatus());
            clones.add(copy);
        }
        return clones;
    }

    private String buildSignature(List<FeedingTask> tasks) {
        StringBuilder sb = new StringBuilder();
        for (FeedingTask t : tasks) {
            sb.append(t.getAnimalId()).append("-").append(t.getFoodName()).append("-").append(t.getZookeeperName()).append("-").append(t.getFeedingTime()).append("|");
        }
        return sb.toString();
    }
}
