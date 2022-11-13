package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of {@link UserDetailsService}.
 */
@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private static final String ROLE_PREFIX = "ROLE_";
  private static final String ROLE_CANDIDATE = "ROLE_CANDIDATE";
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.getUserByEmail(username);
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    if (user.isPresent()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.get().getRole()));
    } else {
      grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_CANDIDATE));
    }
    return new JwtUserDetails(username, passwordEncoder.encode(username), grantedAuthorities);
  }
}
