package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.BandDTO;
import com.java.bandify.controller.api.model.UserProfileDTO;
import com.java.bandify.domain.service.band.BandService;
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
@RequestMapping("/band")
public class BandController {

  @Autowired
  private BandService bandService;

  @GetMapping("/{bandId}")
  public ResponseEntity<BandDTO> getUser(@PathVariable Integer bandId) {
    try {
      return ResponseEntity.ok(bandService.getBand(bandId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<BandDTO>> getAllUsers() {
    try {
      return ResponseEntity.ok(bandService.getAllBands());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }
}
