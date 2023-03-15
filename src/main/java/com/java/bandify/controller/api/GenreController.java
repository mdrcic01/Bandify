package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.GenreDTO;
import com.java.bandify.domain.service.genre.GenreService;
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
@RequestMapping("/genre")
public class GenreController {

  @Autowired
  private GenreService genreService;

  @GetMapping("/{genreId}")
  public ResponseEntity<GenreDTO> getGenre(@PathVariable Integer genreId) {
    try {
      return ResponseEntity.ok(genreService.getGenre(genreId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<GenreDTO>> getAllGenres() {
    try {
      return ResponseEntity.ok(genreService.getAllGenres());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }
}
