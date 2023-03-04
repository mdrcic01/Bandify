package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.CountyDTO;
import com.java.bandify.domain.service.county.CountyService;
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
@RequestMapping("/county")
public class CountyController {

  @Autowired
  private CountyService countyService;

  @GetMapping("/{countyId}")
  public ResponseEntity<CountyDTO> getCounty(@PathVariable Integer countyId) {
    try {
      return ResponseEntity.ok(countyService.getCounty(countyId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<CountyDTO>> getAllCounties() {
    try {
      return ResponseEntity.ok(countyService.getAllCounties());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }
}
