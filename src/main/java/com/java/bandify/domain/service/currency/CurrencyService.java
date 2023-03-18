package com.java.bandify.domain.service.currency;

import com.java.bandify.controller.api.model.CurrencyDTO;
import com.java.bandify.persistance.db.entity.CurrencyEntity;
import com.java.bandify.persistance.db.repository.CurrencyRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

  @Autowired
  private CurrencyRepository currencyRepository;

  public CurrencyDTO getCurrency(Integer currencyId) throws NoSuchElementException {
    Optional<CurrencyEntity> currency = currencyRepository.findById(currencyId);

    if(currency.isEmpty())
      throw new NoSuchElementException("There is no currencies under id " + currencyId);

    return CurrencyDTO.from(currency.get());
  }

  public List<CurrencyDTO> getAllCurrencies() throws NoSuchElementException {
    List<CurrencyEntity> currencies = currencyRepository.findAll();

    if(currencies.isEmpty())
      throw new NoSuchElementException("No currencies are available");

    return currencies.stream().map(CurrencyDTO::from).collect(Collectors.toList());
  }

  public CurrencyEntity fetchCurrencyIfExistOrThrow(Integer currencyId) {
    Optional<CurrencyEntity> currency = currencyRepository.findById(currencyId);
    if(currency.isEmpty()) {
      throw new NoSuchElementException(String.format("Currency uder id %d doesn't exist", currencyId));
    }

    return currency.get();
  }
}
