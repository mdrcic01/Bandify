package com.java.bandify.domain.service.genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.GenreDTO;
import com.java.bandify.domain.service.genre.GenreService;
import com.java.bandify.persistance.db.entity.GenreEntity;
import com.java.bandify.persistance.db.repository.GenreRepository;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GenreServiceTest {

  @InjectMocks
  private GenreService genreService;
  @Mock
  private GenreRepository genreRepository;


  @Test
  public void getGenre_should_returnGenreDTO_when_requestedGenreExist() {
    GenreEntity genre = buildGenreEntity(1, "Genre");
    when(genreRepository.findById(anyInt())).thenReturn(Optional.of(genre));

    //ACTION
    GenreDTO genreDTO = genreService.getGenre(1);

    assertThat(genreDTO.getGenre()).isEqualTo(genre.getGenre());
  }

  @Test
  public void getGenre_should_throwException_when_requestedGenreDoesNotExist() {
    when(genreRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> genreService.getGenre(1));
  }

  @Test
  public void getAllGenres_should_returnPopulatedList_when_anyGenresAreAvailable() {
    List<GenreEntity> genreEntities = buildGenreEntityList();
    when(genreRepository.findAll()).thenReturn(genreEntities);

    //ACTION
    List<GenreDTO> genreDTOs = genreService.getAllGenres();

    assertThat(genreDTOs.size()).isEqualTo(genreEntities.size());
    assertThat(genreDTOs.get(0).getGenre()).isEqualTo(genreEntities.get(0).getGenre());
    assertThat(genreDTOs.get(1).getGenre()).isEqualTo(genreEntities.get(1).getGenre());
    assertThat(genreDTOs.get(2).getGenre()).isEqualTo(genreEntities.get(2).getGenre());

  }

  @Test
  public void getAllGenres_should_throwException_when_noGenresAreAvailable() {
    when(genreRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> genreService.getAllGenres());
  }

  private List<GenreEntity> buildGenreEntityList() {
    return List.of(
        buildGenreEntity(1, "Genre1"),
        buildGenreEntity(2, "Genre2"),
        buildGenreEntity(3, "Genre3")
    );
  }

  private GenreEntity buildGenreEntity(Integer id, String genre) {
    return GenreEntity.builder()
        .id(id)
        .genre(genre)
        .build();
  }
}
