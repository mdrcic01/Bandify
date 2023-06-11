package com.java.bandify.domain.service.musician;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.MusicianDTO;
import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.CurrencyEntity;
import java.time.LocalDate;
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

import com.java.bandify.domain.service.instrument.InstrumentService;
import com.java.bandify.domain.service.user.UserService;
import com.java.bandify.persistance.db.entity.CityEntity;
import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.entity.InstrumentEntity;
import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.StateEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.MusicianRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MusicianServiceTest {

  @InjectMocks
  private MusicianService musicianService;
  @Mock
  private MusicianRepository musicianRepository;
  @Mock
  private UserService userProfileService;


  @Test
  public void getMusician_should_returnMusicianDTO_when_requestedMusicianExist() {
    MusicianEntity musician = buildMusicianEntity(1,
        buildUserProfileEntity("Marko", "Markic", 1, "mmarkic", "strongPass")
    );
    when(musicianRepository.findById(anyInt())).thenReturn(Optional.of(musician));

    //ACTION
    MusicianDTO musicianDTO = musicianService.getMusician(1);

    assertThat(musicianDTO.getUserId()).isEqualTo(musician.getId());
  }

  @Test
  public void getMusician_should_throwException_when_requestedMusicianDoesNotExist() {
    when(musicianRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> musicianService.getMusician(1));
  }

  @Test
  public void getAllMusicians_should_returnPopulatedList_when_anyMusiciansAreAvailable() {
    List<UserEntity> users = List.of(
        buildUserProfileEntity("Marko", "Markic", 1, "mmarkic", "mmarkic"),
        buildUserProfileEntity("Ivan", "Ivanic", 2, "iivanic", "iivanic")
    );
    when(musicianRepository.findAll()).thenReturn(users);

    //ACTION
    List<MusicianDTO> musicianDTOs = musicianService.getAllMusicians();

    assertThat(musicianDTOs.size()).isEqualTo(users.size());
    assertThat(musicianDTOs.get(0).getUserId()).isEqualTo(users.get(0).getId());
    assertThat(musicianDTOs.get(1).getUserId()).isEqualTo(users.get(1).getId());

  }

  @Test
  public void getAllMusicians_should_throwException_when_noMusiciansAreAvailable() {
    when(musicianRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> musicianService.getAllMusicians());
  }

  private MusicianEntity buildMusicianEntity(Integer id, UserEntity userProfile) {
    return new MusicianEntity(userProfile.getUsername(), userProfile.getPassword(), userProfile.getFirstName(),
        userProfile.getLastName(), userProfile.getUserType(), userProfile.getDateOfBirth(), userProfile.getAuthorities(),
        userProfile.getCity(), buildBandEntity(), buildInstrumentEntityList());
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
        .dateOfBirth(LocalDate.parse("2022-12-12"))
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

  private BandEntity buildBandEntity() {
    return BandEntity.builder()
        .id(1)
        .price(100)
        .currency(null)
        .genre(null)
        .bandName("test")
        .build();
  }
}
