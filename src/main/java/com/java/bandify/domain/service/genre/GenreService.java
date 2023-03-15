package com.java.bandify.domain.service.genre;

import com.java.bandify.controller.api.model.GenreDTO;
import com.java.bandify.persistance.db.entity.GenreEntity;
import com.java.bandify.persistance.db.repository.GenreRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

  @Autowired
  private GenreRepository genreRepository;

  public GenreDTO getGenre(Integer genreId) throws NoSuchElementException {
    Optional<GenreEntity> genre = genreRepository.findById(Long.valueOf(genreId));

    if(genre.isEmpty())
      throw new NoSuchElementException("There is no genres under id " + genreId);

    return GenreDTO.from(genre.get());
  }

  public List<GenreDTO> getAllGenres() throws NoSuchElementException {
    List<GenreEntity> genres = genreRepository.findAll();

    if(genres.isEmpty())
      throw new NoSuchElementException("No genres are available");

    return genres.stream().map(GenreDTO::from).collect(Collectors.toList());
  }
}
