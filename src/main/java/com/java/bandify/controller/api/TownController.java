package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.TownDTO;
import com.java.bandify.domain.service.town.TownService;
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
@RequestMapping("/town")
public class TownController {

  @Autowired
  private TownService townService;

  @GetMapping("/{townId}")
  public ResponseEntity<TownDTO> getTown(@PathVariable Integer townId) {
    try {
      return ResponseEntity.ok(townService.getTown(townId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<TownDTO>> getAllTowns() {
    try {
      return ResponseEntity.ok(townService.getAllTowns());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }
}
