package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.intellias.intellistart.interviewplanning.exception.InvalidCandidateSlotDateException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimeSlotBoundariesException;
import com.intellias.intellistart.interviewplanning.exception.SlotIsOverlappingException;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repository.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.util.CandidateSlotFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {
  @Mock
  private CandidateSlotRepository candidateSlotRepository;
  @Mock
  private BookingService bookingService;
  private CandidateService service;

  @BeforeEach
  void setup() {
    service = new CandidateService(candidateSlotRepository, bookingService);
  }

  @Test
  @Order(1)
  void getAllSlotsWorkingProperly() {
    CandidateSlot candidateSlot1 = CandidateSlotFactory.createCandidateSlot();
    CandidateSlot candidateSlot2 = CandidateSlotFactory.createCandidateSlot();
    String email = candidateSlot1.getEmail();

    Mockito.when(candidateSlotRepository.findAllByEmail(email))
        .thenReturn(List.of(candidateSlot1, candidateSlot2));

    List<CandidateSlot> slotList = service.getAllSlots(email);

    assertNotNull(slotList);
    assertEquals(2, slotList.size());
    assertTrue(slotList.contains(candidateSlot1));
    assertTrue(slotList.contains(candidateSlot2));
  }

  @Test
  @Order(2)
  void createSlotWorkingProperly() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createCandidateSlot();
    candidateSlot.setId(1L);

    Mockito.when(candidateSlotRepository.save(candidateSlot)).thenReturn(candidateSlot);
    Mockito.when(candidateSlotRepository.findAllByEmail(candidateSlot.getEmail()))
        .thenReturn(List.of(candidateSlot));

    CandidateSlot candidateSlotFromDb = service.createSlot(candidateSlot);
    Optional<CandidateSlot> candidateSlotOptional = findCandidateSlotFromList(candidateSlotFromDb);

    assertNotNull(candidateSlotFromDb.getId());
    assertTrue(candidateSlotOptional.isPresent());
  }

  @Test
  @Order(3)
  void createSlotThrowsAnExceptionIfDateIsNotInFuture() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithDateInPast();

    assertThrows(InvalidCandidateSlotDateException.class, () -> service.createSlot(candidateSlot));
  }

  @Test
  @Order(4)
  void createSlotThrowsAnExceptionIfTimeIsNotInFuture() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithTimePeriodInPast();

    assertThrows(InvalidTimeSlotBoundariesException.class, () -> service.createSlot(candidateSlot));
  }

  @Test
  @Order(5)
  void createSlotThrowsAnExceptionIfDurationLessThanMin() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithDurationLessThanMin();

    assertThrows(InvalidTimeSlotBoundariesException.class, () -> service.createSlot(candidateSlot));

  }

  @Test
  @Order(6)
  void createSlotThrowsAnExceptionIfPeriodIsNotRounded() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithNotRoundedPeriod();

    assertThrows(InvalidTimeSlotBoundariesException.class, () -> service.createSlot(candidateSlot));
  }

  @Test
  @Order(7)
  void createSlotThrowsAnExceptionIfAnOverlappingSlotExists() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createCandidateSlot();
    CandidateSlot candidateSlot1 = CandidateSlotFactory.createCandidateSlot();

    Mockito.when(candidateSlotRepository.findAllByEmail(candidateSlot.getEmail()))
        .thenReturn(List.of(candidateSlot));

    assertThrows(SlotIsOverlappingException.class, () -> service.createSlot(candidateSlot1));
  }


  @Test
  @Order(8)
  void editSlotWorkingProperly() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createCandidateSlot();
    candidateSlot.setDate(LocalDate.now().plusDays(2));
    CandidateSlot candidateSlotToUpdate = CandidateSlotFactory.createCandidateSlot();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);
    Mockito.when(candidateSlotRepository.findAllByEmail(candidateSlot.getEmail()))
        .thenReturn(List.of(candidateSlot, candidateSlotToUpdate));

    assertTrue(service.editSlot(candidateSlot, 1L));
  }

  @Test
  @Order(9)
  void editSlotThrowsAnExceptionIfDateIsNotInFuture() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithDateInPast();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);

    assertThrows(InvalidCandidateSlotDateException.class,
        () -> service.editSlot(candidateSlot, 1L));
  }

  @Test
  @Order(10)
  void editSlotThrowsAnExceptionIfTimeIsNotInFuture() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithTimePeriodInPast();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);

    assertThrows(InvalidTimeSlotBoundariesException.class,
        () -> service.editSlot(candidateSlot, 1L));
  }

  @Test
  @Order(11)
  void editSlotThrowsAnExceptionIfDurationLessThanMin() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithDurationLessThanMin();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);

    assertThrows(InvalidTimeSlotBoundariesException.class,
        () -> service.editSlot(candidateSlot, 1L));

  }

  @Test
  @Order(12)
  void editSlotThrowsAnExceptionIfPeriodIsNotRounded() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithNotRoundedPeriod();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);

    assertThrows(InvalidTimeSlotBoundariesException.class,
        () -> service.editSlot(candidateSlot, 1L));
  }

  @Test
  @Order(13)
  void editSlotThrowsAnExceptionIfAnOverlappingSlotExists() {

    CandidateSlot candidateSlot1 = CandidateSlotFactory.createCandidateSlot();
    CandidateSlot candidateSlot2 = CandidateSlotFactory.createCandidateSlot();
    CandidateSlot candidateSlotToUpdate = CandidateSlotFactory.createCandidateSlot();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot1);

    Mockito.when(candidateSlotRepository.findAllByEmail(candidateSlot1.getEmail()))
        .thenReturn(List.of(candidateSlot1, candidateSlot2, candidateSlotToUpdate));

    assertThrows(SlotIsOverlappingException.class, () -> service.editSlot(candidateSlot2, 1L));
  }

  private Optional<CandidateSlot> findCandidateSlotFromList(
      CandidateSlot candidateSlotBeforeUpdate) {
    return service.getAllSlots(candidateSlotBeforeUpdate.getEmail()).stream()
        .filter(slot -> Objects.equals(candidateSlotBeforeUpdate.getId(), slot.getId()))
        .findFirst();
  }

  private void setIdForSlotAndConfigureMockBehaviorForEditMethod(CandidateSlot candidateSlot) {
    candidateSlot.setId(1L);

    Mockito.when(candidateSlotRepository.findById(1L)).thenReturn(Optional.of(candidateSlot));
  }
}