package com.java.bandify.persistance.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.RequiredArgsConstructor;

@Entity(name = "currency")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CurrencyEntity {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "currency_id")
     private Integer id;

     @Column(name = "currency_code")
     private String code;

     @OneToMany(mappedBy = "currency")
     @JsonIgnore
     private List<BandEntity> bands;
}
