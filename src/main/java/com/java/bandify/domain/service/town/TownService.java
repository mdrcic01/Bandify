package com.java.bandify.domain.service.town;

import com.java.bandify.controller.api.model.TownDTO;
import com.java.bandify.persistance.db.entity.TownEntity;
import com.java.bandify.persistance.db.repository.TownRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TownService {

  @Autowired
  private TownRepository townRepository;

  public TownDTO getTown(Integer townId) throws NoSuchElementException {
    Optional<TownEntity> town = townRepository.findById(townId);

    if(town.isEmpty())
      throw new NoSuchElementException("There is no towns under id " + townId);

    return TownDTO.from(town.get());
  }

  public List<TownDTO> getAllTowns() throws NoSuchElementException {
    List<TownEntity> towns = townRepository.findAll();

    if(towns.isEmpty())
      throw new NoSuchElementException("No towns are available");

    return towns.stream().map(TownDTO::from).collect(Collectors.toList());
  }

  public TownEntity fetchTownEntityIfExistOrThrow(Integer townId) {
    Optional<TownEntity> town = townRepository.findById(townId);

    if(town.isEmpty()) {
      throw new NoSuchElementException(String.format("Town with id %d doesn't exist", townId));
    }

    return town.get();
  }
}
