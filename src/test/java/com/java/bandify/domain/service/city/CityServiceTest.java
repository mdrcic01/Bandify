package com.java.bandify.domain.service.city;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.CityDTO;
import com.java.bandify.persistance.db.entity.StateEntity;
import com.java.bandify.persistance.db.entity.CityEntity;
import com.java.bandify.persistance.db.repository.CityRepository;
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
public class CityServiceTest {

  @InjectMocks
  private CityService cityService;
  @Mock
  private CityRepository cityRepository;

  @Test
  public void getTown_should_returnTownDTO_when_requestedTownExist() {
    CityEntity town = buildCityEntity(1, "Town");
    when(cityRepository.findById(anyInt())).thenReturn(Optional.of(town));

    //ACTION
    CityDTO cityDTO = cityService.getCity(1);

    assertThat(cityDTO.getPostalCode()).isEqualTo(town.getPostalCode());
    assertThat(cityDTO.getName()).isEqualTo(town.getName());
  }

  @Test
  public void getTown_should_throwException_when_requestedTownDoesNotExist() {
    when(cityRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> cityService.getCity(1));
  }

  @Test
  public void getAllCounties_should_returnPopulatedList_when_anyCountiesAreAvailable() {
    List<CityEntity> townEntities = buildCityEntityList();
    when(cityRepository.findAll()).thenReturn(townEntities);

    //ACTION
    List<CityDTO> cityDTOS = cityService.getAllCities();

    assertThat(cityDTOS.size()).isEqualTo(townEntities.size());
    assertThat(cityDTOS.get(0).getName()).isEqualTo(townEntities.get(0).getName());
    assertThat(cityDTOS.get(0).getPostalCode()).isEqualTo(townEntities.get(0).getPostalCode());
    assertThat(cityDTOS.get(1).getName()).isEqualTo(townEntities.get(1).getName());
    assertThat(cityDTOS.get(1).getPostalCode()).isEqualTo(townEntities.get(1).getPostalCode());
    assertThat(cityDTOS.get(2).getName()).isEqualTo(townEntities.get(2).getName());
    assertThat(cityDTOS.get(2).getPostalCode()).isEqualTo(townEntities.get(2).getPostalCode());
  }

  @Test
  public void getAllCounties_should_throwException_when_noCountiesAreAvailable() {
    when(cityRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> cityService.getAllCities());
  }

  private List<CityEntity> buildCityEntityList() {
    return List.of(
        buildCityEntity(1, "Town1"),
        buildCityEntity(2, "Town2"),
        buildCityEntity(3, "Town3")
    );
  }

  private CityEntity buildCityEntity(Integer id, String name) {
    return CityEntity.builder()
        .postalCode(id)
        .state(buildStateEntity())
        .name(name)
        .build();
  }

  private StateEntity buildStateEntity() {
    return StateEntity.builder()
        .id(1)
        .name("County")
        .build();
  }
}
