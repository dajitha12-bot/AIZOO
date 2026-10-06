package com.example.zoo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conflicts")
public class ConflictController {

    @GetMapping
    public String showConflictsPage(HttpSession session, Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }

        model.addAttribute("activePage", "conflicts");
        model.addAttribute("headerTitle", "Conflicts & Re-planning");
        model.addAttribute("headerSubtitle", "View detected conflicts and how AI resolves them");
        return "conflicts";
    }
}
