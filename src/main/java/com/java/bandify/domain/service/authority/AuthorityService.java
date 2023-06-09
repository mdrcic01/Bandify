package com.java.bandify.domain.service.authority;

import com.java.bandify.controller.api.model.AuthorityDTO;
import com.java.bandify.persistance.db.entity.AuthorityEntity;
import com.java.bandify.persistance.db.repository.AuthorityRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

     @Autowired
     private AuthorityRepository authorityRepository;

     public List<AuthorityDTO> getAllAuthorities() throws NoSuchElementException {
          List<AuthorityEntity> authorities = authorityRepository.findAll();

          if (authorities.isEmpty()) {
               throw new NoSuchElementException("No authorities are available");
          }

          return authorities.stream().map(AuthorityDTO::from).collect(Collectors.toList());
     }

}