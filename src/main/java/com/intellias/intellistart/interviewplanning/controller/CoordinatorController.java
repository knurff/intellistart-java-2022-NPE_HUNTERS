package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.service.CoordinatorService;
import com.intellias.intellistart.interviewplanning.util.MappingUtils;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * CoordinatorController controller.
 */
@RestController
public class CoordinatorController {
  private final CoordinatorService coordinatorService;

  public CoordinatorController(final CoordinatorService coordinatorService) {
    this.coordinatorService = coordinatorService;
  }

  @GetMapping("/users/interviewers")
  List<User> getAllInterviewers() {
    return coordinatorService.getAllInterviewers();
  }

  @GetMapping("/users/coordinators")
  List<User> getAllCoordinators() {
    return coordinatorService.getAllCoordinators();
  }

  /**
   * Returns InterviewerSlotDto relative to {@code slot}.
   *
   * @param interviewerId slot owner interviewerId
   * @param slotId id of interviewer slot
   * @param slot new interviewerSlot
   * @return interviewerSlotDto as updated slot
   */
  @PostMapping("/interviewers/{interviewerId}/slots/{slotId}")
  public InterviewerSlotDto editSlot(@PathVariable Long interviewerId, @PathVariable Long slotId,
      @RequestBody InterviewerSlotDto slot) {
    InterviewerSlot entity = MappingUtils.mapToInterviewerSlotEntity(slot);

    InterviewerSlot responseEntity = coordinatorService.editSlot(interviewerId, slotId, entity);
    return MappingUtils.mapToInterviewerSlotDto(responseEntity);
  }
}
