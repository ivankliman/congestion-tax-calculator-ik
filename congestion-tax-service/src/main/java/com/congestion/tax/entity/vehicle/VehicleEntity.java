package com.congestion.tax.entity.vehicle;

import com.congestion.tax.model.vehicle.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Audited
@Table(name = "vehicles",
        indexes = {
                @Index(name = "vehicle_license_plate_idx", columnList = "license_plate")
        })
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String licensePlate;

    @Column
    private VehicleType vehicleType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;
}
