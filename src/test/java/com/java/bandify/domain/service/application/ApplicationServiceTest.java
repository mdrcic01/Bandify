package com.java.bandify.domain.service.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.java.bandify.persistance.db.entity.ApplicationEntity;
import com.java.bandify.persistance.db.entity.BandEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.ApplicationRepository;
import com.java.bandify.persistance.db.repository.BandRepository;
import com.java.bandify.persistance.db.repository.MusicianRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ApplicationServiceTest {


     @InjectMocks
     private ApplicationService applicationService;
     @Mock
     private BandRepository bandRepository;
     @Mock
     private MusicianRepository musicianRepository;
     @Mock
     private ApplicationRepository applicationRepository;

     @Test
     void saveApplication_should_successfullySaveNewApplication_when_givenBandAndMusicianIds() {
          when(bandRepository.findById(anyInt())).thenReturn(Optional.of(buildBandEntity()));
          when(musicianRepository.findById(anyInt())).thenReturn(Optional.of(buildUserEntity("a", "a", 2, "aa", "aa")));
          when(applicationRepository.save(any())).thenReturn(buildApplicationEntity("pending"));

          //ACTION
          ApplicationEntity application = applicationService.saveApplication(1,1);

          assertThat(application.getAppliedOn()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
          assertThat(application.getBand().getBandName()).isEqualTo("band");
          assertThat(application.getMusician().getUsername()).isEqualTo("aa");
     }

     @Test
     void saveApplication_should_returnNull_when_givenParametersDoNotMeetCriteria() {
          //musician and band optionals are empty

          //ACTION
          ApplicationEntity application = applicationService.saveApplication(1,1);

          assertThat(application).isNull();
     }

     @Test
     void getAllApplicationStatuses_should_return_applicationList_when_applicationsAreAvailable() {
          when(bandRepository.findById(anyInt())).thenReturn(Optional.of(buildBandEntity()));

          //ACTION
          List<ApplicationEntity> applications = applicationService.getAllApplicationStatusesByBandId(1);

          assertThat(applications).isEmpty();
     }

     @Test
     void getAllApplicationStatuses_should_throwError_when_bandOptionalIsEmpty() {
          when(bandRepository.findById(anyInt())).thenReturn(Optional.empty());

          assertThrows(NoSuchElementException.class, () -> applicationService.getAllApplicationStatusesByBandId(1));
     }

     @Test
     void getAllApplicationStatusesByMusician_should_return_applicationList_when_applicationsAreAvailable() {
          when(applicationRepository.findAllByMusicianId(anyInt())).thenReturn(Collections.emptyList());

          //ACTION
          List<ApplicationEntity> applications = applicationService.getAllApplicationStatusesByMusician(1);

          assertThat(applications).isEmpty();
     }


     private ApplicationEntity buildApplicationEntity(String status) {
          return ApplicationEntity.builder()
              .id(null)
              .band(buildBandEntity())
              .musician(buildUserEntity("a", "a", 2, "aa", "aa"))
              .status(status)
              .appliedOn(LocalDateTime.now())
              .build();
     }

     private BandEntity buildBandEntity() {
          return BandEntity.builder()
              .id(1)
              .bandName("band")
              .price(122)
              .createdBy(buildUserEntity("a", "a", 2, "aa", "aa"))
              .build();
     }

     private UserEntity buildUserEntity(String firstName, String lastName, Integer id, String username, String password) {
          return UserEntity.builder()
              .id(id)
              .firstName(firstName)
              .lastName(lastName)
              .dateOfBirth(LocalDate.parse("2022-12-12"))
              .username(username)
              .password(password)
              .userType("musician")
              .build();
     }
}
