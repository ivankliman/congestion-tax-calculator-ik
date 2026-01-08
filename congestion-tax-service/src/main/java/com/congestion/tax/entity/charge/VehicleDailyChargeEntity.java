package com.congestion.tax.entity.charge;

import com.congestion.tax.entity.CityEntity;
import com.congestion.tax.entity.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "vehicle_daily_charges",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_city_vehicle_date",
                        columnNames = {"city_id", "vehicle_id", "charge_date"}
                )
        },
        indexes = {
                @Index(
                        name = "city_vehicle_date_idx",
                        columnList = "city_id, vehicle_id, charge_date"
                )
        })
public class VehicleDailyChargeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @Column
    private LocalDate chargeDate;

    @Column
    private Double dailyChargeAmount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;
}
