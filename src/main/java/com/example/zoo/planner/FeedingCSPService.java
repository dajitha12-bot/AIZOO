package com.example.zoo.planner;

import com.example.zoo.entity.Animal;
import com.example.zoo.entity.Food;
import com.example.zoo.entity.Zookeeper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FeedingCSPService {

    public static class CSPVariable {
        private final Animal animal;
        private final String slotTime;
        private List<String> foodDomain;
        private List<String> keeperDomain;

        public CSPVariable(Animal animal, String slotTime, List<String> foodDomain, List<String> keeperDomain) {
            this.animal = animal;
            this.slotTime = slotTime;
            this.foodDomain = new ArrayList<>(foodDomain);
            this.keeperDomain = new ArrayList<>(keeperDomain);
        }

        public Animal getAnimal() { return animal; }
        public String getSlotTime() { return slotTime; }
        public List<String> getFoodDomain() { return foodDomain; }
        public List<String> getKeeperDomain() { return keeperDomain; }
    }

    public List<CSPVariable> buildVariables(List<Animal> animals,
                                            Map<String, List<String>> suitableFoodsMap,
                                            List<Zookeeper> availableKeepers) {
        List<CSPVariable> variables = new ArrayList<>();

        List<String> keeperNames = new ArrayList<>();
        for (Zookeeper zk : availableKeepers) {
            if (Boolean.TRUE.equals(zk.getIsAvailable())) {
                keeperNames.add(zk.getName());
            }
        }
        if (keeperNames.isEmpty()) {
            keeperNames.add("Zookeeper A");
        }

        String[] times = {"08:00 AM", "01:00 PM", "05:00 PM"};

        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);
            List<String> suitableFoods = suitableFoodsMap.get(animal.getSpecies());
            if (suitableFoods == null || suitableFoods.isEmpty()) {
                suitableFoods = List.of("Grass", "Hay");
            }

            String slotTime = times[i % times.length];
            variables.add(new CSPVariable(animal, slotTime, suitableFoods, keeperNames));
        }

        return variables;
    }
}
