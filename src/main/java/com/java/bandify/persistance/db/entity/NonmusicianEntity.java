package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "nonmusician")
public class NonmusicianEntity extends UserEntity {

     private String bio;

     public NonmusicianEntity(String username, String password, String firstName, String lastName, String userType,
         LocalDate dateOfBirth, List<AuthorityEntity> authorities, CityEntity city, String bio) {
          super(username, password, firstName, lastName, userType, dateOfBirth, authorities, city);
          this.bio = bio;
     }

     public NonmusicianEntity() {
          super();
     }

     public void setBio(String bio) {
          this.bio = bio;
     }

}
