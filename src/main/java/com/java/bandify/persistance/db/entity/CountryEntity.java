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

@Entity(name = "country")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CountryEntity {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "country_id")
     private Integer id;

     @Column(name = "country_name", length = 64, nullable = false)
     private String name;

     @OneToMany(mappedBy = "country")
     @JsonIgnore
     private List<StateEntity> counties;
}
