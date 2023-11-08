package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@org.springframework.stereotype.Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
