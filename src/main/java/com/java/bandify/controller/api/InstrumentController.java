package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.InstrumentDTO;
import com.java.bandify.domain.service.instrument.InstrumentService;
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
@RequestMapping("/instrument")
@CrossOrigin("http://localhost:3000")
public class InstrumentController {

     @Autowired
     private InstrumentService instrumentService;

     @GetMapping("/{instrumentId}")
     public ResponseEntity<InstrumentDTO> getInstrument(@PathVariable Integer instrumentId) {
          try {
               return ResponseEntity.ok(instrumentService.getInstrument(instrumentId));
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }

     @GetMapping
     public ResponseEntity<List<InstrumentDTO>> getAllInstruments() {
          try {
               return ResponseEntity.ok(instrumentService.getAllInstruments());
          } catch (NoSuchElementException e) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
          }
     }
}
