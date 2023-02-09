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
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "county")
@Data

@RequiredArgsConstructor
public class CountyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "county_id")
  private Integer id;

  @Column(name = "county_name", length = 64, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "country_id", nullable = false)
  private CountryEntity country;

  @OneToMany(mappedBy = "county")
  private List<TownEntity> towns;
}
