package com.example.zoo.repository;

import com.example.zoo.entity.PlannerConflict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannerConflictRepository extends JpaRepository<PlannerConflict, Long> {
    List<PlannerConflict> findByIsResolvedFalse();
    long countByIsResolvedFalse();
}
