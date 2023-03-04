package com.java.bandify.domain.service.instrument;

import com.java.bandify.controller.api.model.BandDTO;
import com.java.bandify.controller.api.model.InstrumentDTO;
import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.InstrumentEntity;
import com.java.bandify.persistance.db.repository.BandRepository;
import com.java.bandify.persistance.db.repository.InstrumentRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentService {

  @Autowired
  private InstrumentRepository instrumentRepository;

  public InstrumentDTO getInstrument(Integer instrumentId) throws NoSuchElementException {
    Optional<InstrumentEntity> instrument = instrumentRepository.findById(Long.valueOf(instrumentId));

    if(instrument.isEmpty())
      throw new NoSuchElementException("There is no instruments under id " + instrumentId);

    return InstrumentDTO.from(instrument.get());
  }

  public List<InstrumentDTO> getAllInstruments() throws NoSuchElementException {
    List<InstrumentEntity> instruments = instrumentRepository.findAll();

    if(instruments.isEmpty())
      throw new NoSuchElementException("No instruments are available");

    return instruments.stream().map(InstrumentDTO::from).collect(Collectors.toList());
  }
}
