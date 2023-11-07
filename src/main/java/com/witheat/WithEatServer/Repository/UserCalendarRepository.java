package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.UserCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCalendarRepository extends JpaRepository<UserCalendar, Long> {
}
