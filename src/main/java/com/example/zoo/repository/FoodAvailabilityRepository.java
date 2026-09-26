package com.example.zoo.repository;

import com.example.zoo.entity.FoodAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodAvailabilityRepository extends JpaRepository<FoodAvailability, Long> {
    Optional<FoodAvailability> findByFoodName(String foodName);
}
