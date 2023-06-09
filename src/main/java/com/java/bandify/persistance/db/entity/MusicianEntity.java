package com.java.bandify.persistance.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "musician")
public class MusicianEntity extends UserEntity {

     @ManyToOne
     @JoinColumn(name = "band_id")
     @JsonIgnore
     private BandEntity band;

     @ManyToMany(cascade = CascadeType.ALL)
     @JoinTable(name = "musician_instrument", joinColumns = {@JoinColumn(name = "musician_id")}, inverseJoinColumns = {
         @JoinColumn(name = "instrument_id")})
     private List<InstrumentEntity> instruments;

     public MusicianEntity(String username, String password, String firstName, String lastName, String userType,
         LocalDate dateOfBirth, List<AuthorityEntity> authorities, CityEntity city, BandEntity band,
         List<InstrumentEntity> instruments) {
          super(username, password, firstName, lastName, userType, dateOfBirth, authorities, city);
          this.band = band;
          this.instruments = instruments;
     }

     public MusicianEntity() {
          super();
     }

     public BandEntity getBand() {
          return band;
     }

     public void setBand(BandEntity band) {
          this.band = band;
     }

     public List<InstrumentEntity> getInstruments() {
          return instruments;
     }

     public void setInstruments(List<InstrumentEntity> instruments) {
          this.instruments = instruments;
     }


}
