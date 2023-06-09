package com.java.bandify.persistance.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "user_id")
     private Integer id;

     @Column(name = "username")
     private String username;

     @Column(name = "password")
     private String password;

     @Column(name = "first_name")
     private String firstName;

     @Column(name = "last_name")
     private String lastName;

     @Column(name = "user_type")
     private String userType;

     @Column(name = "date_of_birth")
     private LocalDate dateOfBirth;

     @ManyToMany
     @JoinTable(
         name = "user_authority",
         joinColumns = @JoinColumn(name = "user_id"),
         inverseJoinColumns = @JoinColumn(name = "authority_id"))
     private List<AuthorityEntity> authorities;


     @ManyToOne
     @JoinColumn(name = "postal_code")
     private CityEntity city;

     public UserEntity(String username, String password, String firstName, String lastName, String userType,
         LocalDate dateOfBirth,
         List<AuthorityEntity> authorities, CityEntity city) {
          this.username = username;
          this.password = password;
          this.firstName = firstName;
          this.userType = userType;
          this.lastName = lastName;
          this.dateOfBirth = dateOfBirth;
          this.authorities = authorities;
          this.city = city;
     }

}
