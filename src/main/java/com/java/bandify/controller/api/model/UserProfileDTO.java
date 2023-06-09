package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.UserEntity;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserProfileDTO {

     private String firstName;
     private String lastName;
     private LocalDate dateOfBirth;
     private String town;
     private String username;

     public static UserProfileDTO from(UserEntity userProfile) {
          return UserProfileDTO.builder()
              .firstName(userProfile.getFirstName())
              .lastName(userProfile.getLastName())
              .dateOfBirth(userProfile.getDateOfBirth())
              .town(userProfile.getCity().getName())
              .username(userProfile.getUsername())
              .build();
     }
}
