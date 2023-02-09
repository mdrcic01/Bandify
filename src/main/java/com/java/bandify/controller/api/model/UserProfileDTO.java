package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.UserProfileEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserProfileDTO {

  private String firstName;
  private String lastName;
  private LocalDateTime dateOfBirth;
  private String town;
  private String username;

  public static UserProfileDTO from(UserProfileEntity userProfile) {
    return UserProfileDTO.builder()
        .firstName(userProfile.getFirstName())
        .lastName(userProfile.getLastName())
        .dateOfBirth(userProfile.getDateOfBirth())
        .town(userProfile.getTown().getName())
        .username(userProfile.getUser().getUsername())
        .build();
  }
}
