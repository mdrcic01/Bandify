package com.java.bandify.persistance.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "band")
@Data

@RequiredArgsConstructor
public class BandEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "band_id")
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

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "band_instrument",
      joinColumns = { @JoinColumn(name = "band_id") },
      inverseJoinColumns = { @JoinColumn(name = "instrument_id")}
  )
  private List<InstrumentEntity> instruments;

  public String getGenreName() {
    return genre != null ? genre.getGenre() : null;
  }

  public String getCurrencyCode() {
    return currency != null ? currency.getCode() : null;
  }
}
