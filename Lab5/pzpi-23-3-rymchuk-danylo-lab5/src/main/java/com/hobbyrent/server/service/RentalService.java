package com.hobbyrent.server.service;

import com.hobbyrent.server.model.*;
import com.hobbyrent.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Rental processRental(Long equipmentId, Long userId, LocalDateTime endDate) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Спорядження не знайдено"));
        if (!"AVAILABLE".equals(equipment.getStatus())) {
            throw new RuntimeException("Це спорядження зараз недоступне для оренди");
        }
        LocalDateTime now = LocalDateTime.now();
        long hours = Duration.between(now, endDate).toHours();
        if (hours <= 0) hours = 1;

        double totalPrice = hours * equipment.getHourlyRate();

        if (hours > 24) {
            totalPrice = totalPrice * 0.85;
        }

        equipment.setStatus("RENTED");
        equipmentRepository.save(equipment);

        User tenant = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));

        Rental rental = new Rental();
        rental.setEquipment(equipment);
        rental.setTenant(tenant);
        rental.setStartDate(now);
        rental.setEndDate(endDate);
        rental.setTotalPrice(totalPrice);
        rental.setStatus("ACTIVE");

        return rentalRepository.save(rental);
    }
}