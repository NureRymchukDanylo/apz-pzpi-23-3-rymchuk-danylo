package com.hobbyrent.server.controller;

import com.hobbyrent.server.model.IotData;
import com.hobbyrent.server.model.Equipment;
import com.hobbyrent.server.repository.IotDataRepository;
import com.hobbyrent.server.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/iot")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IotController {
    private final IotDataRepository iotRepository;
    private final EquipmentRepository equipmentRepository;

    @PostMapping("/telemetry")
    public IotData receiveData(@RequestBody IotData incomingData) {

        if (incomingData.getEquipment() == null || incomingData.getEquipment().getId() == null) {
            throw new RuntimeException("Equipment ID is required");
        }

        Long equipId = incomingData.getEquipment().getId();
        String sensorType = incomingData.getSensorType();

        List<IotData> existingEntries = iotRepository.findByEquipmentId(equipId);

        Optional<IotData> existingRecord = existingEntries.stream()
                .filter(d -> d.getSensorType().equals(sensorType))
                .findFirst();

        if (existingRecord.isPresent()) {
            IotData recordToUpdate = existingRecord.get();
            recordToUpdate.setSensorValue(incomingData.getSensorValue());
            recordToUpdate.setTimestamp(LocalDateTime.now());
            return iotRepository.save(recordToUpdate);
        } else {
            Equipment equipment = equipmentRepository.findById(equipId)
                    .orElseThrow(() -> new RuntimeException("Equipment not found"));

            incomingData.setEquipment(equipment);
            incomingData.setTimestamp(LocalDateTime.now());
            return iotRepository.save(incomingData);
        }
    }

    @DeleteMapping("/equipment/{equipmentId}")
    @Transactional
    public String deleteByEquipment(@PathVariable Long equipmentId) {
        List<IotData> data = iotRepository.findByEquipmentId(equipmentId);
        if (!data.isEmpty()) {
            iotRepository.deleteAll(data);
            return "All telemetry records for equipment " + equipmentId + " have been deleted.";
        }
        return "No telemetry found for equipment " + equipmentId;
    }
}