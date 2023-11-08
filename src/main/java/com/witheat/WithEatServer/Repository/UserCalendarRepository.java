package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.UserCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCalendarRepository extends JpaRepository<UserCalendar, Long> {
}
