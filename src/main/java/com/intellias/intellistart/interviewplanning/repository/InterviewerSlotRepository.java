package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * InterviewerSlotRepository repository.
 */

@Repository
public interface InterviewerSlotRepository extends JpaRepository<InterviewerSlot, Long> {

  List<InterviewerSlot> getAllByInterviewer(User interviewer);

  List<InterviewerSlot> getAllByWeekAndDayOfWeek(final int week, final DayOfWeek dayOfWeek);
}
