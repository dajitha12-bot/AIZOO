package com.example.zoo.controller;

import com.example.zoo.entity.Zookeeper;
import com.example.zoo.repository.ZookeeperRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final ZookeeperRepository zookeeperRepository;

    public AuthController(ZookeeperRepository zookeeperRepository) {
        this.zookeeperRepository = zookeeperRepository;
    }

    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("userRole") != null && "ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/admin/dashboard";
        }
        if (session.getAttribute("zookeeper") != null) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if ("ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/admin/dashboard";
        }
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
        String userTrim = username != null ? username.trim() : "";
        String passTrim = password != null ? password.trim() : "";

        // Admin Portal Login
        if ("admin".equalsIgnoreCase(userTrim)) {
            session.setAttribute("userRole", "ADMIN");
            session.setAttribute("zookeeper", "Admin");
            return "redirect:/admin/dashboard";
        }

        // Zookeeper Login by ID or Name
        Optional<Zookeeper> zkOpt = zookeeperRepository.findByZookeeperId(userTrim);
        if (zkOpt.isEmpty()) {
            zkOpt = zookeeperRepository.findByNameIgnoreCase(userTrim);
        }

        if (zkOpt.isPresent()) {
            Zookeeper keeper = zkOpt.get();
            session.setAttribute("userRole", "ZOOKEEPER");
            session.setAttribute("zookeeper", keeper.getName());
            session.setAttribute("zookeeperId", keeper.getZookeeperId());
            return "redirect:/dashboard";
        } else if (!userTrim.isEmpty()) {
            session.setAttribute("userRole", "ZOOKEEPER");
            session.setAttribute("zookeeper", userTrim);
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Invalid credentials. Try Zookeeper ID 'ZK001'-'ZK006' or 'admin'.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
