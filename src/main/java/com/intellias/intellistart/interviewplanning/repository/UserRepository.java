package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> getAllByRole(final UserRole userRole);

  Optional<User> getUserByEmail(final String email);

  Optional<User> getUserByIdAndRole(final Long id, final UserRole role);
}
