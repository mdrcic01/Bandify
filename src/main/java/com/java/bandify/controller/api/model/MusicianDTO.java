package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.InstrumentEntity;
import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicianDTO {

     private Integer userId;
     private Integer bandId;
     private List<Integer> instrumentIds;

     public static MusicianDTO from(MusicianEntity musicianEntity) {
          return MusicianDTO.builder()
              .bandId(musicianEntity.getBand().getId())
              .instrumentIds(getInstrumentDtoList(musicianEntity.getInstruments()))
              .build();
     }

     public static MusicianDTO from(UserEntity musicianEntity) {
          return MusicianDTO.builder()
              .userId(musicianEntity.getId())
              .build();
     }

     private static List<Integer> getInstrumentDtoList(List<InstrumentEntity> instrumentList) {
          return instrumentList.stream().map(InstrumentEntity::getId).collect(Collectors.toList());
     }
}
