package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.NonmusicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonmusicianRepository extends JpaRepository<NonmusicianEntity, Long> {

}
