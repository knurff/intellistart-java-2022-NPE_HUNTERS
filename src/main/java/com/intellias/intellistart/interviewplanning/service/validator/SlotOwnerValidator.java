package com.intellias.intellistart.interviewplanning.service.validator;

import com.intellias.intellistart.interviewplanning.exception.UserIsNotSlotOwnerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SlotOwnerValidator class, which contains methods to validate, that user is changing his own
 * slots.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SlotOwnerValidator {


  /**
   * Validates, that user is changing his own slot.
   *
   * @throws UserIsNotSlotOwnerException if user is trying to change slot that owned by another
   *                                     user.
   */
  public static void validateSlotOwner(String ownerEmail, String givenEmail) {
    if (!ownerEmail.equals(givenEmail)) {
      throw new UserIsNotSlotOwnerException("You can edit only your own slot");
    }
  }
}
