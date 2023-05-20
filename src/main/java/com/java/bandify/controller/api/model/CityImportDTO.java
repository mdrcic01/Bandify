package com.java.bandify.controller.api.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CityImportDTO {
  private Integer id;
  private String name;
  private Integer state_id;

}
