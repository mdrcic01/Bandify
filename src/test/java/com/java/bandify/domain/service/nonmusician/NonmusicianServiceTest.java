package com.java.bandify.domain.service.nonnonmusician;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.NonmusicianDTO;
import com.java.bandify.domain.service.instrument.InstrumentService;
import com.java.bandify.domain.service.nonmusician.NonmusicianService;
import com.java.bandify.domain.service.user.UserProfileService;
import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.entity.CountyEntity;
import com.java.bandify.persistance.db.entity.NonmusicianEntity;
import com.java.bandify.persistance.db.entity.TownEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.entity.UserProfileEntity;
import com.java.bandify.persistance.db.repository.NonmusicianRepository;
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
public class NonmusicianServiceTest {

  @InjectMocks
  private NonmusicianService nonmusicianService;
  @Mock
  private NonmusicianRepository nonmusicianRepository;
  @Mock
  private UserProfileService userProfileService;
  @Captor
  private ArgumentCaptor<NonmusicianEntity> nonmusicianEntityArgumentCaptor;


  @Test
  public void getNonmusician_should_returnNonmusicianDTO_when_requestedNonmusicianExist() {
    NonmusicianEntity nonmusician = buildNonmusicianEntity(1,
        buildUserProfileEntity("Marko", "Markic", 1,
            buildUserEntity("mmarkic", "strongPass")
        )
    );
    when(nonmusicianRepository.findById(anyInt())).thenReturn(Optional.of(nonmusician));

    //ACTION
    NonmusicianDTO nonmusicianDTO = nonmusicianService.getNonmusician(1);

    assertThat(nonmusicianDTO.getUserId()).isEqualTo(nonmusician.getUserProfileId());
  }

  @Test
  public void getNonmusician_should_throwException_when_requestedNonmusicianDoesNotExist() {
    when(nonmusicianRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> nonmusicianService.getNonmusician(1));
  }

  @Test
  public void getAllNonmusicians_should_returnPopulatedList_when_anyNonmusiciansAreAvailable() {
    List<NonmusicianEntity> nonmusicianEntities = buildNonmusicianEntityList();
    when(nonmusicianRepository.findAll()).thenReturn(nonmusicianEntities);

    //ACTION
    List<NonmusicianDTO> nonmusicianDTOs = nonmusicianService.getAllNonmusicians();

    assertThat(nonmusicianDTOs.size()).isEqualTo(nonmusicianEntities.size());
    assertThat(nonmusicianDTOs.get(0).getUserId()).isEqualTo(nonmusicianEntities.get(0).getUserProfileId());
    assertThat(nonmusicianDTOs.get(1).getUserId()).isEqualTo(nonmusicianEntities.get(1).getUserProfileId());
    assertThat(nonmusicianDTOs.get(2).getUserId()).isEqualTo(nonmusicianEntities.get(2).getUserProfileId());

  }

  @Test
  public void getAllNonmusicians_should_throwException_when_noNonmusiciansAreAvailable() {
    when(nonmusicianRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> nonmusicianService.getAllNonmusicians());
  }

  @Test
  public void addOrEditNonmusician_should_addNewNonmusician_when_givenIdIsNull() {
    NonmusicianDTO nonmusicianDTO = buildNonmusicianDTO();
    NonmusicianEntity nonmusicianEntity = buildNonmusicianEntity(1, buildUserProfileEntity("test", "test", 1, buildUserEntity("ttest", "ttest")));
    when(nonmusicianRepository.save(nonmusicianEntityArgumentCaptor.capture())).thenReturn(nonmusicianEntity);

    //ACTION
    NonmusicianEntity nonmusician = nonmusicianService.addOrEditNonmusician(nonmusicianDTO, null);

    verify(nonmusicianRepository, times(1)).save(nonmusicianEntityArgumentCaptor.capture());
    assertThat(nonmusician).isEqualTo(nonmusicianEntity);
  }

  @Test
  public void addOrEditNonmusician_should_editExistingNonmusician_when_givenIdIsNotNull() {
    NonmusicianDTO nonmusicianDTO = buildNonmusicianDTO();
    NonmusicianEntity nonmusicianEntity = buildNonmusicianEntity(1, buildUserProfileEntity("test", "test", 1, buildUserEntity("ttest", "ttest")));
    when(nonmusicianRepository.save(nonmusicianEntityArgumentCaptor.capture())).thenReturn(nonmusicianEntity);

    //ACTION
    NonmusicianEntity nonmusician = nonmusicianService.addOrEditNonmusician(nonmusicianDTO, 1);

    verify(nonmusicianRepository, times(1)).save(nonmusicianEntityArgumentCaptor.capture());
    assertThat(nonmusician).isEqualTo(nonmusicianEntity);
  }

  private NonmusicianDTO buildNonmusicianDTO() {
    return NonmusicianDTO.builder()
        .userId(1)
        .build();
  }

  private List<NonmusicianEntity> buildNonmusicianEntityList() {
    return List.of(
        buildNonmusicianEntity(1, buildUserProfileEntity("Marko", "Markic", 1, buildUserEntity("mmarkic", "strongPass"))),
        buildNonmusicianEntity(2, buildUserProfileEntity("Ivan", "Ivic", 2, buildUserEntity("iivic", "strongPass"))),
        buildNonmusicianEntity(3, buildUserProfileEntity("Marin", "Marinic", 3, buildUserEntity("mmarinic", "strongPass")))
    );
  }

  private NonmusicianEntity buildNonmusicianEntity(Integer id, UserProfileEntity userProfile) {
    return NonmusicianEntity.builder()
        .id(id)
        .userProfile(userProfile)
        .build();
  }

  private UserProfileEntity buildUserProfileEntity(String firstName, String lastName, Integer id, UserEntity user) {
    return UserProfileEntity.builder()
        .id(id)
        .firstName(firstName)
        .lastName(lastName)
        .town(buildTownEntity())
        .dateOfBirth(LocalDateTime.parse("2022-12-12T13:22:22"))
        .user(user)
        .build();
  }

  private UserEntity buildUserEntity(String username, String password) {
    return UserEntity.builder()
        .username(username)
        .password(password)
        .build();
  }

  private TownEntity buildTownEntity() {
    return TownEntity.builder()
        .name("Town")
        .postalCode(12356)
        .county(buildCountyEntity())
        .build();
  }

  private CountyEntity buildCountyEntity() {
    return CountyEntity.builder()
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
}
