package com.java.bandify.domain.service.country;

import com.java.bandify.controller.api.model.CountryDTO;
import com.java.bandify.domain.service.state.StateService;
import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.repository.CountryRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

     @Autowired
     private CountryRepository countryRepository;

     public CountryDTO getCountry(Integer countryId) throws NoSuchElementException {
          Optional<CountryEntity> country = countryRepository.findById(countryId);

       if (country.isEmpty()) {
         throw new NoSuchElementException("There is no countries under id " + countryId);
       }

          return CountryDTO.from(country.get());
     }

     public List<CountryDTO> getAllCountries() throws NoSuchElementException {
          List<CountryEntity> countries = countryRepository.findAll();

       if (countries.isEmpty()) {
         throw new NoSuchElementException("No countries are available");
       }

          return countries.stream().map(CountryDTO::from).collect(Collectors.toList());
     }

     public void importCountries(List<CountryDTO> countryDTOs) {
          for (CountryDTO countryDTO : countryDTOs) {
               countryRepository.save(
                   CountryEntity.builder()
                       .id(countryDTO.getId())
                       .name(countryDTO.getName())
                       .build()
               );
          }
     }

     public CountryEntity fetchCountryEntityById(Integer countryId) throws NoSuchElementException {
        Optional<CountryEntity> country = countryRepository.findById(countryId);
        if(country.isEmpty()) return countryRepository.getReferenceById(0);
          //throw new NoSuchElementException(String.format("No element with id %d", countryId));

        return country.get();
     }
}
