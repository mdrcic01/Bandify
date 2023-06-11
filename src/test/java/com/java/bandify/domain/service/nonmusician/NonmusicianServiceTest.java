package com.java.bandify.domain.service.nonmusician;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

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

import com.java.bandify.controller.api.model.NonmusicianDTO;
import com.java.bandify.persistance.db.entity.CityEntity;
import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.entity.NonmusicianEntity;
import com.java.bandify.persistance.db.entity.StateEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.NonmusicianRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class NonmusicianServiceTest {

  @InjectMocks
  private NonmusicianService nonmusicianService;
  @Mock
  private NonmusicianRepository nonmusicianRepository;


  @Test
  public void getNonmusician_should_returnNonmusicianDTO_when_requestedNonmusicianExist() {
    NonmusicianEntity nonmusician = buildNonmusicianEntity(1,
        buildUserProfileEntity("Marko", "Markic", 1, "mmarkic", "strongPass")
    );
    when(nonmusicianRepository.findById(anyInt())).thenReturn(Optional.of(nonmusician));

    //ACTION
    NonmusicianDTO nonmusicianDTO = nonmusicianService.getNonmusician(1);

    assertThat(nonmusicianDTO.getUserId()).isEqualTo(nonmusician.getId());
  }

  @Test
  public void getNonmusician_should_throwException_when_requestedNonmusicianDoesNotExist() {
    when(nonmusicianRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> nonmusicianService.getNonmusician(1));
  }

  @Test
  public void getAllNonmusicians_should_returnPopulatedList_when_anyNonmusiciansAreAvailable() {
    List<UserEntity> nonmusicianEntities = buildNonmusicianEntityList();
    when(nonmusicianRepository.findAll()).thenReturn(nonmusicianEntities);

    //ACTION
    List<NonmusicianDTO> nonmusicianDTOs = nonmusicianService.getAllNonmusicians();

    assertThat(nonmusicianDTOs.size()).isEqualTo(nonmusicianEntities.size());
    assertThat(nonmusicianDTOs.get(0).getUserId()).isEqualTo(nonmusicianEntities.get(0).getId());
    assertThat(nonmusicianDTOs.get(1).getUserId()).isEqualTo(nonmusicianEntities.get(1).getId());
    assertThat(nonmusicianDTOs.get(2).getUserId()).isEqualTo(nonmusicianEntities.get(2).getId());

  }

  @Test
  public void getAllNonmusicians_should_throwException_when_noNonmusiciansAreAvailable() {
    when(nonmusicianRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> nonmusicianService.getAllNonmusicians());
  }

  private List<UserEntity> buildNonmusicianEntityList() {
    return List.of(
        buildNonmusicianEntity(1, buildUserProfileEntity("Marko", "Markic", 1, "mmarkic", "mm")),
        buildNonmusicianEntity(2, buildUserProfileEntity("Ivan", "Ivic", 2, "iivic", "ii")),
        buildNonmusicianEntity(3, buildUserProfileEntity("Marin", "Marinic", 3, "mmarinic", "mm"))
    );
  }

  private NonmusicianEntity buildNonmusicianEntity(Integer id, UserEntity userProfile) {
	  NonmusicianEntity entity = new NonmusicianEntity();
	  entity.setId(id);
	  entity.setUsername(userProfile.getUsername());
	  entity.setPassword(null);
	  entity.setFirstName(userProfile.getFirstName());
	  entity.setLastName(userProfile.getLastName());
	  entity.setCity(buildTownEntity());
	  entity.setDateOfBirth(LocalDate.parse("2022-12-12"));
	  
    return entity;
  }

  private UserEntity buildUserProfileEntity(String firstName, String lastName, Integer id, String username, String password) {
    return UserEntity.builder()
        .id(id)
        .username(username)
        .password(password)
        .firstName(firstName)
        .lastName(lastName)
        .city(buildTownEntity())
        .dateOfBirth(LocalDate.parse("2022-12-12"))
        .build();
  }

  private CityEntity buildTownEntity() {
    return CityEntity.builder()
        .name("Town")
        .postalCode(12356)
        .state(buildCountyEntity())
        .build();
  }

  private StateEntity buildCountyEntity() {
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
}
