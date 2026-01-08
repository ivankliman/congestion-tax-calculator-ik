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
import org.hibernate.envers.Audited;

import java.time.Instant;

@Audited
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "congestion_tax_pending_charges",
        indexes = {
                @Index(name = "congestion_tax_first_pass_idx", columnList = "firstPass")
        })
public class CongestionTaxPendingChargeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @Column
    private Instant firstPass;

    @Column
    private Instant lastPass;

    @Column
    private Double price;

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;
}
