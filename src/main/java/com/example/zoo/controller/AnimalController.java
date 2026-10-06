package com.example.zoo.controller;

import com.example.zoo.ai.KnowledgeReasoningService;
import com.example.zoo.ai.KnowledgeRepresentationService;
import com.example.zoo.dto.DietaryAnalysisDto;
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
    private final KnowledgeReasoningService knowledgeReasoningService;

    public AnimalController(AnimalService animalService,
                            KnowledgeRepresentationService knowledgeService,
                            KnowledgeReasoningService knowledgeReasoningService) {
        this.animalService = animalService;
        this.knowledgeService = knowledgeService;
        this.knowledgeReasoningService = knowledgeReasoningService;
    }

    @GetMapping
    public String showAnimalsPage(@RequestParam(required = false, defaultValue = "1") String tab,
                                  HttpSession session,
                                  Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        populateModel(model, tab);
        return "animals";
    }

    @PostMapping("/analyze-diet")
    public String analyzeDiet(@RequestParam String name,
                              @RequestParam String species,
                              @RequestParam(defaultValue = "5") Integer age,
                              HttpSession session,
                              Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        DietaryAnalysisDto analysis = knowledgeReasoningService.analyzeAnimalDietarySafety(name, species, age);

        populateModel(model, "2");
        model.addAttribute("dietaryAnalysis", analysis);
        model.addAttribute("queryName", name);
        model.addAttribute("querySpecies", species);
        model.addAttribute("queryAge", age);

        return "animals";
    }

    @GetMapping("/api/analyze-diet")
    @ResponseBody
    public DietaryAnalysisDto analyzeDietApi(@RequestParam String name,
                                             @RequestParam String species,
                                             @RequestParam(defaultValue = "5") Integer age,
                                             HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return new DietaryAnalysisDto();
        }
        return knowledgeReasoningService.analyzeAnimalDietarySafety(name, species, age);
    }

    @PostMapping("/add")
    public String addAnimal(@ModelAttribute Animal animal,
                            @RequestParam(required = false) List<String> foodPreferencesList,
                            @RequestParam(required = false) String selectedImage,
                            HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        if (foodPreferencesList != null && !foodPreferencesList.isEmpty()) {
            animal.setFoodPreference(String.join(", ", foodPreferencesList));
        }

        if (selectedImage != null && !selectedImage.isEmpty()) {
            if (!selectedImage.startsWith("/")) {
                animal.setImagePath("/images/animals/" + selectedImage);
            } else {
                animal.setImagePath(selectedImage);
            }
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

    private void populateModel(Model model, String tab) {
        model.addAttribute("animals", animalService.getAllAnimals());
        model.addAttribute("speciesKnowledgeList", knowledgeService.getAllSpeciesKnowledge());
        model.addAttribute("activeTab", tab);
        model.addAttribute("activePage", "animals");

        List<String> predefinedImages = Arrays.asList(
                "lion.png", "tiger.png", "elephant.png", "giraffe.png", "zebra.png",
                "leopard.png", "bear.png", "wolf.png", "gorilla.png", "rhino.png",
                "hippo.png", "penguin.png", "flamingo.png", "crocodile.png", "ostrich.png",
                "kangaroo.png", "panda.png", "redpanda.png", "monkey.png", "otter.png"
        );
        model.addAttribute("predefinedImages", predefinedImages);
    }
}
