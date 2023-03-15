package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.CountyEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountyDTO {
  private Integer id;
  private String name;

  public static CountyDTO from(CountyEntity countyEntity) {
    return CountyDTO.builder()
        .id(countyEntity.getId())
        .name(countyEntity.getName())
        .build();
  }
}
