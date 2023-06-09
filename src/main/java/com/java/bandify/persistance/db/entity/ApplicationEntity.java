package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "application")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ApplicationEntity {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "application_id")
     private Integer id;

     @OneToOne
     @JoinColumn(name = "band_id")
     private BandEntity band;

     @OneToOne
     @JoinColumn(name = "musician_id")
     private UserEntity musician;

     private String status;

     @Column(name = "applied_on")
     private LocalDateTime appliedOn;
}
