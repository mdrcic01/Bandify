package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.StateEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<StateEntity, Integer> {

     List<StateEntity> findAllByCountryId(Integer id);
}
