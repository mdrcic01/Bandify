package com.java.bandify.domain.service.musician;

import com.java.bandify.controller.api.model.MusicianDTO;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.MusicianRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicianService {

     @Autowired
     private MusicianRepository musicianRepository;

     public MusicianDTO getMusician(Integer musicianId) throws NoSuchElementException {
          Optional<UserEntity> musician = musicianRepository.findById(musicianId);

          if (musician.isEmpty()) {
               throw new NoSuchElementException("There is no musicians under id " + musicianId);
          }

          return MusicianDTO.from(musician.get());
     }

     public List<MusicianDTO> getAllMusicians() throws NoSuchElementException {
          List<UserEntity> musicians = musicianRepository.findAll();

          if (musicians.isEmpty()) {
               throw new NoSuchElementException("No musicians are available");
          }

          return musicians.stream().map(MusicianDTO::from).collect(Collectors.toList());
     }
}
