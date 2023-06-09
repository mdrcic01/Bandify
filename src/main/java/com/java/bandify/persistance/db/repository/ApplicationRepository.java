package com.java.bandify.persistance.db.repository;

import com.java.bandify.persistance.db.entity.ApplicationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {

     ApplicationEntity findByBandIdAndMusicianId(Integer bandId, Integer userId);

     List<ApplicationEntity> findAllByBandIdAndStatus(Integer bandId, String status);

     List<ApplicationEntity> findAllByMusicianIdAndStatus(Integer musicianId, String status);

     List<ApplicationEntity> findAllByMusicianId(Integer musicianId);

     void deleteAllByBand_Id(Integer band_id);
}
