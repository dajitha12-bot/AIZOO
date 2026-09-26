package com.example.zoo.repository;

import com.example.zoo.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findByAnimalId(String animalId);
    boolean existsByAnimalId(String animalId);
}
