package com.java.bandify.domain.service.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.StateDTO;
import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.entity.StateEntity;
import com.java.bandify.persistance.db.repository.StateRepository;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StateServiceTest {

  @InjectMocks
  private StateService stateService;
  @Mock
  private StateRepository stateRepository;

  @Test
  public void getCounty_should_returnCountyDTO_when_requestedCountyExist() {
    StateEntity county = buildCountyEntity(1, "County");
    when(stateRepository.findById(anyInt())).thenReturn(Optional.of(county));

    //ACTION
    StateDTO countyDTO = stateService.getState(1);

    assertThat(countyDTO.getId()).isEqualTo(county.getId());
    assertThat(countyDTO.getName()).isEqualTo(county.getName());
  }

  @Test
  public void getCounty_should_throwException_when_requestedCountyDoesNotExist() {
    when(stateRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> stateService.getState(1));
  }

  @Test
  public void getAllCounties_should_returnPopulatedList_when_anyCountiesAreAvailable() {
    List<StateEntity> countyEntities = buildCountyEntityList();
    when(stateRepository.findAll()).thenReturn(countyEntities);

    //ACTION
    List<StateDTO> countyDTOs = stateService.getAllStates();

    assertThat(countyDTOs.size()).isEqualTo(countyEntities.size());
    assertThat(countyDTOs.get(0).getName()).isEqualTo(countyEntities.get(0).getName());
    assertThat(countyDTOs.get(0).getId()).isEqualTo(countyEntities.get(0).getId());
    assertThat(countyDTOs.get(1).getName()).isEqualTo(countyEntities.get(1).getName());
    assertThat(countyDTOs.get(1).getId()).isEqualTo(countyEntities.get(1).getId());
    assertThat(countyDTOs.get(2).getName()).isEqualTo(countyEntities.get(2).getName());
    assertThat(countyDTOs.get(2).getId()).isEqualTo(countyEntities.get(2).getId());
  }

  @Test
  public void getAllCounties_should_throwException_when_noCountiesAreAvailable() {
    when(stateRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> stateService.getAllStates());
  }

  private List<StateEntity> buildCountyEntityList() {
    return List.of(
        buildCountyEntity(1, "County1"),
        buildCountyEntity(2, "County2"),
        buildCountyEntity(3, "County3")
    );
  }

  private StateEntity buildCountyEntity(Integer id, String name) {
    return StateEntity.builder()
        .id(id)
        .country(buildCountryEntity())
        .name(name)
        .build();
  }

  private CountryEntity buildCountryEntity() {
    return CountryEntity.builder()
        .id(1)
        .name("Country")
        .build();
  }
}
