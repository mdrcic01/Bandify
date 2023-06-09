package com.java.bandify.domain.service.application;

import com.java.bandify.persistance.db.entity.ApplicationEntity;
import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.ApplicationRepository;
import com.java.bandify.persistance.db.repository.BandRepository;
import com.java.bandify.persistance.db.repository.MusicianRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

     @Autowired
     private ApplicationRepository applicationRepository;

     @Autowired
     private BandRepository bandRepository;

     @Autowired
     private MusicianRepository musicianRepo;

     public ApplicationEntity saveApplication(Integer bandId, Integer musicianId) {
          Optional<BandEntity> bandOptional = bandRepository.findById(bandId);
          Optional<UserEntity> userOptional = musicianRepo.findById(musicianId);
          if (isBandPresentAndUserMusician(bandOptional, userOptional, musicianId)) {
               applicationRepository.save(
                   new ApplicationEntity(null, bandOptional.get(), userOptional.get(), "pending", LocalDateTime.now()));
          }
          return null;
     }

     public ApplicationEntity updateApplicationStatus(Integer bandId, Integer musicianId, String status) {
          Optional<BandEntity> bandOptional = bandRepository.findById(bandId);
          Optional<UserEntity> userOptional = musicianRepo.findById(musicianId);
          ApplicationEntity applicationEntity = applicationRepository.findByBandIdAndMusicianId(bandId, musicianId);
          if (isBandPresentIsUserMusicianAndIsApplicationNull(bandOptional, userOptional, musicianId,
              applicationEntity)) {

               if (status.equals("approved")) {

                    List<ApplicationEntity> applicationList = applicationRepository.findAllByMusicianIdAndStatus(
                        musicianId, "pending");
                    for (ApplicationEntity application : applicationList) {
                         application.setStatus("cancelled");
                    }
                    applicationRepository.saveAll(applicationList);
                    BandEntity bandEntity = bandOptional.get();
                    MusicianEntity entity = (MusicianEntity) userOptional.get();
                    entity.setBand(bandEntity);
                    musicianRepo.save(entity);
               }
               applicationEntity.setStatus(status);
               return applicationRepository.save(applicationEntity);
          }
          return null;
     }

     public List<ApplicationEntity> getAllApplicationStatus(Integer bandId) {
          Optional<BandEntity> bandOptional = bandRepository.findById(bandId);
          if (bandOptional.isEmpty()) {
               throw new NoSuchElementException("Band not found");
          }
          return applicationRepository.findAllByBandIdAndStatus(bandId, "pending");
     }

     public List<ApplicationEntity> getAllApplicationStatusByMusician(Integer musicianId) {
          return applicationRepository.findAllByMusicianId(musicianId);
     }

     private boolean isBandPresentAndUserMusician(Optional<BandEntity> band, Optional<UserEntity> user,
         Integer musicianId) {
          return band.isPresent() && user.isPresent() &&
              user.get().getUserType().equals("musician") &&
              !Objects.equals(band.get().getCreatedBy().getId(), musicianId);
     }

     private boolean isBandPresentIsUserMusicianAndIsApplicationNull(Optional<BandEntity> band,
         Optional<UserEntity> user, Integer musicianId, ApplicationEntity applicationEntity) {
          return band.isPresent() && user.isPresent() &&
              user.get().getUserType().equals("musician") &&
              !Objects.equals(band.get().getCreatedBy().getId(), musicianId) &&
              applicationEntity != null;
     }

}
