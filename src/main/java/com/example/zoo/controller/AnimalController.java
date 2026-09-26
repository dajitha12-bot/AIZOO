package com.example.zoo.controller;

import com.example.zoo.ai.KnowledgeRepresentationService;
import com.example.zoo.entity.Animal;
import com.example.zoo.service.AnimalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final KnowledgeRepresentationService knowledgeService;

    public AnimalController(AnimalService animalService, KnowledgeRepresentationService knowledgeService) {
        this.animalService = animalService;
        this.knowledgeService = knowledgeService;
    }

    @GetMapping
    public String showAnimalsPage(@RequestParam(required = false, defaultValue = "1") String tab,
                                  HttpSession session,
                                  Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        model.addAttribute("animals", animalService.getAllAnimals());
        model.addAttribute("speciesKnowledgeList", knowledgeService.getAllSpeciesKnowledge());
        model.addAttribute("activeTab", tab);
        model.addAttribute("activePage", "animals");

        // Predefined image list
        List<String> predefinedImages = Arrays.asList(
                "elephant.png", "lion.png", "giraffe.png", "zebra.png", "tiger.png",
                "deer.png", "monkey.png", "bear.png", "penguin.png", "rhino.png",
                "hippo.png", "parrot.png", "crocodile.png"
        );
        model.addAttribute("predefinedImages", predefinedImages);

        return "animals";
    }

    @PostMapping("/add")
    public String addAnimal(@ModelAttribute Animal animal,
                            @RequestParam(required = false) String selectedImage,
                            HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        if (selectedImage != null && !selectedImage.isEmpty()) {
            animal.setImagePath("/images/animals/" + selectedImage);
        } else if (animal.getImagePath() == null || animal.getImagePath().isEmpty()) {
            animal.setImagePath("/images/animals/" + animal.getSpecies().toLowerCase() + ".png");
        }

        animalService.saveAnimal(animal);
        return "redirect:/animals?tab=1";
    }

    @GetMapping("/delete/{id}")
    public String deleteAnimal(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        animalService.deleteAnimal(id);
        return "redirect:/animals?tab=1";
    }
}
