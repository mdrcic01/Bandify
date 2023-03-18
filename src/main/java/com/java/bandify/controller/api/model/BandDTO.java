package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.BandEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BandDTO {
  private String bandName;
  private Integer price;
  private Integer currency;
  private Integer genre;

  public static BandDTO from(BandEntity bandEntity) {
    return BandDTO.builder()
        .bandName(bandEntity.getBandName())
        .price(bandEntity.getPrice())
        .currency(bandEntity.getCurrencyId())
        .genre(bandEntity.getGenreId())
        .build();
  }
}
