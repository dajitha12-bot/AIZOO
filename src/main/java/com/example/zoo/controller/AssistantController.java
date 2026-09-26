package com.example.zoo.controller;

import com.example.zoo.ai.ZooAssistantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class AssistantController {

    private final ZooAssistantService zooAssistantService;

    public AssistantController(ZooAssistantService zooAssistantService) {
        this.zooAssistantService = zooAssistantService;
    }

    @GetMapping("/assistant")
    public String showAssistantPage(HttpSession session, Model model) {
        if (session.getAttribute("zookeeper") == null) {
            return "redirect:/login";
        }
        model.addAttribute("activePage", "assistant");
        return "assistant";
    }

    @PostMapping("/api/chat")
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleChatMessage(@RequestBody Map<String, String> request, HttpSession session) {
        if (session.getAttribute("zookeeper") == null) {
            return ResponseEntity.status(401).body(Map.of("response", "Unauthorized session."));
        }

        String userMsg = request.get("message");
        String reply = zooAssistantService.processQuery(userMsg);

        return ResponseEntity.ok(Map.of("response", reply));
    }
}
