package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.MusicianDTO;
import com.java.bandify.domain.service.musician.MusicianService;
import com.java.bandify.domain.service.musician.MusicianService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MusicianController {

  @Autowired
  private MusicianService musicianService;

  @GetMapping("/{musicianId}")
  public ResponseEntity<MusicianDTO> getMusician(@PathVariable Integer musicianId) {
    try {
      return ResponseEntity.ok(musicianService.getMusician(musicianId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<MusicianDTO>> getAllMusicians() {
    try {
      return ResponseEntity.ok(musicianService.getAllMusicians());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }
}
