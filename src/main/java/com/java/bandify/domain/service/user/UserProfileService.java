package com.java.bandify.domain.service.user;

import com.java.bandify.controller.api.model.UserProfileDTO;
import com.java.bandify.persistance.db.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

  @Autowired
  private UserProfileRepository userProfileRepository;

  public UserProfileDTO getUserProfile(Integer userProfileId) {
    return UserProfileDTO.from(userProfileRepository.getReferenceById(Long.valueOf(userProfileId)));
  }
}
