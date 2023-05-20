package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityEntity, Integer> {

}
