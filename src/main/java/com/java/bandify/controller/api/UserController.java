package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.UserDTO;
import com.java.bandify.controller.api.model.UserProfileDTO;
import com.java.bandify.domain.service.user.UserProfileService;
import com.java.bandify.domain.service.user.model.User;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserProfileService userProfileService;

  @GetMapping("/{userProfileId}")
  public ResponseEntity<UserProfileDTO> getUser(@PathVariable Integer userProfileId) {
    try {
      return ResponseEntity.ok(userProfileService.getUserProfile(userProfileId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<UserProfileDTO>> getAllUsers() {
    try {
      return ResponseEntity.ok(userProfileService.getAllUserProfiles());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @PostMapping("/")
  public ResponseEntity<HttpStatus> insertUser(@RequestBody UserDTO userDTO) {
    userProfileService.insertUser(userDTO);
    return ResponseEntity.ok(HttpStatus.CREATED);
  }
}
