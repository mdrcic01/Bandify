package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "user_profile")
@Data
@RequiredArgsConstructor
public class UserProfileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_profile_id")
  private Integer id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "date_of_birth", nullable = false)
  private LocalDateTime dateOfBirth;

  @ManyToOne
  @JoinColumn(name = "postal_code")
  private TownEntity town;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @OneToOne(mappedBy = "userProfile")
  private MusicianEntity musician;

  @OneToOne(mappedBy = "userProfile")
  private NonmusicianEntity nonmusician;
}
