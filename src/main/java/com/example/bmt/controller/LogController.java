package com.example.bmt.controller;

import com.example.bmt.util.EncryptionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.io.IOException;

@Controller
public class LogController {
    private final Path logPath = Paths.get("logs/application.log");

    @GetMapping("/logs")
    public String viewLogs(@RequestParam(required=false) String q,
                           @RequestParam(required=false) String date,
                           Model model) throws IOException {
        List<String> lines = new ArrayList<>();
        if (Files.exists(logPath)) {
            List<String> all = Files.readAllLines(logPath);
            int from = Math.max(0, all.size() - 400);
            lines = new ArrayList<>(all.subList(from, all.size()));
        }
        if (date != null && !date.isBlank()) {
            lines.removeIf(l -> !l.contains(date));
        }
        if (q != null && !q.isBlank()) {
            String qq = q.toLowerCase();
            lines.removeIf(l -> !l.toLowerCase().contains(qq));
        }
        Collections.reverse(lines);
        model.addAttribute("logLines", lines);
        return "log/logs";
    }

    @PostMapping("/logs/encrypt")
    @ResponseBody
    public Map<String,String> encrypt(@RequestParam String data) {
        String token = UUID.randomUUID().toString();
        String encrypted = EncryptionUtil.encrypt(data);
        return Map.of("token", token, "encrypted", encrypted);
    }

    @PostMapping("/logs/decrypt")
    @ResponseBody
    public Map<String,String> decrypt(@RequestParam String data) {
        String token = UUID.randomUUID().toString();
        String decrypted = EncryptionUtil.decrypt(data);
        return Map.of("token", token, "decrypted", decrypted);
    }
}
