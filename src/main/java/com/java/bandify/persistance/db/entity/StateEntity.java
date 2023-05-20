package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "state")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class StateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "state_id")
  private Integer id;

  @Column(name = "state_name", length = 64, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "country_id", nullable = false)
  private CountryEntity country;

  @OneToMany(mappedBy = "state")
  private List<CityEntity> towns;
}
