package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

}
