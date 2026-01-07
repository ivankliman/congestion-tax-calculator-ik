package com.congestion.tax.entity.rules;

import com.congestion.tax.entity.CityEntity;
import com.congestion.tax.model.vehicle.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.Month;
import java.util.List;

/*
 Entity covering question about flexibility of rules. Single charge rule not covered here because it was not clear to me how it should be defined.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "congestion_tax_rules")
public class CongestionTaxRuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @ElementCollection
    private List<DayOfWeek> exemptDaysInAWeek;

    @ElementCollection
    private List<Month> exemptMonths;

    @Column
    private Boolean chargingOnHolidays;

    @Column
    private Integer daysNotChargingBeforeHoliday;

    @Column
    private Integer daysNotChargingAfterHoliday;

    @Column
    private Double maxChargePerDay;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<VehicleType> exemptVehicleTypes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;
}
