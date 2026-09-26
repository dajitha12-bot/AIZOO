package com.example.zoo.service;

import com.example.zoo.entity.Animal;
import com.example.zoo.event.ZooDataChangeEvent;
import com.example.zoo.repository.AnimalRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AnimalService(AnimalRepository animalRepository, ApplicationEventPublisher eventPublisher) {
        this.animalRepository = animalRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Optional<Animal> getAnimalById(Long id) {
        return animalRepository.findById(id);
    }

    public Optional<Animal> getAnimalByAnimalId(String animalId) {
        return animalRepository.findByAnimalId(animalId);
    }

    public Animal saveAnimal(Animal animal) {
        boolean isNew = (animal.getId() == null);
        if (animal.getImagePath() == null || animal.getImagePath().isEmpty()) {
            animal.setImagePath("/images/animals/" + animal.getSpecies().toLowerCase() + ".png");
        }

        Animal saved = animalRepository.save(animal);

        String details = String.format("Action  : %s\nID      : %s\nName    : %s\nSpecies : %s\nAge     : %d\nImage   : %s",
                isNew ? "ANIMAL_ADDED" : "ANIMAL_UPDATED",
                saved.getAnimalId(), saved.getName(), saved.getSpecies(), saved.getAge(), saved.getImagePath());

        eventPublisher.publishEvent(new ZooDataChangeEvent(this, "ANIMAL_UPDATE", "Animal ID " + saved.getAnimalId(), details));

        return saved;
    }

    public void deleteAnimal(Long id) {
        Optional<Animal> opt = animalRepository.findById(id);
        if (opt.isPresent()) {
            Animal a = opt.get();
            animalRepository.deleteById(id);
            eventPublisher.publishEvent(new ZooDataChangeEvent(this, "ANIMAL_UPDATE", "Animal Removed", "Deleted Animal ID: " + a.getAnimalId() + " (" + a.getName() + ")"));
        }
    }
}
