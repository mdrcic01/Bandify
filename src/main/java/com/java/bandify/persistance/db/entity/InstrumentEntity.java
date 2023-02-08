package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.Data;

@Entity(name = "instrument")
@Data
public class InstrumentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "instrument_name", nullable = false)
  private String name;

  @ManyToMany(mappedBy = "instruments")
  private List<MusicianEntity> musicians;
}
