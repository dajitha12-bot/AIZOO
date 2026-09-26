package com.example.zoo.repository;

import com.example.zoo.entity.FeedingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FeedingPlanRepository extends JpaRepository<FeedingPlan, Long> {
    Optional<FeedingPlan> findTopByPlanDateOrderByGeneratedTimeDesc(LocalDate planDate);
    Optional<FeedingPlan> findTopByOrderByGeneratedTimeDesc();
}
