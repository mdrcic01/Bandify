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
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "musician")
@Data
@Builder
@RequiredArgsConstructor
public class MusicianEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "musician_id")
  private Integer id;

  @OneToOne
  @JoinColumn(name = "user_profile_id", nullable = false)
  private UserProfileEntity userProfile;

  @ManyToOne
  @JoinColumn(name = "band_id")
  private BandEntity band;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "musician_instrument",
      joinColumns = { @JoinColumn(name = "musician_id")},
      inverseJoinColumns = { @JoinColumn(name = "instrument_id")}
  )
  private List<InstrumentEntity> instruments;

  public Integer getUserProfileId() {
    if(userProfile == null) {
      return null;
    }
    return userProfile.getId();
  }
}
