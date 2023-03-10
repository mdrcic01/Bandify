package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

}
