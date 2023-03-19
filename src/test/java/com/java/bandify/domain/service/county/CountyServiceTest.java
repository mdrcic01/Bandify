package com.java.bandify.domain.service.county;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.CountyDTO;
import com.java.bandify.persistance.db.entity.CountryEntity;
import com.java.bandify.persistance.db.entity.CountyEntity;
import com.java.bandify.persistance.db.repository.CountyRepository;
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
public class CountyServiceTest {

  @InjectMocks
  private CountyService countyService;
  @Mock
  private CountyRepository countyRepository;

  @Test
  public void getCounty_should_returnCountyDTO_when_requestedCountyExist() {
    CountyEntity county = buildCountyEntity(1, "County");
    when(countyRepository.findById(anyInt())).thenReturn(Optional.of(county));

    //ACTION
    CountyDTO countyDTO = countyService.getCounty(1);

    assertThat(countyDTO.getId()).isEqualTo(county.getId());
    assertThat(countyDTO.getName()).isEqualTo(county.getName());
  }

  @Test
  public void getCounty_should_throwException_when_requestedCountyDoesNotExist() {
    when(countyRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> countyService.getCounty(1));
  }

  @Test
  public void getAllCounties_should_returnPopulatedList_when_anyCountiesAreAvailable() {
    List<CountyEntity> countyEntities = buildCountyEntityList();
    when(countyRepository.findAll()).thenReturn(countyEntities);

    //ACTION
    List<CountyDTO> countyDTOs = countyService.getAllCounties();

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
    when(countyRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> countyService.getAllCounties());
  }

  private List<CountyEntity> buildCountyEntityList() {
    return List.of(
        buildCountyEntity(1, "County1"),
        buildCountyEntity(2, "County2"),
        buildCountyEntity(3, "County3")
    );
  }

  private CountyEntity buildCountyEntity(Integer id, String name) {
    return CountyEntity.builder()
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
