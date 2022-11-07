package com.intellias.intellistart.interviewplanning.service.factory;

import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.WeekBooking;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;

public class UserFactory {
    public static final String TEST_EMAIL = "test@test.com";
    public static final String CURRENT_USER_EMAIL = "current@test.com";

    public static User createCoordinator() {
        User user = new User(UserRole.COORDINATOR);
        WeekBooking weekBooking = new WeekBooking(5, 5);
        user.setId(0L);
        user.setEmail(TEST_EMAIL);
        user.setMaxBookingsPerWeek(weekBooking);

        return user;
    }

    public static User createCurrentCoordinator() {
        User user = new User(UserRole.COORDINATOR);
        WeekBooking weekBooking = new WeekBooking(5, 5);
        user.setId(0L);
        user.setEmail(CURRENT_USER_EMAIL);
        user.setMaxBookingsPerWeek(weekBooking);

        return user;
    }
}
