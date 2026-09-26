package com.example.zoo.repository;

import com.example.zoo.entity.CareRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareRuleRepository extends JpaRepository<CareRule, Long> {
}
