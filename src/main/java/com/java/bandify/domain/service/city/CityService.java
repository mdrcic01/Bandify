package com.java.bandify.domain.service.city;

import com.java.bandify.controller.api.model.CityDTO;
import com.java.bandify.controller.api.model.CityImportDTO;
import com.java.bandify.domain.service.state.StateService;
import com.java.bandify.persistance.db.entity.CityEntity;
import com.java.bandify.persistance.db.repository.CityRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

  @Autowired
  private CityRepository cityRepository;
  @Autowired
  private StateService stateService;

  public CityDTO getCity(Integer cityId) throws NoSuchElementException {
    Optional<CityEntity> city = cityRepository.findById(cityId);

    if(city.isEmpty())
      throw new NoSuchElementException("There is no cities under id " + cityId);

    return CityDTO.from(city.get());
  }

  public List<CityDTO> getAllCities() throws NoSuchElementException {
    List<CityEntity> cities = cityRepository.findAll();

    if(cities.isEmpty())
      throw new NoSuchElementException("No cities are available");

    return cities.stream().map(CityDTO::from).collect(Collectors.toList());
  }

  public void importCities(List<CityImportDTO> cities) {
    for (CityImportDTO city : cities) {
      cityRepository.save(
          CityEntity.builder()
              .postalCode(city.getId())
              .name(city.getName())
              .state(stateService.fetchStateById(city.getState_id()))
              .build()
      );
    }
  }

  public CityEntity fetchCityEntityIfExistOrThrow(Integer townId) {
    Optional<CityEntity> city = cityRepository.findById(townId);

    if(city.isEmpty()) {
      throw new NoSuchElementException(String.format("City with id %d doesn't exist", townId));
    }

    return city.get();
  }
}
