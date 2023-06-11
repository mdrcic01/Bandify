package com.java.bandify.security.web;


import com.java.bandify.persistance.db.entity.AuthorityEntity;
import com.java.bandify.persistance.db.entity.CityEntity;
import com.java.bandify.persistance.db.entity.InstrumentEntity;
import com.java.bandify.persistance.db.entity.MusicianEntity;
import com.java.bandify.persistance.db.entity.NonmusicianEntity;
import com.java.bandify.persistance.db.entity.UserEntity;
import com.java.bandify.persistance.db.repository.AuthorityRepository;
import com.java.bandify.persistance.db.repository.CityRepository;
import com.java.bandify.persistance.db.repository.InstrumentRepository;
import com.java.bandify.persistance.db.repository.UserRepository;
import com.java.bandify.security.jwt.JwtFilter;
import com.java.bandify.security.jwt.TokenProvider;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {

     private final TokenProvider tokenProvider;
     private final AuthenticationManagerBuilder authenticationManagerBuilder;
     @Autowired
     UserRepository userRepository;
     @Autowired
     InstrumentRepository instrumentRepository;
     @Autowired
     AuthorityRepository authorityRepository;
     @Autowired
     CityRepository cityRepository;
     @Autowired
     PasswordEncoder passEncoder;

     public LoginController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
          this.tokenProvider = tokenProvider;
          this.authenticationManagerBuilder = authenticationManagerBuilder;
     }

     @PostMapping("/authenticate")
     public ResponseEntity<JWTToken> authenticate(@Valid @RequestBody LoginController.LoginDTO login) {

          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              login.getUsername(),
              login.getPassword()
          );

          Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
          SecurityContextHolder.getContext().setAuthentication(authentication);

          String jwt = tokenProvider.createToken(authentication);

          HttpHeaders httpHeaders = new HttpHeaders();
          httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

          Optional<UserEntity> userObj = userRepository.findByUsername(login.getUsername());
          UserEntity user = userObj.get();

          return new ResponseEntity<>(
              new JWTToken(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                  user.getUserType(), jwt), httpHeaders, HttpStatus.OK);
     }

     @PostMapping("/register")
     public ResponseEntity<?> register(@Valid @RequestBody LoginController.SignUpDTO signUp) {
          Optional<UserEntity> userObj = userRepository.findByUsername(signUp.getUsername());
          if (userObj.isPresent()) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), "Username already exists")).build();
          }
          List<AuthorityEntity> authoritiesList = authorityRepository.findAll();
          Optional<CityEntity> city = cityRepository.findById(signUp.getCity());

          if (signUp.getRole().equals("musician")) {

               List<String> instruments = signUp.getInstruments();
               List<InstrumentEntity> instrumentList = instrumentRepository.findAllByNameIn(instruments);

               return ResponseEntity.status(HttpStatus.OK)
                   .body(userRepository.save(new MusicianEntity(signUp.getUsername(),
                       passEncoder.encode(signUp.getPassword()),
                       signUp.getFirstName(), signUp.getLastname(), signUp.getRole(),
                       signUp.getDateOfBirth(),
                       authoritiesList,
                       city.get(), null, instrumentList)));

          } else if (signUp.getRole().equals("non-musician")) {

               return ResponseEntity.status(HttpStatus.OK)
                   .body(userRepository.save(new NonmusicianEntity(signUp.getUsername(),
                       passEncoder.encode(signUp.getPassword()),
                       signUp.getFirstName(), signUp.getLastname(), signUp.getRole(),
                       signUp.getDateOfBirth(),
                       authoritiesList,
                       city.get(), signUp.getIntrests())));
          }
          return null;
     }

     @PostMapping("/update-user/{userId}")
     public ResponseEntity<?> updateUser(@PathVariable Integer userId,
         @Valid @RequestBody LoginController.SignUpDTO signUp) {

          Optional<CityEntity> city = cityRepository.findById(signUp.getCity());
          Optional<UserEntity> user = userRepository.findById(userId);
          if (user.isEmpty()) {
               return ResponseEntity.of(
                   ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), "User not found")).build();
          }

          if (signUp.getRole().equals("musician")) {
               List<String> instruments = signUp.getInstruments();
               List<InstrumentEntity> instrumentList = instrumentRepository.findAllByNameIn(instruments);
               MusicianEntity userEntity = (MusicianEntity) user.get();

               userEntity.setUsername(signUp.getUsername());
               if (signUp.getPassword() != null && !signUp.getPassword().trim().equals("")) {
                    userEntity.setPassword(passEncoder.encode(signUp.getPassword()));
               }
               userEntity.setFirstName(signUp.getFirstName());
               userEntity.setLastName(signUp.getLastname());
               userEntity.setDateOfBirth(signUp.getDateOfBirth());
               userEntity.setInstruments(instrumentList);
               userEntity.setCity(city.get());
               return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userEntity));
          } else if (signUp.getRole().equals("non-musician")) {

               NonmusicianEntity userEntity = (NonmusicianEntity) user.get();
               userEntity.setUsername(signUp.getUsername());
               if (signUp.getPassword() != null && !signUp.getPassword().trim().equals("")) {
                    userEntity.setPassword(passEncoder.encode(signUp.getPassword()));
               }
               userEntity.setFirstName(signUp.getFirstName());
               userEntity.setLastName(signUp.getLastname());
               userEntity.setDateOfBirth(signUp.getDateOfBirth());
               userEntity.setCity(city.get());
               userEntity.setBio(signUp.getIntrests());
               return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userEntity));
          }
          return null;
     }


     /**
      * Return jwt token in body because Angular has problems with parsing plain string response entity
      */
     static class JWTToken {

          private Integer userId;
          private String username;
          private String firstName;
          private String lastName;
          private String role;
          private String token;


          public JWTToken(Integer userId, String username, String firstName, String lastName, String role,
              String token) {
               super();
               this.userId = userId;
               this.username = username;
               this.firstName = firstName;
               this.lastName = lastName;
               this.role = role;
               this.token = token;
          }

          public String getToken() {
               return token;
          }

          public void setToken(String token) {
               this.token = token;
          }

          public Integer getUserId() {
               return userId;
          }

          public void setUserId(Integer userId) {
               this.userId = userId;
          }

          public String getUsername() {
               return username;
          }

          public void setUsername(String username) {
               this.username = username;
          }

          public String getFirstName() {
               return firstName;
          }

          public void setFirstName(String firstName) {
               this.firstName = firstName;
          }

          public String getLastName() {
               return lastName;
          }

          public void setLastName(String lastName) {
               this.lastName = lastName;
          }

          public String getRole() {
               return role;
          }

          public void setRole(String role) {
               this.role = role;
          }
     }

     @Data
     static class LoginDTO {

          @NotNull
          private String username;

          @NotNull
          private String password;
     }

     @Data
     static class SignUpDTO {

          @NotNull
          private String username;

          @NotNull
          private String password;

          @NotNull
          private String firstName;

          @NotNull
          private String lastname;

          @NotNull
          private LocalDate dateOfBirth;

          @NotNull
          private String role;

          @NotNull
          private List<String> authorities;

          @NotNull
          private Integer country;

          @NotNull
          private Integer state;

          @NotNull
          private Integer city;

          private List<String> instruments;

          private String intrests;
     }
}
