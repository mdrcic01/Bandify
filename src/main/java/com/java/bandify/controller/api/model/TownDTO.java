package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.entity.TownEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TownDTO {
  private Integer postalCode;
  private String name;

  public static TownDTO from(TownEntity townEntity) {
    return TownDTO.builder()
        .postalCode(townEntity.getPostalCode())
        .name(townEntity.getName())
        .build();
  }
}
