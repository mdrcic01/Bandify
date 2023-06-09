package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.MusicianEntity;
import jakarta.transaction.Transactional;

@Transactional
public interface MusicianRepository extends UserBaseRepository<MusicianEntity> {

}
