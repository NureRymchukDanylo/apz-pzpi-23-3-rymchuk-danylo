package com.hobbyrent.server.controller;

import com.hobbyrent.server.model.Rental;
import com.hobbyrent.server.model.User;
import com.hobbyrent.server.repository.UserRepository;
import com.hobbyrent.server.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
}
