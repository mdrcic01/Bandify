package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "town")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TownEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "postal_code")
  private Integer postalCode;

  @Column(name = "town_name", length = 64, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "county_id", nullable = false)
  private CountyEntity county;
}
