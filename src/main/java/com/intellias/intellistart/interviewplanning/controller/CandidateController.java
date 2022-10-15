package com.intellias.intellistart.interviewplanning.controller;

import static com.intellias.intellistart.interviewplanning.util.CandidateSlotMapper.mapDtoToEntity;

import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.service.CandidateService;
import com.intellias.intellistart.interviewplanning.util.CandidateSlotMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * CandidateController controller.
 */
@RestController
@RequestMapping("/candidates/current/slots")
public class CandidateController {

  private final CandidateService candidateService;

  @Autowired
  public CandidateController(CandidateService candidateService) {
    this.candidateService = candidateService;
  }

  @GetMapping
  public List<CandidateSlotDto> getAllSlots() {
    List<CandidateSlot> candidateSlotList = candidateService.getAllSlots(getUserEmailFromToken());
    return CandidateSlotMapper.convertCandidateSlotListToDtoList(candidateSlotList);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CandidateSlot addSlot(@RequestBody CandidateSlotDto candidateSlotDto) {
    candidateSlotDto.setEmail(getUserEmailFromToken());
    return candidateService.createSlot(mapDtoToEntity(candidateSlotDto));
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void editSlot(@PathVariable Long id, @RequestBody CandidateSlotDto candidateSlotDto) {
    candidateSlotDto.setEmail(getUserEmailFromToken());
    candidateService.editSlot(mapDtoToEntity(candidateSlotDto), id);
  }

  private String getUserEmailFromToken() {
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    return loggedInUser.getName();
  }
}
