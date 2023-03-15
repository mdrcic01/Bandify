package com.java.bandify.domain.service.band;

import com.java.bandify.controller.api.model.BandDTO;
import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.CurrencyEntity;
import com.java.bandify.persistance.db.entity.GenreEntity;
import com.java.bandify.persistance.db.repository.BandRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BandService {

  @Autowired
  private BandRepository bandRepository;

  public BandDTO getBand(Integer bandId) throws NoSuchElementException {
    Optional<BandEntity> band = bandRepository.findById(Long.valueOf(bandId));

    if(band.isEmpty())
      throw new NoSuchElementException("There is no bands under id " + bandId);

    return BandDTO.from(band.get());
  }

  public List<BandDTO> getAllBands() throws NoSuchElementException {
    List<BandEntity> bands = bandRepository.findAll();

    if(bands.isEmpty())
      throw new NoSuchElementException("No bands are available");

    return bands.stream().map(BandDTO::from).collect(Collectors.toList());
  }

  public void createOrEditBand(BandDTO bandDTO, Integer id) {
    bandRepository.save(
        BandEntity.builder()
            .id(id)
            .bandName(bandDTO.getBandName())
            .genre(GenreEntity.builder().genre(bandDTO.getGenre()).build())
            .currency(CurrencyEntity.builder().code(bandDTO.getCurrency()).build())
            .build()
    );
  }
}
