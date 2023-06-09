package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {

     CurrencyEntity findByCode(String code);
}
