package com.example.zoo.controller;

import com.example.zoo.service.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping({"/", "/dashboard"})
    public String showDashboard(HttpSession session, Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        Map<String, Object> dashboardData = dashboardService.getDashboardData();
        model.addAllAttributes(dashboardData);
        model.addAttribute("activePage", "dashboard");

        return "dashboard";
    }
}
