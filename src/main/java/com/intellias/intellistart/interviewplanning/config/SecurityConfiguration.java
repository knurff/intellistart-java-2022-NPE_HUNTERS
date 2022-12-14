package com.intellias.intellistart.interviewplanning.config;

import com.intellias.intellistart.interviewplanning.exception.CustomAccessDeniedHandler;
import com.intellias.intellistart.interviewplanning.exception.CustomAuthenticationEntryPoint;
import com.intellias.intellistart.interviewplanning.security.JwtTokenFilter;
import com.intellias.intellistart.interviewplanning.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class.
 */
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@AllArgsConstructor
public class SecurityConfiguration {

  private JwtUserDetailsService jwtService;
  private PasswordEncoder passwordEncoder;
  private JwtTokenFilter jwtTokenFilter;

  @Qualifier("customAuthenticationEntryPoint")
  private CustomAuthenticationEntryPoint authenticationEntryPoint;

  private CustomAccessDeniedHandler accessDeniedHandler;

  /**
   * Configures AuthenticationManager bean.
   */
  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity httpSecurity) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(
        AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder);
    return authenticationManagerBuilder.build();
  }

  /**
   * Configures SecurityFilterChain bean.
   */
  @Bean
  public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable();

    httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    httpSecurity.authorizeRequests().antMatchers("/auth/login").permitAll().anyRequest()
        .authenticated();

    httpSecurity.exceptionHandling()
        .authenticationEntryPoint(
            authenticationEntryPoint
        ).accessDeniedHandler(accessDeniedHandler);

    httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

}
