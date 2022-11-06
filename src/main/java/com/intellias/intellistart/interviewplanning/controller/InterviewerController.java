package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.controller.dto.mapper.InterviewerSlotsMapper;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.service.InterviewerService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  private final InterviewerSlotsMapper interviewerSlotsMapper;

  /**
   * Returns all interviewer slots by id.
   *
   * @param interviewerId long id of interviewer
   * @return list of InterviewerSlotDto
   */
  @GetMapping("/{interviewerId}/slots")
  @RolesAllowed("ROLE_INTERVIEWER")
  public List<InterviewerSlotDto> getAllInterviewerSlots(@PathVariable Long interviewerId) {
    List<InterviewerSlot> interviewerSlots =
        interviewerService.getAllInterviewerSlotsByInterviewerId(interviewerId);

    return interviewerSlotsMapper.mapToInterviewerSlotsDtoList(interviewerSlots);
  }

  @PostMapping("/{interviewerId}/bookings")
  @RolesAllowed("ROLE_INTERVIEWER")
  public void setMaxBookings(@PathVariable Long interviewerId, int maxBooking) {
    interviewerService.setMaxBookings(interviewerId, maxBooking);
  }

  /**
   * Returns interviewer slot DTO if slot was created.
   *
   * @param interviewerId      long id of interviewer
   * @param interviewerSlotDto InterviewerSlotDto
   * @return InterviewerSlotDto
   */
  @PostMapping("/{interviewerId}/slots")
  @RolesAllowed("ROLE_INTERVIEWER")
  public InterviewerSlotDto createSlot(@PathVariable Long interviewerId,
      @RequestBody InterviewerSlotDto interviewerSlotDto) {
    InterviewerSlot responseEntity = interviewerService.createSlot(
        interviewerSlotsMapper.mapToInterviewerSlotEntity(interviewerSlotDto), interviewerId);
    return interviewerSlotsMapper.mapToInterviewerSlotsDto(responseEntity);
  }

  /**
   * Returns interviewer slot DTO if slot was edited.
   *
   * @param interviewerId      long id of interviewer
   * @param interviewerSlotDto InterviewerSlotDto
   * @return InterviewerSlotDto
   */
  @PutMapping("/{interviewerId}/slots/{slotId}")
  public InterviewerSlotDto editSlot(@PathVariable Long interviewerId, @PathVariable Long slotId,
      @RequestBody InterviewerSlotDto interviewerSlotDto) {
    InterviewerSlot responseEntity = interviewerService.editSlot(
        interviewerSlotsMapper.mapToInterviewerSlotEntity(interviewerSlotDto), interviewerId,
        slotId);
    return interviewerSlotsMapper.mapToInterviewerSlotsDto(responseEntity);
  }
}
