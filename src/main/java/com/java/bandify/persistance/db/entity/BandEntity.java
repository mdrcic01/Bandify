package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "band")
public class BandEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "band_name", length = 64, nullable = false)
  private String bandName;

  @Column(name = "price")
  private Integer price;

  @Column(name = "currency_id")
  private Integer currencyId;

  @Column(name = "genre_id")
  private Integer genreId;
}
