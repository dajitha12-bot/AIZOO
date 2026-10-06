package com.example.zoo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @GetMapping
    public String showSchedulePage(HttpSession session, Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        model.addAttribute("activePage", "schedule");
        model.addAttribute("headerTitle", "My Schedule");
        model.addAttribute("headerSubtitle", "Your assigned tasks for today");
        return "schedule";
    }
}
