package com.java.bandify.controller.api.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

  private String firstName;
  private String lastName;
  private LocalDateTime dateOfBirth;
  private Integer townPostalCode;
  private String username;
  private String password;
}
