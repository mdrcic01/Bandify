package com.java.bandify.controller.api;

import com.java.bandify.controller.api.model.CurrencyDTO;
import com.java.bandify.domain.service.currency.CurrencyService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

  @Autowired
  private CurrencyService currencyService;

  @GetMapping("/{currencyId}")
  public ResponseEntity<CurrencyDTO> getCurrency(@PathVariable Integer currencyId) {
    try {
      return ResponseEntity.ok(currencyService.getCurrency(currencyId));
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<CurrencyDTO>> getAllCurrencies() {
    try {
      return ResponseEntity.ok(currencyService.getAllCurrencies());
    } catch (NoSuchElementException e) {
      return ResponseEntity.of(
          ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
    }
  }
}
