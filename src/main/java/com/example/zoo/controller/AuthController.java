package com.example.zoo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("zookeeper") != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if ("zookeeper".equalsIgnoreCase(username) || "admin".equalsIgnoreCase(username) || "keeper".equalsIgnoreCase(username)) {
            session.setAttribute("zookeeper", "Zookeeper");
            return "redirect:/dashboard";
        } else if (!username.trim().isEmpty()) {
            session.setAttribute("zookeeper", username);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password. Try 'zookeeper' / 'password'.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
