package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.GenreEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreDTO {
  private String genre;

  public static GenreDTO from(GenreEntity genreEntity) {
    return GenreDTO.builder()
        .genre(genreEntity.getGenre())
        .build();
  }
}
