package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
     Optional<UserEntity> findByUsername(String username);
}
