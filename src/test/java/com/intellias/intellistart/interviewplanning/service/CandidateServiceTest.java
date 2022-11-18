package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import com.intellias.intellistart.interviewplanning.exception.*;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repository.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.service.factory.CandidateSlotFactory;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

  @Mock
  private CandidateSlotRepository candidateSlotRepository;
  @Mock
  private BookingService bookingService;
  @InjectMocks
  private CandidateService service;


  @Test
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
  void createSlotThrowsAnExceptionIfDateIsNotInFuture() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithDateInPast();

    assertThrows(InvalidSlotDateException.class, () -> service.createSlot(candidateSlot));
  }

  @Test
  void createSlotThrowsAnExceptionIfTimeIsNotInFuture() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithTimePeriodInPast();

    assertThrows(InvalidTimePeriodBoundaries.class, () -> service.createSlot(candidateSlot));
  }

  @Test
  void createSlotThrowsAnExceptionIfDurationLessThanMin() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithDurationLessThanMin();

    assertThrows(InvalidTimePeriodBoundaries.class, () -> service.createSlot(candidateSlot));

  }

  @Test
  void createSlotThrowsAnExceptionIfPeriodIsNotRounded() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithNotRoundedPeriod();

    assertThrows(InvalidTimePeriodBoundaries.class, () -> service.createSlot(candidateSlot));
  }

  @Test
  void createSlotThrowsAnExceptionIfAnOverlappingSlotExists() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createCandidateSlot();
    CandidateSlot candidateSlot1 = CandidateSlotFactory.createCandidateSlot();

    Mockito.when(candidateSlotRepository.findAllByEmail(candidateSlot.getEmail()))
        .thenReturn(List.of(candidateSlot));

    assertThrows(SlotIsOverlappingException.class, () -> service.createSlot(candidateSlot1));
  }


  @Test
  void editSlotWorkingProperly() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createCandidateSlot();
    candidateSlot.setDate(LocalDate.now().plusDays(2));
    CandidateSlot candidateSlotToUpdate = CandidateSlotFactory.createCandidateSlot();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);
    Mockito.when(candidateSlotRepository.findAllByEmail(candidateSlot.getEmail()))
        .thenReturn(List.of(candidateSlot, candidateSlotToUpdate));
    Mockito.when(candidateSlotRepository.save(any(CandidateSlot.class))).thenReturn(candidateSlot);

    assertEquals(candidateSlot, service.editSlot(candidateSlot, 1L));
  }


  @Test
  void editSlotThrowsAnExceptionIfDateIsNotInFuture() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithDateInPast();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);

    assertThrows(InvalidSlotDateException.class,
        () -> service.editSlot(candidateSlot, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfTimeIsNotInFuture() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithTimePeriodInPast();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> service.editSlot(candidateSlot, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfDurationLessThanMin() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithDurationLessThanMin();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> service.editSlot(candidateSlot, 1L));

  }

  @Test
  void editSlotThrowsAnExceptionIfPeriodIsNotRounded() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotWithNotRoundedPeriod();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> service.editSlot(candidateSlot, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfAnOverlappingSlotExists() {

    CandidateSlot candidateSlot1 = CandidateSlotFactory.createCandidateSlot();
    CandidateSlot candidateSlot2 = CandidateSlotFactory.createCandidateSlot();
    CandidateSlot candidateSlotToUpdate = CandidateSlotFactory.createCandidateSlot();

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot1);

    Mockito.when(candidateSlotRepository.findAllByEmail(candidateSlot1.getEmail()))
        .thenReturn(List.of(candidateSlot1, candidateSlot2, candidateSlotToUpdate));

    assertThrows(SlotIsOverlappingException.class, () -> service.editSlot(candidateSlot2, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfSlotNotExists() {
    Long id = 1L;
    CandidateSlot newSlot = new CandidateSlot();

    Mockito.when(candidateSlotRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(SlotNotFoundException.class,
        () -> service.editSlot(newSlot, id));
  }

  @Test
  void editSlotThrowsAnExceptionIfCandidateTryingToChangeNotHisOwnSlot() {
    CandidateSlot candidateSlot1 = CandidateSlotFactory.createCandidateSlot();

    CandidateSlot candidateSlot2 = CandidateSlotFactory.createCandidateSlot();
    candidateSlot2.setId(2L);
    candidateSlot2.setEmail("test2@gmail.com");
    candidateSlot2.setDate(LocalDate.now().plusDays(5));

    setIdForSlotAndConfigureMockBehaviorForEditMethod(candidateSlot1);

    assertThrows(UserIsNotSlotOwnerException.class, () -> service.editSlot(candidateSlot2, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfSlotContainsBookings() {
    CandidateSlot newSlot = CandidateSlotFactory.createCandidateSlot();
    CandidateSlot oldSlot = CandidateSlotFactory.createCandidateSlot();
    oldSlot.getBookings().add(new Booking());

    setIdForSlotAndConfigureMockBehaviorForEditMethod(oldSlot);

    assertThrows(SlotContainsBookingsException.class,
        () -> service.editSlot(newSlot, 1L));
  }

  @Test
  void getAllSlotsWithRelatedBookingIdsUsingDateReturnsExpectedMap() {
    final CandidateSlot expectedSlot = new CandidateSlot();
    final Set<Long> expectedSet = new HashSet<>();
    final Map<CandidateSlot, Set<Long>> expectedMap = new HashMap<>();
    expectedMap.put(expectedSlot, expectedSet);

    Mockito.when(candidateSlotRepository.getAllByDate(any()))
        .thenReturn(List.of(expectedSlot));
    Mockito.when(bookingService.getAllBookingIdsRelatedToCandidateSlot(any()))
        .thenReturn(expectedSet);

    final Map<CandidateSlot, Set<Long>> actualMap =
        service.getAllSlotsWithRelatedBookingIdsUsingDate(LocalDate.now());

    assertNotNull(actualMap);
    assertTrue(actualMap.containsKey(expectedSlot));
    assertEquals(actualMap.get(expectedSlot), expectedSet);
    assertEquals(expectedMap, actualMap);
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