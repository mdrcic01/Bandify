package com.java.bandify.controller.api;

import com.java.bandify.domain.service.user.UserService;
import com.java.bandify.persistance.db.entity.UserEntity;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {

     @Autowired
     private UserService userService;

     @GetMapping("/{userId}")
     public ResponseEntity<UserEntity> getState(@PathVariable Integer userId) {
          try {
               return ResponseEntity.ok(userService.fetchUserIfExists(userId));
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }

}
