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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "band")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BandEntity {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "band_id")
     private Integer id;

     @Column(name = "band_name", length = 64, nullable = false)
     private String bandName;

     @Column(name = "price")
     private Integer price;

     @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
     @JoinColumn(name = "currency_id")
     private CurrencyEntity currency;

     @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
     @JoinColumn(name = "genre_id")
     private GenreEntity genre;

     @OneToMany(mappedBy = "band", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
     private List<MusicianEntity> musicians;

     @OneToOne
     @JoinColumn(name = "created_by")
     private UserEntity createdBy;

     @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
     @JoinTable(
         name = "band_instrument",
         joinColumns = {@JoinColumn(name = "band_id")},
         inverseJoinColumns = {@JoinColumn(name = "instrument_id")}
     )
     private List<InstrumentEntity> instruments;
}
