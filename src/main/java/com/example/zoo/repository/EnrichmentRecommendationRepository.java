package com.example.zoo.repository;

import com.example.zoo.entity.EnrichmentRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnrichmentRecommendationRepository extends JpaRepository<EnrichmentRecommendation, Long> {
    List<EnrichmentRecommendation> findByDate(LocalDate date);
    List<EnrichmentRecommendation> findByAnimalId(String animalId);
}
