package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.StateDTO;
import com.java.bandify.domain.service.state.StateService;
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
@RequestMapping("/state")
public class StateController {

  @Autowired
  private StateService stateService;

  @GetMapping("/{stateId}")
  public ResponseEntity<StateDTO> getState(@PathVariable Integer stateId) {
    try {
      return ResponseEntity.ok(stateService.getState(stateId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<StateDTO>> getAllStates() {
    try {
      return ResponseEntity.ok(stateService.getAllStates());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }
}
