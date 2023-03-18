package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.TownEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownRepository extends JpaRepository<TownEntity, Integer> {

}
