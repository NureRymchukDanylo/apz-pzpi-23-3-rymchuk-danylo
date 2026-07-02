package com.hobbyrent.server.controller;

import com.hobbyrent.server.model.*;
import com.hobbyrent.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentRepository repository;

    @GetMapping
    public List<Equipment> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Equipment create(@RequestBody Equipment equipment) {
        return repository.save(equipment);
    }

    @PutMapping("/{id}")
    public Equipment update(@PathVariable Long id,
                            @RequestBody Equipment equipment) {
        equipment.setId(id);
        return repository.save(equipment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}


