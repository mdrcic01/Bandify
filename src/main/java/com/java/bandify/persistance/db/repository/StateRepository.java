package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.StateEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<StateEntity, Integer> {

}
