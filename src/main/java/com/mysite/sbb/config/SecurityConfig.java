package com.mysite.sbb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.formLogin(form -> form
        .loginPage("/member/login") // 로그인 페이지 URL
        .defaultSuccessUrl("/")
        .failureUrl("/member/login/error")
        .usernameParameter("username")
        .passwordParameter("password")
        .permitAll());

    http.logout(Customizer.withDefaults());

    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/css/**", "/js/**").permitAll()
            .requestMatchers("/").permitAll()
            //.requestMatchers("/question/**").permitAll()
            .requestMatchers("/member/**").permitAll()
            .anyRequest().authenticated());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // 시큐리티 인증 및 권한 부여 처리를 위해 사용
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
