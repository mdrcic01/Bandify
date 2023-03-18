package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.CountyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountyRepository extends JpaRepository<CountyEntity, Integer> {

}
