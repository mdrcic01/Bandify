package com.java.bandify.domain.service.band;

import com.java.bandify.controller.api.model.BandDTO;
import com.java.bandify.domain.service.currency.CurrencyService;
import com.java.bandify.domain.service.genre.GenreService;
import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.InstrumentEntity;
import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.ApplicationRepository;
import com.java.bandify.persistance.db.repository.BandRepository;
import com.java.bandify.persistance.db.repository.InstrumentRepository;
import com.java.bandify.persistance.db.repository.MusicianRepository;
import com.java.bandify.persistance.db.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BandService {

     @Autowired
     InstrumentRepository instrumentRepository;
     @Autowired
     MusicianRepository musicianRepo;
     @Autowired
     ApplicationRepository applicationRepository;
     @Autowired
     private BandRepository bandRepository;
     @Autowired
     private UserRepository userRepository;
     @Autowired
     private GenreService genreService;
     @Autowired
     private CurrencyService currencyService;

     public BandEntity getBand(Integer bandId) throws NoSuchElementException {
          Optional<BandEntity> band = bandRepository.findById(bandId);

          if (band.isEmpty()) {
               throw new NoSuchElementException("There is no bands under id " + bandId);
          }

          return band.get();
     }

     public BandEntity getBandByOwner(Integer userId) throws NoSuchElementException {
          Optional<UserEntity> user = userRepository.findById(userId);
          if (isUserPresentAndMusician(user)) {
               BandEntity band = bandRepository.findByCreatedById(userId);
               return Objects.requireNonNullElseGet(band, BandEntity::new);
          } else {
               return new BandEntity();
          }
     }

     @Transactional
     public void deleteBandByOwner(Integer bandId, Integer userId) throws NoSuchElementException {
          Optional<BandEntity> bandOptional = bandRepository.findById(bandId);

          if (bandOptional.isPresent() && Objects.equals(bandOptional.get().getCreatedBy().getId(), userId)) {
               BandEntity bandEntity = bandOptional.get();
               List<MusicianEntity> musicians = musicianRepo.findByBandId(bandId);

               musicians.forEach(musician -> {
                    musician.setBand(null);
                    musicianRepo.save(musician);
               });
               applicationRepository.deleteAllByBand_Id(bandId);
               bandRepository.delete(bandEntity);
          }
     }

     public List<BandEntity> getAllBands() throws NoSuchElementException {
          List<BandEntity> bands = bandRepository.findAll();

          if (bands.isEmpty()) {
               throw new NoSuchElementException("No bands available");
          }

          return bands;
     }

     public void createOrEditBand(BandDTO bandDTO, Integer id) {
          Optional<UserEntity> user = userRepository.findById(bandDTO.getUserId());
          List<InstrumentEntity> instrumentList = instrumentRepository.findAllByNameIn(bandDTO.getInstruments());

          if (isUserPresentAndMusician(user)) {
               BandEntity owner = bandRepository.findByCreatedById(bandDTO.getUserId());

               if (owner != null && !Objects.equals(owner.getId(), id)) {
                    throw new NoSuchElementException("User already owns other band");
               }

               BandEntity band = null;
               if (id == null) {
                    band = new BandEntity();
                    List<MusicianEntity> musicians = new ArrayList<>();
                    MusicianEntity musician = (MusicianEntity) user.get();
                    musicians.add(musician);
                    band.setMusicians(musicians);
                    musician.setBand(band);
               } else {
                    Optional<BandEntity> bandOptional = bandRepository.findById(id);
                    if (bandOptional.isPresent()) {
                         band = bandOptional.get();
                    }
               }
               band.setBandName(bandDTO.getBandName());
               band.setCreatedBy(user.get());
               band.setCurrency(currencyService.getCurrency(bandDTO.getCurrency()));
               band.setGenre(genreService.fetchGenreByName(bandDTO.getGenre()));
               band.setInstruments(instrumentList);
               band.setPrice(bandDTO.getPrice());
               bandRepository.save(band);
          } else {
               throw new NoSuchElementException("User has no permission to create a band");
          }

     }

     private boolean isUserPresentAndMusician(Optional<UserEntity> user) {
          return user.isPresent() && user.get().getUserType().equals("musician");
     }
}
