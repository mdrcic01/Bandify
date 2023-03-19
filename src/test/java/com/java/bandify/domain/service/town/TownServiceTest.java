package com.java.bandify.domain.service.town;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.TownDTO;
import com.java.bandify.persistance.db.entity.CountyEntity;
import com.java.bandify.persistance.db.entity.TownEntity;
import com.java.bandify.persistance.db.repository.TownRepository;
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
public class TownServiceTest {

  @InjectMocks
  private TownService townService;
  @Mock
  private TownRepository townRepository;

  @Test
  public void getTown_should_returnTownDTO_when_requestedTownExist() {
    TownEntity town = buildTownEntity(1, "Town");
    when(townRepository.findById(anyInt())).thenReturn(Optional.of(town));

    //ACTION
    TownDTO townDTO = townService.getTown(1);

    assertThat(townDTO.getPostalCode()).isEqualTo(town.getPostalCode());
    assertThat(townDTO.getName()).isEqualTo(town.getName());
  }

  @Test
  public void getTown_should_throwException_when_requestedTownDoesNotExist() {
    when(townRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> townService.getTown(1));
  }

  @Test
  public void getAllCounties_should_returnPopulatedList_when_anyCountiesAreAvailable() {
    List<TownEntity> townEntities = buildTownEntityList();
    when(townRepository.findAll()).thenReturn(townEntities);

    //ACTION
    List<TownDTO> townDTOs = townService.getAllTowns();

    assertThat(townDTOs.size()).isEqualTo(townEntities.size());
    assertThat(townDTOs.get(0).getName()).isEqualTo(townEntities.get(0).getName());
    assertThat(townDTOs.get(0).getPostalCode()).isEqualTo(townEntities.get(0).getPostalCode());
    assertThat(townDTOs.get(1).getName()).isEqualTo(townEntities.get(1).getName());
    assertThat(townDTOs.get(1).getPostalCode()).isEqualTo(townEntities.get(1).getPostalCode());
    assertThat(townDTOs.get(2).getName()).isEqualTo(townEntities.get(2).getName());
    assertThat(townDTOs.get(2).getPostalCode()).isEqualTo(townEntities.get(2).getPostalCode());
  }

  @Test
  public void getAllCounties_should_throwException_when_noCountiesAreAvailable() {
    when(townRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> townService.getAllTowns());
  }

  private List<TownEntity> buildTownEntityList() {
    return List.of(
        buildTownEntity(1, "Town1"),
        buildTownEntity(2, "Town2"),
        buildTownEntity(3, "Town3")
    );
  }

  private TownEntity buildTownEntity(Integer id, String name) {
    return TownEntity.builder()
        .postalCode(id)
        .county(buildCountyEntity())
        .name(name)
        .build();
  }

  private CountyEntity buildCountyEntity() {
    return CountyEntity.builder()
        .id(1)
        .name("County")
        .build();
  }
}
