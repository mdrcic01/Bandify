package com.java.bandify.domain.service.user;

import com.java.bandify.controller.api.model.UserProfileDTO;
import com.java.bandify.persistance.db.entity.UserProfileEntity;
import com.java.bandify.persistance.db.repository.UserProfileRepository;
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

  public UserProfileDTO getUserProfile(Integer userProfileId) throws NoSuchElementException {
    Optional<UserProfileEntity> userProfile = userProfileRepository.findById(Long.valueOf(userProfileId));;

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
}
