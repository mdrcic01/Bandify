package com.java.bandify.controller.api.model;

import com.java.bandify.persistance.db.entity.CurrencyEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyDTO {
  private String code;

  public static CurrencyDTO from(CurrencyEntity currencyEntity) {
    return CurrencyDTO.builder()
        .code(currencyEntity.getCode())
        .build();
  }
}
