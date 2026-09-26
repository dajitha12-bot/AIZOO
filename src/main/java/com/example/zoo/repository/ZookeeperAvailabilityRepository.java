package com.example.zoo.repository;

import com.example.zoo.entity.ZookeeperAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZookeeperAvailabilityRepository extends JpaRepository<ZookeeperAvailability, Long> {
    Optional<ZookeeperAvailability> findByZookeeperName(String zookeeperName);
}
