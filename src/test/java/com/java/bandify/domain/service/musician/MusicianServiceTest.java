package com.java.bandify.domain.service.musician;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.MusicianDTO;
import com.java.bandify.domain.service.instrument.InstrumentService;
import com.java.bandify.domain.service.user.UserService;
import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.entity.StateEntity;
import com.java.bandify.persistance.db.entity.InstrumentEntity;
import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.CityEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.MusicianRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MusicianServiceTest {

  @InjectMocks
  private MusicianService musicianService;
  @Mock
  private MusicianRepository musicianRepository;
  @Mock
  private InstrumentService instrumentService;
  @Mock
  private UserService userProfileService;
  @Captor
  private ArgumentCaptor<MusicianEntity> musicianEntityArgumentCaptor;


  @Test
  public void getMusician_should_returnMusicianDTO_when_requestedMusicianExist() {
    MusicianEntity musician = buildMusicianEntity(1,
        buildUserProfileEntity("Marko", "Markic", 1, "mmarkic", "strongPass")
    );
    when(musicianRepository.findById(anyInt())).thenReturn(Optional.of(musician));

    //ACTION
    MusicianDTO musicianDTO = musicianService.getMusician(1);

    assertThat(musicianDTO.getUserId()).isEqualTo(musician.getUserProfileId());
    assertThat(musicianDTO.getInstrumentIds()).isEqualTo(mapToInstrumentIdList(musician));
    assertThat(musicianDTO.getBandId()).isEqualTo(musician.getBandId());
  }

  @Test
  public void getMusician_should_throwException_when_requestedMusicianDoesNotExist() {
    when(musicianRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> musicianService.getMusician(1));
  }

  @Test
  public void getAllMusicians_should_returnPopulatedList_when_anyMusiciansAreAvailable() {
    List<MusicianEntity> musicianEntities = buildMusicianEntityList();
    when(musicianRepository.findAll()).thenReturn(musicianEntities);

    //ACTION
    List<MusicianDTO> musicianDTOs = musicianService.getAllMusicians();

    assertThat(musicianDTOs.size()).isEqualTo(musicianEntities.size());
    assertThat(musicianDTOs.get(0).getUserId()).isEqualTo(musicianEntities.get(0).getUserProfileId());
    assertThat(musicianDTOs.get(1).getInstrumentIds()).isEqualTo(mapToInstrumentIdList(musicianEntities.get(1)));
    assertThat(musicianDTOs.get(2).getBandId()).isEqualTo(musicianEntities.get(2).getBandId());

  }

  @Test
  public void getAllMusicians_should_throwException_when_noMusiciansAreAvailable() {
    when(musicianRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> musicianService.getAllMusicians());
  }

  @Test
  public void addOrEditMusician_should_addNewMusician_when_givenIdIsNull() {
    MusicianDTO musicianDTO = buildMusicianDTO();
    MusicianEntity musicianEntity = buildMusicianEntity(1, buildUserProfileEntity("test", "test", 1, "ttest", "ttest"));
    when(musicianRepository.save(musicianEntityArgumentCaptor.capture())).thenReturn(musicianEntity);

    //ACTION
    MusicianEntity musician = musicianService.addOrEditMusician(musicianDTO, null);

    verify(musicianRepository, times(1)).save(musicianEntityArgumentCaptor.capture());
    assertThat(musician).isEqualTo(musicianEntity);
  }

  @Test
  public void addOrEditMusician_should_editExistingMusician_when_givenIdIsNotNull() {
    MusicianDTO musicianDTO = buildMusicianDTO();
    MusicianEntity musicianEntity = buildMusicianEntity(1, buildUserProfileEntity("test", "test", 1, "ttest", "ttest"));
    when(musicianRepository.save(musicianEntityArgumentCaptor.capture())).thenReturn(musicianEntity);

    //ACTION
    MusicianEntity musician = musicianService.addOrEditMusician(musicianDTO, 1);

    verify(musicianRepository, times(1)).save(musicianEntityArgumentCaptor.capture());
    assertThat(musician).isEqualTo(musicianEntity);
  }

  private MusicianDTO buildMusicianDTO() {
    return MusicianDTO.builder()
        .userId(1)
        .bandId(1)
        .instrumentIds(List.of(1,2,3))
        .build();
  }

  private List<MusicianEntity> buildMusicianEntityList() {
    return List.of(
        buildMusicianEntity(1, buildUserProfileEntity("Marko", "Markic", 1, "mmarkic", "strongPass")),
        buildMusicianEntity(2, buildUserProfileEntity("Ivan", "Ivic", 2, "iivic", "strongPass")),
        buildMusicianEntity(3, buildUserProfileEntity("Marin", "Marinic", 3, "mmarinic", "strongPass"))
    );
  }

  private MusicianEntity buildMusicianEntity(Integer id, UserEntity userProfile) {
    return MusicianEntity.builder()
        .id(id)
        .userProfile(userProfile)
        .instruments(buildInstrumentEntityList())
        .build();
  }

  private List<InstrumentEntity> buildInstrumentEntityList() {
    return List.of(
        buildInstrumentEntity(1, "Instrument1"),
        buildInstrumentEntity(2, "Instrument2"),
        buildInstrumentEntity(3, "Instrument3")
    );
  }

  private InstrumentEntity buildInstrumentEntity(Integer id, String name) {
    return InstrumentEntity.builder()
        .id(id)
        .name(name)
        .build();
  }

  private UserEntity buildUserProfileEntity(String firstName, String lastName, Integer id, String username, String password) {
    return UserEntity.builder()
        .id(id)
        .firstName(firstName)
        .lastName(lastName)
        .city(buildCityEntity())
        .dateOfBirth(LocalDateTime.parse("2022-12-12T13:22:22"))
        .username(username)
        .password(password)
        .build();
  }

  private CityEntity buildCityEntity() {
    return CityEntity.builder()
        .name("Town")
        .postalCode(12356)
        .state(buildStateEntity())
        .build();
  }

  private StateEntity buildStateEntity() {
    return StateEntity.builder()
        .id(1)
        .country(buildCountryEntity())
        .name("County")
        .build();
  }

  private CountryEntity buildCountryEntity() {
    return CountryEntity.builder()
        .id(1)
        .name("Country")
        .build();
  }

  private List<Integer> mapToInstrumentIdList(MusicianEntity musician) {
    return musician.getInstruments().stream().map(InstrumentEntity::getId).toList();
  }
}
