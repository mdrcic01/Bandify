package com.java.bandify.controller.api;

import com.java.bandify.domain.service.application.ApplicationService;
import com.java.bandify.persistance.db.entity.ApplicationEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {

     @Autowired
     private ApplicationService applicationService;

     @PostMapping("/{bandId}/{musicianId}")
     public ResponseEntity<ApplicationEntity> saveApplication(@PathVariable Integer bandId,
         @PathVariable Integer musicianId) {
          ApplicationEntity application = applicationService.saveApplication(bandId, musicianId);
          return ResponseEntity.status(HttpStatus.OK).body(application);
     }

     @PostMapping("/{bandId}/{musicianId}/{status}")
     public ResponseEntity<ApplicationEntity> updateApplicationStatus(@PathVariable Integer bandId,
         @PathVariable Integer musicianId, @PathVariable String status) {
          ApplicationEntity application = applicationService.updateApplicationStatus(bandId, musicianId, status);
          return ResponseEntity.status(HttpStatus.OK).body(application);
     }

     @GetMapping("/{bandId}")
     public ResponseEntity<List<ApplicationEntity>> getAllApplicationStatus(@PathVariable Integer bandId) {
          List<ApplicationEntity> applicationEntities = applicationService.getAllApplicationStatusesByBandId(bandId);
          return ResponseEntity.status(HttpStatus.OK).body(applicationEntities);
     }

     @GetMapping("/musician/{musicianId}")
     public ResponseEntity<List<ApplicationEntity>> getAllApplicationStatusByMusician(
         @PathVariable Integer musicianId) {
          List<ApplicationEntity> applicationEntities = applicationService.getAllApplicationStatusesByMusician(
              musicianId);
          return ResponseEntity.status(HttpStatus.OK).body(applicationEntities);
     }

     @DeleteMapping("/{bandId}/{musicianId}")
     public ResponseEntity<ApplicationEntity> deleteApplication(@PathVariable Integer bandId,
         @PathVariable Integer musicianId) {
          ApplicationEntity application = applicationService.updateApplicationStatus(bandId, musicianId, "revoked");
          return ResponseEntity.status(HttpStatus.OK).body(application);
     }

}
