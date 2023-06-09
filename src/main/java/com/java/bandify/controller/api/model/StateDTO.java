package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.StateEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateDTO {

     private Integer id;
     private String name;
     private Integer country_id;

     public static StateDTO from(StateEntity stateEntity) {
          return StateDTO.builder()
              .id(stateEntity.getId())
              .name(stateEntity.getName())
              .country_id(stateEntity.getCountry().getId())
              .build();
     }
}
