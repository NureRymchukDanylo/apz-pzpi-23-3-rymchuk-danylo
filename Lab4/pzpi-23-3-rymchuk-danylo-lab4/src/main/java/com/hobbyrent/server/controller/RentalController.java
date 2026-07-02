package com.hobbyrent.server.controller;

import com.hobbyrent.server.model.Rental;
import com.hobbyrent.server.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/start")
    public Rental startRental(@RequestParam Long equipmentId,
                              @RequestParam Long userId,
                              @RequestParam String endIsoDate) {
        LocalDateTime endDate = LocalDateTime.parse(endIsoDate);
        return rentalService.processRental(equipmentId, userId, endDate);
    }
}