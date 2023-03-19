package com.java.bandify.domain.service.country;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.CountryDTO;
import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.repository.CountryRepository;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CountryServiceTest {

  @InjectMocks
  private CountryService countryService;
  @Mock
  private CountryRepository countryRepository;

  @Test
  public void getCountry_should_returnCountry_when_specificIdIsGiven() {
    CountryEntity country = buildCountryEntity(1, "Country");
    when(countryRepository.findById(anyInt())).thenReturn(Optional.of(country));

    //ACTION
    CountryDTO countryDTO = countryService.getCountry(1);

    assertThat(countryDTO.getName()).isEqualTo(countryDTO.getName());
    assertThat(countryDTO.getId()).isEqualTo(countryDTO.getId());
  }

  @Test
  public void getCountry_should_throwException_when_requiredCountryDoesNotExist() {
    when(countryRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> countryService.getCountry(1));
  }

  @Test
  public void getAllCountries_should_throwException_when_noCountriesExist() {
    when(countryRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> countryService.getAllCountries());
  }

  @Test
  public void getAllCountries_should_returnPopulatedList_whenMultipleCountriesAreAvailable() {
    List<CountryEntity> countryEntities = buildCountryEntityList();
    when(countryRepository.findAll()).thenReturn(countryEntities);

    //ACTION
    List<CountryDTO> countryDTOs = countryService.getAllCountries();

    assertThat(countryDTOs.size()).isEqualTo(countryEntities.size());
    assertThat(countryDTOs.get(0).getName()).isEqualTo(countryEntities.get(0).getName());
    assertThat(countryDTOs.get(1).getName()).isEqualTo(countryEntities.get(1).getName());
    assertThat(countryDTOs.get(2).getName()).isEqualTo(countryEntities.get(2).getName());

  }

  private CountryEntity buildCountryEntity(Integer id, String name) {
    return CountryEntity.builder()
        .id(id)
        .name(name)
        .build();
  }

  private List<CountryEntity> buildCountryEntityList() {
    return List.of(
        buildCountryEntity(1, "Country1"),
        buildCountryEntity(2, "Country2"),
        buildCountryEntity(3, "Country3")
    );
  }
}
