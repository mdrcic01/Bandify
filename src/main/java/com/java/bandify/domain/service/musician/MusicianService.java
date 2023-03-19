package com.java.bandify.domain.service.musician;

import com.java.bandify.controller.api.model.MusicianDTO;
import com.java.bandify.controller.api.model.UserProfileDTO;
import com.java.bandify.domain.service.instrument.InstrumentService;
import com.java.bandify.domain.service.user.UserProfileService;
import com.java.bandify.persistance.db.entity.InstrumentEntity;
import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.UserProfileEntity;
import com.java.bandify.persistance.db.repository.MusicianRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicianService {

  @Autowired
  private MusicianRepository musicianRepository;
  @Autowired
  private InstrumentService instrumentService;
  @Autowired
  private UserProfileService userProfileService;

  public MusicianDTO getMusician(Integer musicianId) throws NoSuchElementException {
    Optional<MusicianEntity> musician = musicianRepository.findById(musicianId);

    if(musician.isEmpty())
      throw new NoSuchElementException("There is no musicians under id " + musicianId);

    return MusicianDTO.from(musician.get());
  }

  public List<MusicianDTO> getAllMusicians() throws NoSuchElementException {
    List<MusicianEntity> musicians = musicianRepository.findAll();

    if(musicians.isEmpty())
      throw new NoSuchElementException("No musicians are available");

    return musicians.stream().map(MusicianDTO::from).collect(Collectors.toList());
  }

  public MusicianEntity addOrEditMusician(MusicianDTO musicianDTO, Integer musicianId) {
    List<InstrumentEntity> instruments = instrumentService.getInstrumentsById(musicianDTO.getInstrumentIds());
    UserProfileEntity userProfile = userProfileService.fetchUserProfileIfExists(musicianDTO.getUserId());

    return musicianRepository.save(
          MusicianEntity.builder()
              .id(musicianId)
              .instruments(instruments)
              .userProfile(userProfile)
              .build()
    );
  }
}
