package com.java.bandify.domain.service.user;

import com.java.bandify.controller.api.model.UserDTO;
import com.java.bandify.controller.api.model.UserProfileDTO;
import com.java.bandify.domain.service.town.TownService;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.entity.UserProfileEntity;
import com.java.bandify.persistance.db.repository.UserProfileRepository;
import com.java.bandify.persistance.db.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

  @Autowired
  private UserProfileRepository userProfileRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TownService townService;

  public UserProfileDTO getUserProfile(Integer userProfileId) throws NoSuchElementException {
    Optional<UserProfileEntity> userProfile = userProfileRepository.findById(userProfileId);;

    if (userProfile.isEmpty())
      throw new NoSuchElementException("User profile under id " + userProfileId + " does not exist");

    return UserProfileDTO.from(userProfile.get());
  }

  public List<UserProfileDTO> getAllUserProfiles() throws NoSuchElementException {
    List<UserProfileEntity> userProfiles = userProfileRepository.findAll();

    if (userProfiles.isEmpty())
      throw new NoSuchElementException("There is no user profiles available");

    return userProfiles.stream().map(UserProfileDTO::from).collect(Collectors.toList());
  }

  public UserProfileEntity insertUser(UserDTO userDTO) {
    UserEntity user = userRepository.save(
        UserEntity.builder()
            .username(userDTO.getUsername())
            .password(userDTO.getPassword())
            .build()
        );

    return userProfileRepository.save(
        UserProfileEntity.builder()
            .user(user)
            .dateOfBirth(userDTO.getDateOfBirth())
            .firstName(userDTO.getFirstName())
            .lastName(userDTO.getLastName())
            .town(townService.fetchTownEntityIfExistOrThrow(userDTO.getTownPostalCode()))
            .build()
    );
  }

  public UserProfileEntity fetchUserProfileIfExists(Integer userProfileId) throws NoSuchElementException {
    Optional<UserProfileEntity> userProfile = userProfileRepository.findById(userProfileId);

    if (userProfile.isEmpty()) {
      throw new NoSuchElementException(String.format("User Profile with id %d does not exist in database", userProfileId));
    }

    return userProfile.get();
  }
}
