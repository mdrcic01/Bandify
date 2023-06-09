package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.AuthorityDTO;
import com.java.bandify.domain.service.authority.AuthorityService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authority")
@CrossOrigin("http://localhost:3000")
public class AuthorityController {

     @Autowired
     private AuthorityService authorityService;

     @GetMapping
     public ResponseEntity<List<AuthorityDTO>> getAllAuthorities() {
          try {
               return ResponseEntity.ok(authorityService.getAllAuthorities());
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }
}
