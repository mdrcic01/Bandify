package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.BandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<BandEntity, Long> {

}
