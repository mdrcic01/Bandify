package com.java.bandify.domain.service.county;

import com.java.bandify.controller.api.model.CountyDTO;
import com.java.bandify.persistance.db.entity.CountyEntity;
import com.java.bandify.persistance.db.repository.CountyRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountyService {

  @Autowired
  private CountyRepository countyRepository;

  public CountyDTO getCounty(Integer countyId) throws NoSuchElementException {
    Optional<CountyEntity> county = countyRepository.findById(Long.valueOf(countyId));

    if(county.isEmpty())
      throw new NoSuchElementException("There is no counties under id " + countyId);

    return CountyDTO.from(county.get());
  }

  public List<CountyDTO> getAllCounties() throws NoSuchElementException {
    List<CountyEntity> counties = countyRepository.findAll();

    if(counties.isEmpty())
      throw new NoSuchElementException("No counties are available");

    return counties.stream().map(CountyDTO::from).collect(Collectors.toList());
  }
}
