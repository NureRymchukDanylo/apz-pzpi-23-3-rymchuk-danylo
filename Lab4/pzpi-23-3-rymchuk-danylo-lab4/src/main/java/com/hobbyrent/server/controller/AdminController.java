package com.hobbyrent.server.controller;

import com.hobbyrent.server.model.User;
import com.hobbyrent.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final List<String> systemLogs = new ArrayList<>();

    @GetMapping("/users")
    public List<User> listAllUsers() {
        logAction("Перегляд списку всіх користувачів");
        return userRepository.findAll();
    }

    @PatchMapping("/users/{id}/role")
    public User changeRole(@PathVariable Long id, @RequestParam String newRole) {
        User user = userRepository.findById(id).orElseThrow();
        String oldRole = user.getRole();
        user.setRole(newRole);
        User savedUser = userRepository.save(user);

        logAction("Зміна ролі користувача ID " + id + " з " + oldRole + " на " + newRole);
        return savedUser;
    }

    @DeleteMapping("/users/{id}")
    public String removeUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        logAction("Видалення користувача ID " + id + " адміністратором");
        return "Користувач видалений адміністратором";
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        logAction("Запит системної статистики");
        return Map.of(
                "total_users", userRepository.count(),
                "server_time", LocalDateTime.now(),
                "db_status", "CONNECTED",
                "active_sessions", 1
        );
    }

    @GetMapping("/logs")
    public List<String> getSystemLogs() {
        return systemLogs;
    }

    private void logAction(String action) {
        String entry = LocalDateTime.now() + " | ACTION: " + action;
        systemLogs.add(entry);
        System.out.println(entry);
    }
}