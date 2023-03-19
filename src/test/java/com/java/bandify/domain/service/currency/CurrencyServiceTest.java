package com.java.bandify.domain.service.currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.controller.api.model.CountyDTO;
import com.java.bandify.controller.api.model.CurrencyDTO;
import com.java.bandify.persistance.db.entity.CountyEntity;
import com.java.bandify.persistance.db.entity.CurrencyEntity;
import com.java.bandify.persistance.db.repository.CurrencyRepository;
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
public class CurrencyServiceTest {

  @InjectMocks
  private CurrencyService currencyService;
  @Mock
  private CurrencyRepository currencyRepository;


  @Test
  public void getCounty_should_returnCountyDTO_when_requestedCountyExist() {
    CurrencyEntity currency = buildCurrencyEntity(1, "County");
    when(currencyRepository.findById(anyInt())).thenReturn(Optional.of(currency));

    //ACTION
    CurrencyDTO currencyDTO = currencyService.getCurrency(1);

    assertThat(currencyDTO.getCode()).isEqualTo(currency.getCode());
  }

  @Test
  public void getCounty_should_throwException_when_requestedCountyDoesNotExist() {
    when(currencyRepository.findById(anyInt())).thenReturn(Optional.empty());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> currencyService.getCurrency(1));
  }

  @Test
  public void getAllCounties_should_returnPopulatedList_when_anyCountiesAreAvailable() {
    List<CurrencyEntity> currencyEntities = buildCurrencyEntityList();
    when(currencyRepository.findAll()).thenReturn(currencyEntities);

    //ACTION
    List<CurrencyDTO> currencyDTOs = currencyService.getAllCurrencies();

    assertThat(currencyDTOs.size()).isEqualTo(currencyEntities.size());
    assertThat(currencyDTOs.get(0).getCode()).isEqualTo(currencyEntities.get(0).getCode());
    assertThat(currencyDTOs.get(1).getCode()).isEqualTo(currencyEntities.get(1).getCode());
    assertThat(currencyDTOs.get(2).getCode()).isEqualTo(currencyEntities.get(2).getCode());

  }

  @Test
  public void getAllCounties_should_throwException_when_noCountiesAreAvailable() {
    when(currencyRepository.findAll()).thenReturn(Collections.emptyList());

    //ACTION
    assertThrows(NoSuchElementException.class, () -> currencyService.getAllCurrencies());
  }

  private List<CurrencyEntity> buildCurrencyEntityList() {
    return List.of(
        buildCurrencyEntity(1, "C1"),
        buildCurrencyEntity(2, "C2"),
        buildCurrencyEntity(3, "C3")
    );
  }

  private CurrencyEntity buildCurrencyEntity(Integer id, String code) {
    return CurrencyEntity.builder()
        .id(id)
        .code(code)
        .build();
  }
}
