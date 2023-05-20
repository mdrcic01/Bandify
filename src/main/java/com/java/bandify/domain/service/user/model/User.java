package com.java.bandify.domain.service.user.model;

import com.java.bandify.persistance.db.entity.UserEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private String firstName;
    private String lastName;
    private Integer age;
    private String city;
    private String instrument;


    //TODO tu je bag - hardkodirani value
    public static User from(UserEntity user, String instrument) {
        return User.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .age(25)
            .city(user.getCity().getName())
            .instrument(instrument)
            .build();
    }
}
