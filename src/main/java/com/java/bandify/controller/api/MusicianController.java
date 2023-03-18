package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.BandDTO;
import com.java.bandify.controller.api.model.MusicianDTO;
import com.java.bandify.domain.service.musician.MusicianService;
import com.java.bandify.domain.service.musician.MusicianService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/musician")
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

  @PostMapping("/")
  public ResponseEntity<HttpStatus> addNewMusician(@RequestBody MusicianDTO musicianDTO) {
    musicianService.addOrEditMusician(musicianDTO, null);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{musicianId}")
  public ResponseEntity<HttpStatus> editMusician(@RequestBody MusicianDTO musicianDTO, @PathVariable Integer musicianId) {
    musicianService.addOrEditMusician(musicianDTO, musicianId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
