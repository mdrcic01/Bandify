package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.AuthorityEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {
}
