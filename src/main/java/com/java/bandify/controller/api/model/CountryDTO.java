package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.CountryEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryDTO {

     private Integer id;
     private String name;

     public static CountryDTO from(CountryEntity countryEntity) {
          return CountryDTO.builder()
              .id(countryEntity.getId())
              .name(countryEntity.getName())
              .build();
     }
}
