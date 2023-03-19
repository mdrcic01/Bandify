package com.java.bandify.persistance.db.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "nonmusician")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class NonmusicianEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "nonmusician_id")
  private Integer id;

  @OneToOne
  @JoinColumn(name = "user_profile_id")
  private UserProfileEntity userProfile;

  public Integer getUserProfileId() {
    if(userProfile == null) {
      return null;
    }

    return userProfile.getId();
  }
}
