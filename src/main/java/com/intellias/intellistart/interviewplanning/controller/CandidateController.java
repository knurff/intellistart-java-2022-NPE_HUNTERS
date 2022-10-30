package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.controller.dto.mapper.CandidateSlotMapper;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.service.CandidateService;
import com.intellias.intellistart.interviewplanning.util.RequestParser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  private final CandidateSlotMapper candidateSlotMapper;

  @Autowired
  public CandidateController(CandidateService candidateService,
      CandidateSlotMapper candidateSlotMapper) {
    this.candidateService = candidateService;
    this.candidateSlotMapper = candidateSlotMapper;
  }

  /**
   * Handles GET request and returns a list of CandidateSlots of the particular user.
   */

  @GetMapping
  public List<CandidateSlotDto> getAllSlots() {
    List<CandidateSlot> candidateSlotList = candidateService.getAllSlots(
        RequestParser.getUserEmailFromToken());
    return candidateSlotMapper.mapToCandidateSlotDtoList(candidateSlotList);
  }

  /**
   * Handles POST request and creates new CandidateSlot.
   */

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CandidateSlotDto addSlot(@RequestBody CandidateSlotDto candidateSlotDto) {
    candidateSlotDto.setEmail(RequestParser.getUserEmailFromToken());

    CandidateSlot responseEntity = candidateService.createSlot(
        candidateSlotMapper.mapToCandidateSlotEntity(candidateSlotDto));
    return candidateSlotMapper.mapToCandidateSlotDto(responseEntity);
  }

  /**
   * Handles POST request and updates existing CandidateSlot.
   */

  @PostMapping("/{id}")
  public CandidateSlotDto editSlot(@PathVariable Long id,
      @RequestBody CandidateSlotDto candidateSlotDto) {
    candidateSlotDto.setEmail(RequestParser.getUserEmailFromToken());
    CandidateSlot candidateSlot = candidateSlotMapper.mapToCandidateSlotEntity(candidateSlotDto);

    CandidateSlot responseEntity = candidateService.editSlot(candidateSlot,
        id);
    return candidateSlotMapper.mapToCandidateSlotDto(responseEntity);
  }
}

