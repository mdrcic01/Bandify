package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.CityDTO;
import com.java.bandify.domain.service.city.CityService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
@CrossOrigin("http://localhost:3000")
public class CityController {

     @Autowired
     private CityService cityService;

     @GetMapping("/{cityId}")
     public ResponseEntity<CityDTO> getTown(@PathVariable Integer cityId) {
          try {
               return ResponseEntity.ok(cityService.getCity(cityId));
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }

     @GetMapping("bystate/{stateId}")
     public ResponseEntity<List<CityDTO>> getCitiesByState(@PathVariable Integer stateId) {
          try {
               return ResponseEntity.ok(cityService.getCitiesByState(stateId));
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }

     @GetMapping
     public ResponseEntity<List<CityDTO>> getAllCities() {
          try {
               return ResponseEntity.ok(cityService.getAllCities());
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }
}
