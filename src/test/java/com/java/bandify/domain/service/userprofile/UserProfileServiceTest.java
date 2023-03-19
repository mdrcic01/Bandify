package com.java.bandify.domain.service.userprofile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.UserDTO;
import com.java.bandify.controller.api.model.UserProfileDTO;
import com.java.bandify.domain.service.town.TownService;
import com.java.bandify.domain.service.user.UserProfileService;
import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.entity.CountyEntity;
import com.java.bandify.persistance.db.entity.TownEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.entity.UserProfileEntity;
import com.java.bandify.persistance.db.repository.UserProfileRepository;
import com.java.bandify.persistance.db.repository.UserRepository;
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
public class UserProfileServiceTest {

  @InjectMocks
  private UserProfileService userProfileService;
  @Mock
  private UserProfileRepository userProfileRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private TownService townService;
  @Captor
  private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;
  @Captor
  private ArgumentCaptor<UserProfileEntity> userProfileEntityArgumentCaptor;

  @Test
  public void getUserProfile_should_returnUserProfile_when_requiredUserProfileExist() {
    UserProfileEntity userProfileEntity = buildUserProfileEntity(1, "test", "test", buildUserEntity("ttest", "ttest"));
    when(userProfileRepository.findById(anyInt())).thenReturn(Optional.of(userProfileEntity));

    //ACTION
    UserProfileDTO userProfileDTO = userProfileService.getUserProfile(1);

    assertThat(userProfileDTO.getUsername()).isEqualTo(userProfileEntity.getUsername());
    assertThat(userProfileDTO.getDateOfBirth()).isEqualTo(userProfileEntity.getDateOfBirth());
  }

  @Test
  public void getUserProfile_should_throwException_when_userProfileDoesNotExist() {
    when(userProfileRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> userProfileService.getUserProfile(1));
  }

  @Test
  public void getAllUserProfiles_should_returnPopulatedList_when_userProfilesAreAvailable() {
    List<UserProfileEntity> userProfileEntities = buildUserProfileEntityList();
    when(userProfileRepository.findAll()).thenReturn(userProfileEntities);

    //ACTION
    List<UserProfileDTO> userProfileDTOs = userProfileService.getAllUserProfiles();

    assertThat(userProfileDTOs.size()).isEqualTo(userProfileEntities.size());
    assertThat(userProfileDTOs.get(0).getUsername()).isEqualTo(userProfileEntities.get(0).getUsername());
    assertThat(userProfileDTOs.get(0).getDateOfBirth()).isEqualTo(userProfileEntities.get(0).getDateOfBirth());
    assertThat(userProfileDTOs.get(1).getUsername()).isEqualTo(userProfileEntities.get(1).getUsername());
    assertThat(userProfileDTOs.get(1).getFirstName()).isEqualTo(userProfileEntities.get(1).getFirstName());
    assertThat(userProfileDTOs.get(2).getUsername()).isEqualTo(userProfileEntities.get(2).getUsername());
    assertThat(userProfileDTOs.get(2).getTown()).isEqualTo(userProfileEntities.get(2).getTownName());
  }

  @Test
  public void getAllUserProfiles_should_throwException_when_userProfilesAreNotAvailable() {
    when(userProfileRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> userProfileService.getAllUserProfiles());
  }

  @Test
  public void insertUser_should_successfullyInsertUserAndUserProfile_when_initiated() {
    UserDTO userProfileDTO = buildUserProfileDTO();
    UserEntity user = buildUserEntity("ttest", "ttest");
    UserProfileEntity userProfileEntity =
        buildUserProfileEntity(1, "test", "test", user);
    when(userRepository.save(userEntityArgumentCaptor.capture())).thenReturn(user);
    when(userProfileRepository.save(userProfileEntityArgumentCaptor.capture())).thenReturn(userProfileEntity);

    //ACTION
    UserProfileEntity userProfile = userProfileService.insertUser(userProfileDTO);

    assertThat(userProfile).isEqualTo(userProfileEntity);
    verify(userRepository, times(1)).save(userEntityArgumentCaptor.capture());
    verify(userProfileRepository, times(1)).save(userProfileEntityArgumentCaptor.capture());
    verifyNoMoreInteractions(userRepository);
    verifyNoMoreInteractions(userProfileRepository);
  }

  private UserDTO buildUserProfileDTO() {
    return UserDTO.builder()
        .username("ttest")
        .dateOfBirth(LocalDateTime.parse("2001-12-12T10:12:12"))
        .firstName("test")
        .lastName("test")
        .password("ttest")
        .townPostalCode(1)
        .build();
  }

  private List<UserProfileEntity> buildUserProfileEntityList() {
    return List.of(
        buildUserProfileEntity(1, "Marko", "Markic", buildUserEntity("mmarkic", "strongPass")),
        buildUserProfileEntity(2, "Ivan", "Ivic", buildUserEntity("iivic", "strongPass")),
        buildUserProfileEntity(3, "Borna", "Bornic", buildUserEntity("bbornic", "strongPass"))
    );
  }

  private UserProfileEntity buildUserProfileEntity(Integer id, String firstName, String lastName, UserEntity user) {
    return UserProfileEntity.builder()
        .id(id)
        .user(user)
        .dateOfBirth(LocalDateTime.parse("2001-12-12T10:12:12"))
        .firstName(firstName)
        .lastName(lastName)
        .town(buildTownEntity())
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
