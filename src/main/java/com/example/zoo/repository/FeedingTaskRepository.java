package com.example.zoo.repository;

import com.example.zoo.entity.FeedingTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedingTaskRepository extends JpaRepository<FeedingTask, Long> {
    List<FeedingTask> findByPlanId(Long planId);
    List<FeedingTask> findByAnimalId(String animalId);
}
