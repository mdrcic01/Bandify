package com.java.bandify.domain.service.user;

import com.java.bandify.domain.service.user.model.User;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

     private UserRepository userRepository;

     public UserEntity fetchUserIfExists(Integer id) {

          return userRepository.findById(id).get();
     }

}
