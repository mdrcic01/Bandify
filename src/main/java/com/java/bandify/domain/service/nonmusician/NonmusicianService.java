package com.java.bandify.domain.service.nonmusician;

import com.java.bandify.controller.api.model.NonmusicianDTO;
import com.java.bandify.persistance.db.entity.NonmusicianEntity;
import com.java.bandify.persistance.db.repository.NonmusicianRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NonmusicianService {

  @Autowired
  private NonmusicianRepository nonmusicianRepository;

  public NonmusicianDTO getNonmusician(Integer nonmusicianId) throws NoSuchElementException {
    Optional<NonmusicianEntity> nonmusician = nonmusicianRepository.findById(Long.valueOf(nonmusicianId));

    if(nonmusician.isEmpty())
      throw new NoSuchElementException("There is no nonmusicians under id " + nonmusicianId);

    return NonmusicianDTO.from(nonmusician.get());
  }

  public List<NonmusicianDTO> getAllNonmusicians() throws NoSuchElementException {
    List<NonmusicianEntity> nonmusicians = nonmusicianRepository.findAll();

    if(nonmusicians.isEmpty())
      throw new NoSuchElementException("No nonmusicians are available");

    return nonmusicians.stream().map(NonmusicianDTO::from).collect(Collectors.toList());
  }
}
