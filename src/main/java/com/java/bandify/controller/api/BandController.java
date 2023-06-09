package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.BandDTO;
import com.java.bandify.domain.service.band.BandService;
import com.java.bandify.persistance.db.entity.BandEntity;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/band")
@CrossOrigin("http://localhost:3000")
public class BandController {

     @Autowired
     private BandService bandService;

     @GetMapping("/{bandId}")
     public ResponseEntity<?> getBand(@PathVariable Integer bandId) {
          try {
               return ResponseEntity.ok(bandService.getBand(bandId));
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }

     @GetMapping("/owner/{userId}")
     public ResponseEntity<?> deleteBandByOwner(@PathVariable Integer userId) {
          try {
               return ResponseEntity.ok(bandService.getBandByOwner(userId));
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }

     @DeleteMapping("/{bandId}/{userId}")
     public ResponseEntity<Void> deleteBandByOwner(@PathVariable Integer bandId, @PathVariable Integer userId) {
          try {
               bandService.deleteBandByOwner(bandId, userId);
               return ResponseEntity.noContent().build();
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }

     @GetMapping
     public ResponseEntity<List<BandEntity>> getAllBands() {
          try {
               return ResponseEntity.ok(bandService.getAllBands());
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }

     @PostMapping
     public ResponseEntity<HttpStatus> createNewBand(@RequestBody BandDTO bandDTO) {
          try {
               bandService.createOrEditBand(bandDTO, null);
               return ResponseEntity.status(HttpStatus.CREATED).build();
          } catch (Exception e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }

     @PutMapping("/{bandId}")
     public ResponseEntity<HttpStatus> editBand(@RequestBody BandDTO bandDTO, @PathVariable Integer bandId) {
          try {
               bandService.createOrEditBand(bandDTO, bandId);
               return ResponseEntity.status(HttpStatus.OK).build();
          } catch (Exception e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }
}
