package com.congestion.tax.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.Instant;
import java.time.ZoneId;

/*
  Entity representing city and its timezone. Used for assigning rules to a certain city with certain timezone.
  Potentially here we can add country code, or directly currency in which the congestion tax should be charged
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Audited
@Table(name = "cities")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private ZoneId timezone;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;
}
