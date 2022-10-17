package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> getAllByRole(final UserRole userRole);
}
