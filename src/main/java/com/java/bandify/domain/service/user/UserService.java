package com.java.bandify.domain.service.user;

import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

     @Autowired
     private UserRepository userRepository;

     public UserEntity fetchUserIfExists(Integer id) {

          return userRepository.findById(id).get();
     }

}
