package com.example.zoo.controller;

import com.example.zoo.entity.Animal;
import com.example.zoo.entity.Zookeeper;
import com.example.zoo.repository.AnimalRepository;
import com.example.zoo.repository.FoodAvailabilityRepository;
import com.example.zoo.repository.SpeciesKnowledgeRepository;
import com.example.zoo.repository.ZookeeperRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ZookeeperRepository zookeeperRepository;
    private final AnimalRepository animalRepository;
    private final SpeciesKnowledgeRepository speciesKnowledgeRepository;
    private final FoodAvailabilityRepository foodAvailabilityRepository;

    public AdminController(ZookeeperRepository zookeeperRepository,
                           AnimalRepository animalRepository,
                           SpeciesKnowledgeRepository speciesKnowledgeRepository,
                           FoodAvailabilityRepository foodAvailabilityRepository) {
        this.zookeeperRepository = zookeeperRepository;
        this.animalRepository = animalRepository;
        this.speciesKnowledgeRepository = speciesKnowledgeRepository;
        this.foodAvailabilityRepository = foodAvailabilityRepository;
    }

    private boolean checkAdminSession(HttpSession session) {
        Object userRole = session.getAttribute("userRole");
        Object zookeeper = session.getAttribute("zookeeper");
        return "ADMIN".equals(userRole) || "Admin".equalsIgnoreCase((String) zookeeper);
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "dashboard");
        model.addAttribute("zookeepersCount", zookeeperRepository.count());
        model.addAttribute("animalsCount", animalRepository.count());
        return "admin/dashboard";
    }

    @GetMapping("/zookeepers")
    public String zookeeperManagement(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "zookeepers");
        model.addAttribute("zookeepers", zookeeperRepository.findAll());
        return "admin/zookeepers";
    }

    @PostMapping("/zookeepers/add")
    public String addZookeeper(@ModelAttribute Zookeeper zookeeper, HttpSession session) {
        if (!checkAdminSession(session)) return "redirect:/login";
        
        if (zookeeper.getZookeeperId() == null || zookeeper.getZookeeperId().isEmpty()) {
            long count = zookeeperRepository.count() + 1;
            zookeeper.setZookeeperId(String.format("ZK%03d", count));
        }
        if (zookeeper.getPassword() == null || zookeeper.getPassword().isEmpty()) {
            zookeeper.setPassword("pass123");
        }
        zookeeperRepository.save(zookeeper);
        return "redirect:/admin/zookeepers";
    }

    @GetMapping("/animals")
    public String animalManagement(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "animals");
        model.addAttribute("animals", animalRepository.findAll());
        return "admin/animals";
    }

    @GetMapping("/knowledge-rules")
    public String knowledgeRules(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "knowledge-rules");
        model.addAttribute("speciesKnowledgeList", speciesKnowledgeRepository.findAll());
        return "admin/knowledge-rules";
    }

    @GetMapping("/food-resources")
    public String foodResources(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "food-resources");
        model.addAttribute("foodItems", foodAvailabilityRepository.findAll());
        return "admin/food-resources";
    }

    @GetMapping("/schedule-rules")
    public String scheduleRules(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "schedule-rules");
        return "admin/schedule-rules";
    }

    @GetMapping("/conflicts-monitoring")
    public String conflictsMonitoring(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "conflicts-monitoring");
        return "admin/conflicts-monitoring";
    }

    @GetMapping("/reports-analytics")
    public String reportsAnalytics(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "reports-analytics");
        return "admin/reports-analytics";
    }

    @GetMapping("/settings")
    public String settings(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "settings");
        return "admin/settings";
    }

    @GetMapping("/ai-insights")
    public String aiInsights(HttpSession session, Model model) {
        if (!checkAdminSession(session)) return "redirect:/login";
        model.addAttribute("activeAdminPage", "ai-insights");
        return "admin/ai-insights";
    }
}
