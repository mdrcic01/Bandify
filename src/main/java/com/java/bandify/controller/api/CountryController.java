package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.CountryDTO;
import com.java.bandify.domain.service.country.CountryService;
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
@RequestMapping("/country")
public class CountryController {

  @Autowired
  private CountryService countryService;

  @GetMapping("/{countryId}")
  public ResponseEntity<CountryDTO> getCountry(@PathVariable Integer countryId) {
    try {
      return ResponseEntity.ok(countryService.getCountry(countryId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<CountryDTO>> getAllCountries() {
    try {
      return ResponseEntity.ok(countryService.getAllCountries());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }
}
