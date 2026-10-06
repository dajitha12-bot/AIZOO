package com.example.zoo.repository;

import com.example.zoo.entity.SpeciesFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeciesFoodRepository extends JpaRepository<SpeciesFood, Long> {
    List<SpeciesFood> findBySpeciesName(String speciesName);
    List<SpeciesFood> findBySpeciesNameIgnoreCase(String speciesName);
}
