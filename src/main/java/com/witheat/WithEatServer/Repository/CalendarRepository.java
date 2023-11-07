package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
