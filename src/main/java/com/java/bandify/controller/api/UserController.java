package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.UserProfileDTO;
import com.java.bandify.domain.service.user.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserProfileService userProfileService;

  @GetMapping("/hello")
  public String helloWorld(@RequestParam(name = "name", defaultValue = "World") String name) {
    return String.format("Hello, %s", name);
  }

  @GetMapping("/{userProfileId}")
  public ResponseEntity<UserProfileDTO> getUser(@PathVariable Integer userProfileId) {
    return ResponseEntity.ok(userProfileService.getUserProfile(userProfileId));
  }
}
