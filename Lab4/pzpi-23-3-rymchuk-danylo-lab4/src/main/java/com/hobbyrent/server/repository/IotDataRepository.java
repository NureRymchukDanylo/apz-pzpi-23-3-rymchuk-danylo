package com.hobbyrent.server.repository;

import com.hobbyrent.server.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IotDataRepository extends JpaRepository<IotData, Long> {
    List<IotData> findByEquipmentId(Long equipmentId);
}
