package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.controller.dto.creator.InterviewerSlotsDtoCreator;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.service.InterviewerService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * InterviewerController controller.
 */
@RestController
@RequestMapping("/interviewers")
@AllArgsConstructor
public class InterviewerController {

  private final InterviewerService interviewerService;
  private final InterviewerSlotsDtoCreator interviewerSlotsDtoCreator;

  /**
   * Returns all interviewer slots by id.
   *
   * @param interviewerId long id of interviewer
   * @return list of InterviewerSlotDto
   */
  @GetMapping("/{interviewerId}/slots")
  public List<InterviewerSlotDto> getAllInterviewerSlots(@PathVariable Long interviewerId) {
    List<InterviewerSlot> interviewerSlots =
        interviewerService.getAllInterviewerSlotsByInterviewerId(interviewerId);

    return interviewerSlotsDtoCreator.createInterviewerSlotsDtoList(interviewerSlots);
  }

  @PostMapping("/{interviewerId}/bookings")
  public void setMaxBookings(@PathVariable Long interviewerId, int maxBooking) {
    interviewerService.setMaxBookings(interviewerId, maxBooking);
  }

}
