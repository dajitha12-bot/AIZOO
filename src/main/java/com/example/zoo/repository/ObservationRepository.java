package com.example.zoo.repository;

import com.example.zoo.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
    List<Observation> findByAnimalIdOrderByTimestampDesc(String animalId);
    List<Observation> findAllByOrderByTimestampDesc();
}
