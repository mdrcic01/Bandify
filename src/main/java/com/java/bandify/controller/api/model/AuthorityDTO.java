package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.AuthorityEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorityDTO {

     private String name;
     private Integer id;

     public static AuthorityDTO from(AuthorityEntity authorityEntity) {
          return AuthorityDTO.builder()
              .id(authorityEntity.getId())
              .name(authorityEntity.getName())
              .build();
     }
}