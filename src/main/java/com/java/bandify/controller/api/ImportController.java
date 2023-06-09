package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.CityImportDTO;
import com.java.bandify.controller.api.model.CountryDTO;
import com.java.bandify.controller.api.model.StateDTO;
import com.java.bandify.domain.service.city.CityService;
import com.java.bandify.domain.service.country.CountryService;
import com.java.bandify.domain.service.state.StateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
@CrossOrigin("http://localhost:3000")
public class ImportController {

     @Autowired
     private CountryService countryService;
     @Autowired
     private StateService stateService;
     @Autowired
     private CityService cityService;

     @PostMapping("/country")
     public ResponseEntity<HttpStatus> importCountries(@RequestBody List<CountryDTO> countryDTOs) {
          countryService.importCountries(countryDTOs);
          return ResponseEntity.status(HttpStatus.OK).build();
     }

     @PostMapping("/state")
     public ResponseEntity<HttpStatus> importStates(@RequestBody List<StateDTO> stateDTOList) {
          stateService.importStates(stateDTOList);
          return ResponseEntity.status(HttpStatus.OK).build();
     }

     @PostMapping("/city")
     public ResponseEntity<HttpStatus> importCities(@RequestBody List<CityImportDTO> cityImportDTOS) {
          cityService.importCities(cityImportDTOS);
          return ResponseEntity.status(HttpStatus.OK).build();
     }
}
