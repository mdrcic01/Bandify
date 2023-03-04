
package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.NonmusicianEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NonmusicianDTO {
  private UserProfileDTO user;

  public static NonmusicianDTO from(NonmusicianEntity nonmusicianEntity) {
    return NonmusicianDTO.builder()
        .user(UserProfileDTO.from(nonmusicianEntity.getUserProfile()))
        .build();
  }
}
