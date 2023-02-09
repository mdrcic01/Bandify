package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "genre")
@Data

@RequiredArgsConstructor
public class GenreEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "genre_id")
  private Integer id;

  @Column(name = "genre_name", nullable = false)
  private String genre;

  @OneToMany(mappedBy = "genre")
  private List<BandEntity> bands;

}
