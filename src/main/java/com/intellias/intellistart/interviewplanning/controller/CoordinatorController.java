package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDto;
import com.intellias.intellistart.interviewplanning.controller.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.service.CoordinatorService;
import com.intellias.intellistart.interviewplanning.util.MappingUtils;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
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
  public List<User> getAllInterviewers() {
    return coordinatorService.getAllInterviewers();
  }

  @GetMapping("/users/coordinators")
  public List<User> getAllCoordinators() {
    return coordinatorService.getAllCoordinators();
  }

  @GetMapping("/weeks/{weekId}/dashboard")
  public DashboardDto getDashboardForWeek(@PathVariable int weekId) {
    return coordinatorService.getDashboardForWeek(weekId);
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

  @PostMapping("/users/coordinators")
  @ResponseStatus(HttpStatus.CREATED)
  public void grantCoordinatorRole(@RequestHeader("Email") String email) {
    coordinatorService.grantRoleForUser(email, UserRole.COORDINATOR);
  }

  @PostMapping("/users/interviewers")
  @ResponseStatus(HttpStatus.CREATED)
  public void grantInterviewerRole(@RequestHeader("Email") String email) {
    coordinatorService.grantRoleForUser(email, UserRole.INTERVIEWER);
  }

  @DeleteMapping("/users/coordinators/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void revokeCoordinatorRole(@PathVariable Long id) {
    coordinatorService.revokeRoleFromUser(id, UserRole.COORDINATOR);
  }

  @DeleteMapping("/users/interviewers/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void revokeInterviewerRole(@PathVariable Long id) {
    coordinatorService.revokeRoleFromUser(id, UserRole.INTERVIEWER);
  }
}