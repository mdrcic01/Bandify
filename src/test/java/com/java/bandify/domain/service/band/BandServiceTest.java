package com.java.bandify.domain.service.band;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.COLLECTION;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.BandDTO;
import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.CurrencyEntity;
import com.java.bandify.persistance.db.entity.GenreEntity;
import com.java.bandify.persistance.db.repository.BandRepository;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BandServiceTest {

  @InjectMocks
  private BandService bandService;
  @Mock
  private BandRepository bandRepository;
  @Captor
  private ArgumentCaptor<BandEntity> bandEntityArgumentCaptor;

  @Test
  public void getAllBands_should_throwException_when_noBandsExist() {
    when(bandRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> bandService.getAllBands());
  }

  @Test
  public void getAllBands_should_returnPopulatedList_when_bandsAreInDatabase() {
    when(bandRepository.findAll()).thenReturn(buildBandEntityList());

    //ACTION
    List<BandDTO> bands = bandService.getAllBands();

    assertThat(bands).isEqualTo(buildBandDTOList());
  }

  @Test
  public void getBand_should_throwException_when_requestedBandDoesNotExist() {
    when(bandRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> bandService.getBand(1));
  }

  @Test
  public void getBand_should_returnBandDTO_when_requestedBandExists() {
    BandEntity bandEntity = buildBandEntity("Band", 12, null);
    when(bandRepository.findById(anyInt())).thenReturn(Optional.of(bandEntity));

    //ACTION
    BandDTO bandDTO = bandService.getBand(1);

    assertThat(bandDTO.getBandName()).isEqualTo(bandEntity.getBandName());
    assertThat(bandDTO.getCurrency()).isEqualTo(bandEntity.getCurrency().getId());
  }

  @Test
  public void createOrEditBand_should_successfullyInsertAndReturnBandEntity_when_idInEntityIsNull() {
    BandEntity newBand = buildBandEntity("band", 12, null);
    when(bandRepository.save(bandEntityArgumentCaptor.capture())).thenReturn(newBand);

    //ACTION
    BandEntity bandEntity = bandRepository.save(newBand);

    assertThat(bandEntity).isEqualTo(newBand);
    verify(bandRepository, times(1)).save(bandEntityArgumentCaptor.capture());
  }

  @Test
  public void createOrEditBand_should_successfullyEditAndReturnBandEntity_when_idInEntityIsPresent() {
    BandEntity newBand = buildBandEntity("band", 12, 1);
    when(bandRepository.save(bandEntityArgumentCaptor.capture())).thenReturn(newBand);

    //ACTION
    BandEntity bandEntity = bandRepository.save(newBand);

    assertThat(bandEntity).isEqualTo(newBand);
    verify(bandRepository, times(1)).save(bandEntityArgumentCaptor.capture());
  }

  private List<BandEntity> buildBandEntityList() {
    return List.of(
        buildBandEntity("Band1", 10, 1),
        buildBandEntity("Band2", 11, 2),
        buildBandEntity("Band3", 12, 3)
    );
  }

  private List<BandDTO> buildBandDTOList() {
    return List.of(
        buildBandDTO("Band1", 10),
        buildBandDTO("Band2", 11),
        buildBandDTO("Band3", 12)
    );
  }

  private BandDTO buildBandDTO(String name, Integer price) {
    return BandDTO.builder()
        .bandName(name)
        .genre(1)
        .currency(1)
        .price(price)
        .build();
  }

  private BandEntity buildBandEntity(String name, Integer price, Integer id) {
    return BandEntity.builder()
        .id(id)
        .bandName(name)
        .genre(buildGenre())
        .currency(buildCurrency())
        .price(price)
        .build();
  }

  private GenreEntity buildGenre() {
    return GenreEntity.builder()
        .id(1)
        .genre("Genre")
        .build();
  }

  private CurrencyEntity buildCurrency() {
    return CurrencyEntity.builder()
        .id(1)
        .code("CUR")
        .build();
  }
}
