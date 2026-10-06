package com.example.zoo.repository;

import com.example.zoo.entity.DangerousFoodKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangerousFoodKnowledgeRepository extends JpaRepository<DangerousFoodKnowledge, Long> {
    List<DangerousFoodKnowledge> findBySpeciesNameIgnoreCase(String speciesName);
}
