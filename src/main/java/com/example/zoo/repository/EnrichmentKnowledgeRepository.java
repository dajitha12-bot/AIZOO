package com.example.zoo.repository;

import com.example.zoo.entity.EnrichmentKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrichmentKnowledgeRepository extends JpaRepository<EnrichmentKnowledge, Long> {
    List<EnrichmentKnowledge> findBySpeciesName(String speciesName);
}
