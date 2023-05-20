package com.java.bandify.domain.service.state;

import com.java.bandify.controller.api.model.StateDTO;
import com.java.bandify.domain.service.city.CityService;
import com.java.bandify.domain.service.country.CountryService;
import com.java.bandify.persistance.db.entity.StateEntity;
import com.java.bandify.persistance.db.repository.StateRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {

  @Autowired
  private StateRepository stateRepository;
  @Autowired
  private CountryService countryService;

  public StateDTO getState(Integer stateId) throws NoSuchElementException {
    Optional<StateEntity> state = stateRepository.findById(stateId);

    if(state.isEmpty())
      throw new NoSuchElementException("There is no states under id " + stateId);

    return StateDTO.from(state.get());
  }

  public List<StateDTO> getAllStates() throws NoSuchElementException {
    List<StateEntity> states = stateRepository.findAll();

    if(states.isEmpty())
      throw new NoSuchElementException("No states are available");

    return states.stream().map(StateDTO::from).collect(Collectors.toList());
  }

  public void importStates(List<StateDTO> states) {
    for (StateDTO state : states) {
      stateRepository.save(
          StateEntity.builder()
              .id(state.getId())
              .name(state.getName())
              .country(countryService.fetchCountryEntityById(state.getCountry_id()))
              .build()
      );
    }
  }

  public StateEntity fetchStateById(Integer stateId) {
    Optional<StateEntity> state = stateRepository.findById(stateId);
    if(state.isEmpty()) return stateRepository.getReferenceById(0);
    return state.get();
  }
}
