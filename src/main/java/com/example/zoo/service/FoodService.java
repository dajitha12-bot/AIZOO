package com.example.zoo.service;

import com.example.zoo.entity.Food;
import com.example.zoo.entity.Zookeeper;
import com.example.zoo.event.ZooDataChangeEvent;
import com.example.zoo.repository.FoodRepository;
import com.example.zoo.repository.ZookeeperRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final ZookeeperRepository zookeeperRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FoodService(FoodRepository foodRepository, ZookeeperRepository zookeeperRepository, ApplicationEventPublisher eventPublisher) {
        this.foodRepository = foodRepository;
        this.zookeeperRepository = zookeeperRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public List<Zookeeper> getAllZookeepers() {
        return zookeeperRepository.findAll();
    }

    public Food updateFoodStock(Long id, Double quantity, Boolean available) {
        Food food = foodRepository.findById(id).orElseThrow();
        food.setTotalQuantity(quantity);
        food.setIsAvailable(available);
        Food saved = foodRepository.save(food);

        StringBuilder sb = new StringBuilder();
        for (Food f : foodRepository.findAll()) {
            sb.append(String.format("%-10s : %.1f %s (%s)\n", f.getName(), f.getRemainingQuantity(), f.getUnit(), f.getIsAvailable() ? "Available" : "Unavailable"));
        }

        eventPublisher.publishEvent(new ZooDataChangeEvent(this, "FOOD_UPDATE", "Updated " + saved.getName(), sb.toString()));
        return saved;
    }

    public Zookeeper updateZookeeperAvailability(Long id, Boolean available) {
        Zookeeper zk = zookeeperRepository.findById(id).orElseThrow();
        zk.setIsAvailable(available);
        Zookeeper saved = zookeeperRepository.save(zk);

        StringBuilder sb = new StringBuilder();
        for (Zookeeper z : zookeeperRepository.findAll()) {
            sb.append(String.format("%-15s : Shift %s - %s (%s)\n", z.getName(), z.getShiftStart(), z.getShiftEnd(), z.getIsAvailable() ? "Available" : "Unavailable"));
        }

        eventPublisher.publishEvent(new ZooDataChangeEvent(this, "FOOD_UPDATE", "Zookeeper Shift Update: " + saved.getName(), sb.toString()));
        return saved;
    }
}
