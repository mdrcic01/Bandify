package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBaseRepository<T extends UserEntity> extends JpaRepository<UserEntity, Integer> {

     Optional<UserEntity> findByUsername(String username);

     Optional<UserEntity> findById(Integer id);

     List<MusicianEntity> findByBandId(Integer id);
}
