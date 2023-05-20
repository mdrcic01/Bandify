package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.CityEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDTO {
  private Integer postalCode;
  private String name;

  public static CityDTO from(CityEntity cityEntity) {
    return CityDTO.builder()
        .postalCode(cityEntity.getPostalCode())
        .name(cityEntity.getName())
        .build();
  }
}
