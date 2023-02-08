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
import lombok.Data;

@Entity(name = "band")
@Data
public class BandEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "band_name", length = 64, nullable = false)
  private String bandName;

  @Column(name = "price")
  private Integer price;

  @ManyToOne
  @JoinColumn(name = "currency_id")
  private CurrencyEntity currency;

  @ManyToOne
  @JoinColumn(name = "genre_id")
  private GenreEntity genre;

  @OneToMany(mappedBy = "band")
  private List<MusicianEntity> musicians;
}
