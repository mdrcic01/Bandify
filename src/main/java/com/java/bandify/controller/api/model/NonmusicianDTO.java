
package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.NonmusicianEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NonmusicianDTO {
  private Integer userId;

  public static NonmusicianDTO from(NonmusicianEntity nonmusicianEntity) {
    return NonmusicianDTO.builder()
        .userId(nonmusicianEntity.getId())
        .build();
  }
}
