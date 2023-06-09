package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.UserEntity;
import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends UserBaseRepository<UserEntity> {

}
