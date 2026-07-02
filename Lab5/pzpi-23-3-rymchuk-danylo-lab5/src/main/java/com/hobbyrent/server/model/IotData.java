    package com.hobbyrent.server.model;

    import jakarta.persistence.*;
    import lombok.*;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.web.bind.annotation.*;
    import java.time.LocalDateTime;
    import java.util.List;

    @Entity
    @Table(name = "iot_data")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class IotData {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "equipment_id")
        private Equipment equipment;

        private String sensorType;
        private String sensorValue;
        private LocalDateTime timestamp;
    }
