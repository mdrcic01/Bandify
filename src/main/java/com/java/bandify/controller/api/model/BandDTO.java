package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.BandEntity;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BandDTO {

     private String bandName;
     private Integer price;
     private String currency;
     private String genre;
     private Integer userId;
     private List<String> instruments;

     public static BandDTO from(BandEntity bandEntity) {
          return BandDTO.builder()
              .bandName(bandEntity.getBandName())
              .price(bandEntity.getPrice())
              .currency(bandEntity.getCurrency().getCode())
              .genre(bandEntity.getGenre().getGenre())
              .userId(bandEntity.getCreatedBy().getId())
              .build();
     }
}
