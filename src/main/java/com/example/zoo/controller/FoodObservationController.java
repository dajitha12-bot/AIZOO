package com.example.zoo.controller;

import com.example.zoo.service.AnimalService;
import com.example.zoo.service.FoodService;
import com.example.zoo.service.ObservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/food-observations")
public class FoodObservationController {

    private final FoodService foodService;
    private final ObservationService observationService;
    private final AnimalService animalService;

    public FoodObservationController(FoodService foodService, ObservationService observationService, AnimalService animalService) {
        this.foodService = foodService;
        this.observationService = observationService;
        this.animalService = animalService;
    }

    @GetMapping
    public String showFoodObservationsPage(@RequestParam(required = false, defaultValue = "1") String tab,
                                           HttpSession session,
                                           Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        model.addAttribute("foods", foodService.getAllFoods());
        model.addAttribute("zookeepers", foodService.getAllZookeepers());
        model.addAttribute("observations", observationService.getAllObservations());
        model.addAttribute("animals", animalService.getAllAnimals());
        model.addAttribute("activeTab", tab);
        model.addAttribute("activePage", "food-observations");

        return "food-observations";
    }

    @PostMapping("/update-food")
    public String updateFood(@RequestParam Long id,
                             @RequestParam Double quantity,
                             @RequestParam(defaultValue = "false") Boolean isAvailable,
                             HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        foodService.updateFoodStock(id, quantity, isAvailable);
        return "redirect:/food-observations?tab=1";
    }

    @PostMapping("/update-keeper")
    public String updateKeeper(@RequestParam Long id,
                               @RequestParam(defaultValue = "false") Boolean isAvailable,
                               HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        foodService.updateZookeeperAvailability(id, isAvailable);
        return "redirect:/food-observations?tab=2";
    }

    @PostMapping("/add-observation")
    public String addObservation(@RequestParam String animalId,
                                 @RequestParam String foodIntake,
                                 @RequestParam String waterIntake,
                                 @RequestParam String activityLevel,
                                 @RequestParam String observationText,
                                 HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        observationService.recordObservation(animalId, foodIntake, waterIntake, activityLevel, observationText);
        return "redirect:/food-observations?tab=3";
    }
}
