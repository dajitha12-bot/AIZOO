package com.example.zoo.repository;

import com.example.zoo.entity.SpeciesKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeciesKnowledgeRepository extends JpaRepository<SpeciesKnowledge, Long> {
    Optional<SpeciesKnowledge> findBySpeciesName(String speciesName);
}
