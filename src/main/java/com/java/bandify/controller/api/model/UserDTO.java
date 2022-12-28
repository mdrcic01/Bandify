package com.java.bandify.controller.api.model;

import com.java.bandify.domain.service.user.model.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {

  private String firstName;
  private String lastName;
  private Integer age;
  private String city;
  private String instrument;

  public UserDTO from(User user) {
    return UserDTO.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .age(user.getAge())
        .city(user.getCity())
        .instrument(user.getInstrument())
        .build();
  }
}
