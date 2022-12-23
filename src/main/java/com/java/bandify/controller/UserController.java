package com.java.bandify.controller;

import com.java.bandify.controller.model.UserDTO;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping("/hello")
  public String helloWorld(@RequestParam(name = "name", defaultValue = "World") String name) {
    return String.format("Hello, %s", name);
  }

  @GetMapping("/")
  public ResponseEntity<UserDTO> getUser() {
    return ResponseEntity.ok(UserDTO.builder()
        .firstName("Marko")
        .lastName("Markic")
        .age(31)
        .city("Sisak")
        .instrument("Guitar")
        .build());
  }
}
