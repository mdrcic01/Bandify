package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NonmusicianDTO {

     private Integer userId;

     public static NonmusicianDTO from(UserEntity nonmusicianEntity) {
          return NonmusicianDTO.builder()
              .userId(nonmusicianEntity.getId())
              .build();
     }
}
