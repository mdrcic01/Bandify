package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.InstrumentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<InstrumentEntity, Integer> {

     List<InstrumentEntity> findAllByNameIn(List<String> nameList);
}
