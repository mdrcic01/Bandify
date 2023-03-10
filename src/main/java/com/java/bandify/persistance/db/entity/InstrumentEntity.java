package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "instrument")
@Data

@RequiredArgsConstructor
public class InstrumentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "instrument_id")
  private Integer id;

  @Column(name = "instrument_name", nullable = false)
  private String name;

  @ManyToMany(mappedBy = "instruments")
  private List<MusicianEntity> musicians;

  @ManyToMany(mappedBy = "instruments")
  private List<BandEntity> bands;
}
