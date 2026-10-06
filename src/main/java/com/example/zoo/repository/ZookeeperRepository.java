package com.example.zoo.repository;

import com.example.zoo.entity.Zookeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZookeeperRepository extends JpaRepository<Zookeeper, Long> {
    Optional<Zookeeper> findByName(String name);
    Optional<Zookeeper> findByZookeeperId(String zookeeperId);
    Optional<Zookeeper> findByNameIgnoreCase(String name);
}
