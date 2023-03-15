package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.BandEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BandDTO {
  private String bandName;
  private Integer price;
  private String currency;
  private String genre;

  public static BandDTO from(BandEntity bandEntity) {
    return BandDTO.builder()
        .bandName(bandEntity.getBandName())
        .price(bandEntity.getPrice())
        .currency(bandEntity.getCurrencyCode())
        .genre(bandEntity.getGenreName())
        .build();
  }
}
