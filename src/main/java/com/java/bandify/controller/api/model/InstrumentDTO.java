package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.InstrumentEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstrumentDTO {

     private Integer id;
     private String instrumentName;

     public static InstrumentDTO from(InstrumentEntity instrumentEntity) {
          return InstrumentDTO.builder()
              .id(instrumentEntity.getId())
              .instrumentName(instrumentEntity.getName())
              .build();
     }
}
