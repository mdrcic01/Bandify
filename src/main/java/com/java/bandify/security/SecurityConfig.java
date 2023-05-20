package com.java.bandify.security;

import com.java.bandify.security.jwt.JwtAuthenticationEntryPoint;
import com.java.bandify.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

     private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
     private final JwtFilter jwtFilter;

     @Bean
     public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }

     @Bean
     public WebSecurityCustomizer webSecurityCustomizer() {
          return (web) -> web.ignoring()
              .requestMatchers(HttpMethod.OPTIONS, "/**")
              .requestMatchers("/h2-console/**");
     }

     @Bean
     public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
          httpSecurity
              .csrf()
              .disable()
              .authorizeHttpRequests()
              .requestMatchers("/api/authenticate").permitAll()
              .anyRequest().authenticated()
              .and()
              .exceptionHandling()
              .authenticationEntryPoint(jwtAuthenticationEntryPoint)
              .and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

          httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

          return httpSecurity.build();
     }

}
