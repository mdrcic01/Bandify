package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.InstrumentEntity;
import com.java.bandify.persistance.db.entity.MusicianEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicianDTO {
  private UserProfileDTO user;
  private BandDTO band;
  private List<InstrumentDTO> instruments;

  public static MusicianDTO from(MusicianEntity musicianEntity) {
    return MusicianDTO.builder()
        .user(UserProfileDTO.from(musicianEntity.getUserProfile()))
        .band(BandDTO.from(musicianEntity.getBand()))
        .instruments(getInstrumentDtoList(musicianEntity.getInstruments()))
        .build();
  }

  private static List<InstrumentDTO> getInstrumentDtoList(List<InstrumentEntity> instrumentList) {
    return instrumentList.stream().map(InstrumentDTO::from).collect(Collectors.toList());
  }
}
