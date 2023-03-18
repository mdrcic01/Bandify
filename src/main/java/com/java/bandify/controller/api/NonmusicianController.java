package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.NonmusicianDTO;
import com.java.bandify.domain.service.nonmusician.NonmusicianService;
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
@RequestMapping("/nonmusician")
public class NonmusicianController {

  @Autowired
  private NonmusicianService nonmusicianService;

  @GetMapping("/{nonmusicianId}")
  public ResponseEntity<NonmusicianDTO> getNonmusician(@PathVariable Integer nonmusicianId) {
    try {
      return ResponseEntity.ok(nonmusicianService.getNonmusician(nonmusicianId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<NonmusicianDTO>> getAllNonmusicians() {
    try {
      return ResponseEntity.ok(nonmusicianService.getAllNonmusicians());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @PostMapping("/")
  public ResponseEntity<HttpStatus> addNewNonmusician(@RequestBody NonmusicianDTO nonmusicianDTO) {

    nonmusicianService.addOrEditNonmusicians(nonmusicianDTO, null);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{nonmusicianId}")
  public ResponseEntity<HttpStatus> editNonmusician(@RequestBody NonmusicianDTO nonmusicianDTO, @PathVariable Integer nonmusicianId) {

    nonmusicianService.addOrEditNonmusicians(nonmusicianDTO, nonmusicianId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
