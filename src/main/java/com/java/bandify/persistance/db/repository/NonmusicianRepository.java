package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.NonmusicianEntity;
import jakarta.transaction.Transactional;

@Transactional
public interface NonmusicianRepository extends UserBaseRepository<NonmusicianEntity> {

}
