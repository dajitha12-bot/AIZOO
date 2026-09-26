package com.example.zoo.repository;

import com.example.zoo.entity.AiDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiDecisionRepository extends JpaRepository<AiDecision, Long> {
    List<AiDecision> findTop10ByOrderByTimestampDesc();
}
