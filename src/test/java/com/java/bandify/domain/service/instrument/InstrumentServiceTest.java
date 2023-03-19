package com.java.bandify.domain.service.instrument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.InstrumentDTO;
import com.java.bandify.domain.service.instrument.InstrumentService;
import com.java.bandify.persistance.db.entity.InstrumentEntity;
import com.java.bandify.persistance.db.repository.InstrumentRepository;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class InstrumentServiceTest {
  
  @InjectMocks
  private InstrumentService instrumentService;
  @Mock
  private InstrumentRepository instrumentRepository;


  @Test
  public void getInstrument_should_returnInstrumentDTO_when_requestedInstrumentExist() {
    InstrumentEntity instrument = buildInstrumentEntity(1, "Instrument");
    when(instrumentRepository.findById(anyInt())).thenReturn(Optional.of(instrument));

    //ACTION
    InstrumentDTO instrumentDTO = instrumentService.getInstrument(1);

    assertThat(instrumentDTO.getInstrumentName()).isEqualTo(instrument.getName());
  }

  @Test
  public void getInstrument_should_throwException_when_requestedInstrumentDoesNotExist() {
    when(instrumentRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> instrumentService.getInstrument(1));
  }

  @Test
  public void getAllInstruments_should_returnPopulatedList_when_anyInstrumentsAreAvailable() {
    List<InstrumentEntity> instrumentEntities = buildInstrumentEntityList();
    when(instrumentRepository.findAll()).thenReturn(instrumentEntities);

    //ACTION
    List<InstrumentDTO> instrumentDTOs = instrumentService.getAllInstruments();

    assertThat(instrumentDTOs.size()).isEqualTo(instrumentEntities.size());
    assertThat(instrumentDTOs.get(0).getInstrumentName()).isEqualTo(instrumentEntities.get(0).getName());
    assertThat(instrumentDTOs.get(1).getInstrumentName()).isEqualTo(instrumentEntities.get(1).getName());
    assertThat(instrumentDTOs.get(2).getInstrumentName()).isEqualTo(instrumentEntities.get(2).getName());

  }

  @Test
  public void getAllInstruments_should_throwException_when_noInstrumentsAreAvailable() {
    when(instrumentRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> instrumentService.getAllInstruments());
  }

  private List<InstrumentEntity> buildInstrumentEntityList() {
    return List.of(
        buildInstrumentEntity(1, "Instrument1"),
        buildInstrumentEntity(2, "Instrument2"),
        buildInstrumentEntity(3, "Instrument3")
    );
  }

  private InstrumentEntity buildInstrumentEntity(Integer id, String name) {
    return InstrumentEntity.builder()
        .id(id)
        .name(name)
        .build();
  }
}
