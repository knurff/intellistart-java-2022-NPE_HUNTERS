package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * UserRepository repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> getAllByRole(final UserRole userRole);

  @Modifying
  @Query(value = "update users set next_week = :nextWeek,"
      + " current_week = :currentWeek  "
      + "where id = :interviewerId", nativeQuery = true)
  void setMaxBookings(@Param("interviewerId") Long interviewerId,
      @Param("currentWeek") int currentWeek,
      @Param("nextWeek") int nextWeek);
}
