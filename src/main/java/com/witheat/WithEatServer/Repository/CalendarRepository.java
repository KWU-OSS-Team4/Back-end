package com.witheat.WithEatServer.Repository;

import com.witheat.WithEatServer.Domain.entity.Calendar;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
